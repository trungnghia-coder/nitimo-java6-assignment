package poly.edu.java6.feature.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import poly.edu.java6.feature.product.dto.productCRUD.prodcuctFindAll.ProductFindAllResponse;
import poly.edu.java6.feature.product.dto.productCRUD.prodcuctFindAll.ProductSummary;
import poly.edu.java6.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductByProductCode(String productId);

    @Query(value = "SELECT p.productCode AS productCode, " +
            "p.name AS name, " +
            "c.name AS categoryName, " +
            "pi.imageUrl AS primaryImageUrl, " +
            "SUM(v.stock) AS totalStock " +
            "FROM Product p " +
            "LEFT JOIN p.category c " + // Tham gia Category
            "LEFT JOIN p.images pi ON pi.isPrimary = true " + // Tham gia Ảnh chính (lọc isPrimary = true)
            "JOIN p.variants v " + // Tham gia Biến thể để tính tồn kho
            "GROUP BY p.productCode, p.name, c.name, pi.imageUrl",
            countQuery = "SELECT COUNT(DISTINCT p.productCode) FROM Product p")
    Page<ProductSummary> findProductSummary(Pageable pageable);
}
