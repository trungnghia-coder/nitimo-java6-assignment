package poly.edu.java6.feature.cart.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.edu.java6.feature.auth.repository.AuthRepository;
import poly.edu.java6.feature.cart.dto.AddItemRequest;
import poly.edu.java6.feature.cart.dto.CartResponse;
import poly.edu.java6.feature.cart.dto.RemoveItemRequest;
import poly.edu.java6.feature.cart.service.CartItemService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    AuthRepository authRepository;

    @GetMapping("/slide-bar")
    public ResponseEntity<?> getCartItemsForSlideBar(Principal principal) {
        try {
            String user = principal.getName();

            List<CartResponse> items = cartItemService.selectCartItemToSlideBar(user);
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

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

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody RemoveItemRequest removeItemRequest, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            cartItemService.deleteItemFromCart(username, removeItemRequest);
            return ResponseEntity.ok("Successfully removed item from cart");
        } else {
            return ResponseEntity.status(401).body("Chưa xác thực");
        }
    }

    @DeleteMapping("/clean")
    public ResponseEntity<String> removeAllFromCart(Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            System.out.println(username);
            cartItemService.deleteALlItemFromCart(username);
            return ResponseEntity.ok("Successfully removed all item from cart");
        } else {
            return ResponseEntity.status(401).body("Chưa xác thực");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateQuantity(
            @RequestParam Integer variantId,
            @RequestParam int quantity,
            Principal principal) {

        String username = principal.getName();
        cartItemService.updateQuantityFromCart(username, variantId, quantity);
        return ResponseEntity.ok("Successfully updated item quantity");
    }
}
