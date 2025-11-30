package poly.edu.java6.feature.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import poly.edu.java6.feature.product.dto.productDisplay.ProductDisplayRequest;

public interface ProductService {
    Page<ProductDisplayRequest> getProductDisplay(Pageable pageable);
}
