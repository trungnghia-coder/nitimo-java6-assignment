package poly.edu.java6.feature.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.java6.model.Cart;
import poly.edu.java6.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
    Optional<CartItem> findByCart_CartCodeAndVariant_VariantId(String cartCode, Integer variantId);

    @Transactional
    void deleteByCart_CartCodeAndVariant_VariantId(String cartCode, Integer variantId);

    @Transactional
    void deleteByCart_CartCode(String cartCode);

    @Query("""
            SELECT ci FROM CartItem ci
            JOIN FETCH ci.variant pv
            JOIN FETCH pv.product p
            JOIN FETCH pv.size s
            WHERE ci.cart = :cart
            """)
    List<CartItem> findByCartWithAllDetails(@Param("cart") Cart cart);
}
