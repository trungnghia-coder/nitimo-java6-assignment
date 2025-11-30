package poly.edu.java6.feature.product.dto.categoryMenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesMenuRequest {
    private String categoryId;
    private String categoryName;
}
