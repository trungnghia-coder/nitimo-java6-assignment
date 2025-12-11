package poly.edu.java6.feature.product.dto.productCRUD.productCreate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductRequest {
    String productName;
    String description;
    BigDecimal price;
    BigDecimal discount;
    String categoryCode;
    List<ProductVariantRequest> productVariantRequest;
}
