package poly.edu.java6.feature.product.dto.productCRUD.productCreate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantRequest {
    private int sizeId;
    private int stockQuantity;
}
