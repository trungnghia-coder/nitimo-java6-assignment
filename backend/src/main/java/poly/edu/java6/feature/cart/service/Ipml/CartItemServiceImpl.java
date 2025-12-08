package poly.edu.java6.feature.cart.service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.java6.feature.auth.service.AuthService;
import poly.edu.java6.feature.cart.dto.AddItemRequest;
import poly.edu.java6.feature.cart.dto.CartResponse;
import poly.edu.java6.feature.cart.dto.RemoveItemRequest;
import poly.edu.java6.feature.cart.repository.CartItemRepository;
import poly.edu.java6.feature.cart.repository.CartRepository;
import poly.edu.java6.feature.cart.service.CartItemService;
import poly.edu.java6.feature.cart.service.CartService;
import poly.edu.java6.feature.product.service.ProductVariantService;
import poly.edu.java6.feature.size.service.SizeService;
import poly.edu.java6.model.*;
import poly.edu.java6.model.CartItem.CartItemId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    AuthService authService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;
    @Autowired
    SizeService sizeService;
    @Autowired
    ProductVariantService productVariantService;
    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public List<CartResponse> selectCartItemToSlideBar(String username) {
        User user = authService.findUserByUsername(username);
        Cart cart = cartRepository.findByUserAndStatus(user, Cart.CartStatus.ACTIVE)
                .orElseGet(() -> cartService.createCart(user));

        List<CartItem> items = cartItemRepository.findByCartWithAllDetails(cart);
        return items.stream()
                .map(item -> {
                    ProductVariant variant = item.getVariant();
                    Product product = variant.getProduct();

                    BigDecimal lineTotalPrice = item.getLineTotalPrice();

                    String imageUrl = findPrimaryImageUrl(product.getImages());

                    return new CartResponse(
                            item.getCart().getCartCode(),
                            product.getName(),
                            imageUrl,
                            item.getQuantity(),
                            lineTotalPrice.doubleValue(),
                            variant.getSize().getName(),
                            variant.getVariantId()
                    );
                })
                .collect(Collectors.toList());
    }

    private String findPrimaryImageUrl(List<ProductImage> images) {
        if (images == null || images.isEmpty()) {
            return "/default-image.jpg";
        }

        return images.stream()
                .filter(ProductImage::getIsPrimary)
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElse(images.get(0).getImageUrl());
    }

    @Override
    public void createCartItem(AddItemRequest addItemRequest, String username) {
        User user = authService.findUserByUsername(username);
        Cart cart = cartRepository.findByUserAndStatus(user, Cart.CartStatus.ACTIVE)
                .orElseGet(() -> cartService.createCart(user));

        ProductVariant variant = productVariantService.findProductAndSize(addItemRequest.getProductCode(),
                addItemRequest.getSize());
        if (variant.getStock() < addItemRequest.getQuantity()) {
            throw new RuntimeException("Sản phẩm không đủ số lượng tồn kho.");
        }

        Optional<CartItem> existingItemOpt = cartItemRepository
                .findByCart_CartCodeAndVariant_VariantId(cart.getCartCode(), variant.getVariantId());

        BigDecimal unitPrice = variant.getProduct().getPrice().subtract(variant.getProduct().getDiscount());
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();

            int newQuantity = existingItem.getQuantity() + addItemRequest.getQuantity();
            existingItem.setQuantity(newQuantity);

            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(newQuantity));

            existingItem.setPrice(totalPrice);

            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();

            CartItemId id = new CartItemId();
            id.setCartCode(cart.getCartCode());
            id.setVariantId(variant.getVariantId());
            newItem.setId(id);

            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setQuantity(addItemRequest.getQuantity());

            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(addItemRequest.getQuantity()));

            newItem.setPrice(totalPrice);

            cartItemRepository.save(newItem);
        }
    }

    @Override
    @Transactional
    public void deleteItemFromCart(String username, RemoveItemRequest removeItemRequest) {
        User user = authService.findUserByUsername(username);
        Cart cart = cartRepository.findByUserAndStatus(user, Cart.CartStatus.ACTIVE)
                .orElse(null);
        if (cart != null) {
            cartItemRepository.deleteByCart_CartCodeAndVariant_VariantId(cart.getCartCode(), removeItemRequest.getVariantId());
        }
    }

    @Override
    @Transactional
    public void deleteALlItemFromCart(String username) {
        User user = authService.findUserByUsername(username);
        Cart activeCart = cartRepository.findByUserAndStatus(user, Cart.CartStatus.ACTIVE)
                .orElse(null);
        System.out.println(activeCart);
        if (activeCart != null) {
            cartItemRepository.deleteByCart_CartCode(activeCart.getCartCode());
        }
    }

    @Override
    @Transactional
    public void updateQuantityFromCart(String username, int variantId, int quantity) {
        User user = authService.findUserByUsername(username);
        Cart cart = cartRepository.findByUserAndStatus(user, Cart.CartStatus.ACTIVE)
                .orElseGet(() -> cartService.createCart(user));
        CartItem item = cartItemRepository
                .findByCart_CartCodeAndVariant_VariantId(cart.getCartCode(), variantId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

        if (quantity <= 0) {
            cartItemRepository.delete(item);
            return;
        }

        if (item.getVariant().getStock() < quantity) {
            throw new RuntimeException("Số lượng mới vượt quá tồn kho.");
        }

        item.setQuantity(quantity);
        BigDecimal unitPrice = productVariantService.calculateUnitPrice(item.getVariant());
        item.setPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));

        cartItemRepository.save(item);
    }

    @Override
    public Boolean checkCartEmpty(String username) {
        User user = authService.findUserByUsername(username);
        Optional<Cart> optionalCart = cartRepository.findByUserAndStatus(user, Cart.CartStatus.ACTIVE);

        if (optionalCart.isEmpty()) {
            return true;
        }

        Cart cart = optionalCart.get();
        long itemCount = cartItemRepository.countByCart(cart);

        return itemCount == 0;
    }
}
