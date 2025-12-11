package poly.edu.java6.feature.product.dto.productCRUD.prodcuctFindAll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFindAllResponse {
    String productImage;
    String productId;
    String productName;
    String categoryName;
    Long inventory;
}
