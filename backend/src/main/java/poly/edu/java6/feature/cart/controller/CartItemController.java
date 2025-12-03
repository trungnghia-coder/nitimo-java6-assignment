package poly.edu.java6.feature.cart.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poly.edu.java6.feature.auth.repository.AuthRepository;
import poly.edu.java6.feature.cart.dto.AddItemRequest;
import poly.edu.java6.feature.cart.service.CartItemService;
import poly.edu.java6.model.User;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    AuthRepository authRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody AddItemRequest requestDTO, Principal principal) {
        String username = principal.getName();
        try {
            cartItemService.createCartItem(requestDTO, username);
            return ResponseEntity.ok(Map.of("message", "Successfully added product to cart"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
