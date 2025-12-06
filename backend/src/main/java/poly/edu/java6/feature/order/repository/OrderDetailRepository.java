package poly.edu.java6.feature.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.java6.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}
