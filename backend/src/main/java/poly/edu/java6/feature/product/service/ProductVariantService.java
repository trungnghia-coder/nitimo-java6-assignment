package poly.edu.java6.feature.product.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.product.repository.ProductVariantRepository;
import poly.edu.java6.model.ProductVariant;

@Service
public class ProductVariantService {
    @Autowired
    private ProductVariantRepository productVariantRepository;

    public ProductVariant findProductAndSize(String productId, int size) {
        return productVariantRepository
                .findByProduct_ProductCodeAndSize_SizeId(productId, size)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm với size đã chọn."));
    }

}
