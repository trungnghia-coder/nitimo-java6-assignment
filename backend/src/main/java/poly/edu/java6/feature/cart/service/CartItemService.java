package poly.edu.java6.feature.cart.service;

import poly.edu.java6.feature.cart.dto.AddItemRequest;
import poly.edu.java6.feature.cart.dto.CartResponse;
import poly.edu.java6.feature.cart.dto.RemoveItemRequest;
import poly.edu.java6.model.Cart;
import poly.edu.java6.model.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartResponse> selectCartItemToSlideBar(String username);
    void createCartItem (AddItemRequest addItemRequest, String username);
    void deleteItemFromCart (String username, RemoveItemRequest removeItemRequest);
    void deleteALlItemFromCart(String username);
    void updateQuantityFromCart(String username, int variantId, int quantity);
}
