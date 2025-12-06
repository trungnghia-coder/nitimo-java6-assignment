package poly.edu.java6.feature.cart.service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.auth.service.AuthService;
import poly.edu.java6.feature.cart.repository.CartRepository;
import poly.edu.java6.feature.cart.service.CartService;
import poly.edu.java6.model.Cart;
import poly.edu.java6.model.User;
import poly.edu.java6.utils.IdUtils;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    AuthService authService;

    @Override
    public Cart createCart(User username) {
        String newId = IdUtils.generateNewId();
        Cart newCart = new Cart();
        newCart.setCartCode(newId);
        newCart.setUser(username);
        newCart.setStatus(Cart.CartStatus.ACTIVE);
        return cartRepository.save(newCart);
    }

    @Override
    public Cart findByUserAndStatus(String username) {
        User user = authService.findUserByUsername(username);
        return cartRepository.findByUserAndStatus(user, Cart.CartStatus.ACTIVE)
                .orElse(null);
    }

    @Override
    public void deactive(Cart cart) {
        if (cart != null) {
            cart.setStatus(Cart.CartStatus.INACTIVE);
            cartRepository.save(cart);
        }
    }
}
