package poly.edu.java6.feature.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.java6.model.CartItem;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
    Optional<CartItem> findByCart_CartCodeAndVariant_VariantId(String cartCode, Integer variantId);
}
