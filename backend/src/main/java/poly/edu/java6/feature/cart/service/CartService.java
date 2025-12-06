package poly.edu.java6.feature.cart.service;

import poly.edu.java6.model.Cart;
import poly.edu.java6.model.User;

public interface CartService {
    Cart createCart (User username);
    Cart findByUserAndStatus(String username);
    void deactive(Cart cart);
}
