package poly.edu.java6.feature.product.dto.productCRUD.deleteProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductResponse {
    boolean success;
    String message;
}
