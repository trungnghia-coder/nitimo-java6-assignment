package poly.edu.java6.feature.product.dto.productCRUD.productUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductVariantRequest {
    Integer variatId;
    Integer sizeId;
    Integer stockQuantity;
}
