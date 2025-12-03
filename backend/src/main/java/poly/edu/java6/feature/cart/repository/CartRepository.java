package poly.edu.java6.feature.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import poly.edu.java6.model.Cart;
import poly.edu.java6.model.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserAndStatus(@Param("user") User user, @Param("status") Cart.CartStatus status);
}
