package poly.edu.java6.feature.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import poly.edu.java6.feature.product.dto.productDetail.ProductDetailRequest;
import poly.edu.java6.feature.product.dto.productDisplay.ProductDisplayRequest;
import poly.edu.java6.model.Product;

public interface ProductService {
    Page<ProductDisplayRequest> getProductDisplay(Pageable pageable);
    ProductDetailRequest getProductById(String Id);
    Product findByProductCode(String productCode);
}
