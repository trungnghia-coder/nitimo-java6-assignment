package poly.edu.java6.feature.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import poly.edu.java6.feature.product.dto.productCRUD.prodcuctFindAll.ProductSummary;
import poly.edu.java6.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductByProductCode(String productId);

    @Query(value = "SELECT p.productCode AS productCode, " +
            "MAX(p.name) AS name, " +
            "MAX(c.name) AS categoryName, " +
            "MAX(pi.imageUrl) AS primaryImageUrl, " +
            "SUM(v.stock) AS totalStock " +
            "FROM Product p " +
            "LEFT JOIN p.category c " +
            "LEFT JOIN p.images pi ON pi.isPrimary = true " +
            "LEFT JOIN p.variants v " +
            "GROUP BY p.productCode",
            countQuery = "SELECT COUNT(DISTINCT p.productCode) FROM Product p")
    Page<ProductSummary> findProductSummary(Pageable pageable);
}
