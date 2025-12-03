package poly.edu.java6.feature.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.java6.model.Product;
import poly.edu.java6.model.ProductVariant;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    List<ProductVariant> findByProduct(Product product);
    Optional<ProductVariant> findByProduct_ProductCodeAndSize_SizeId(String productCode, Integer sizeId);
}
