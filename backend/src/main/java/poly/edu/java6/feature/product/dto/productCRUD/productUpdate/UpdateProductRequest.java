package poly.edu.java6.feature.product.dto.productCRUD.productUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.edu.java6.feature.product.dto.productCRUD.productCreate.ProductVariantRequest;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    String productName;
    String description;
    BigDecimal price;
    BigDecimal discount;
    String categoryCode;
    List<UpdateProductVariantRequest> productVariantRequest;
}
