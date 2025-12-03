package poly.edu.java6.feature.cart.service;

import poly.edu.java6.feature.cart.dto.AddItemRequest;

public interface CartItemService {
    String createCartItem (AddItemRequest addItemRequest, String username);
}
