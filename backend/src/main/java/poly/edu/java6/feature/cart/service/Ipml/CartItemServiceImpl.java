package poly.edu.java6.feature.cart.service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.auth.service.AuthService;
import poly.edu.java6.feature.cart.dto.AddItemRequest;
import poly.edu.java6.feature.cart.repository.CartItemRepository;
import poly.edu.java6.feature.cart.repository.CartRepository;
import poly.edu.java6.feature.cart.service.CartItemService;
import poly.edu.java6.feature.cart.service.CartService;
import poly.edu.java6.feature.product.service.ProductVariantService;
import poly.edu.java6.feature.size.service.SizeService;
import poly.edu.java6.model.Cart;
import poly.edu.java6.model.CartItem;
import poly.edu.java6.model.CartItem.CartItemId;
import poly.edu.java6.model.ProductVariant;
import poly.edu.java6.model.User;

import java.math.BigDecimal;
import java.util.Optional;

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
    public String createCartItem(AddItemRequest addItemRequest, String username) {
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

        return "Added item to cart successfully!";
    }
}
