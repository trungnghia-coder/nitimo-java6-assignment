package poly.edu.java6.feature.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.java6.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
