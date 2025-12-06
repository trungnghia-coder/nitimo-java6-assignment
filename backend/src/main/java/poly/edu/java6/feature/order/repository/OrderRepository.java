package poly.edu.java6.feature.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.java6.model.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
}
