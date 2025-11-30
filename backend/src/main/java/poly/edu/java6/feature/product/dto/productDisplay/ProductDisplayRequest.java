package poly.edu.java6.feature.product.dto.productDisplay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.edu.java6.feature.product.dto.productImages.ProductImageRequest;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDisplayRequest {
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private BigDecimal discount;
    private List<ProductImageRequest> images;
}
