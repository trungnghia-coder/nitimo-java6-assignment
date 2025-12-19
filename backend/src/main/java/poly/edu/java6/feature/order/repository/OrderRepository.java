package poly.edu.java6.feature.order.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.java6.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    @EntityGraph(attributePaths = {"details", "user", "details.product", "details.variant"})
    Optional<Order> findById(String orderCode);
    List<Order> findOrderByUser_Username(String username);
    Integer countByUser_Username(String username);
}
