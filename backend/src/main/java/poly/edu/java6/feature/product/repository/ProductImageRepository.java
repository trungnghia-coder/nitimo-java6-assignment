package poly.edu.java6.feature.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.java6.model.Product;
import poly.edu.java6.model.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Product product);
    List<ProductImage> findByIsPrimary(Boolean isPrimary);
    @Transactional
    void deleteByProduct_ProductCode(String productId);
}
