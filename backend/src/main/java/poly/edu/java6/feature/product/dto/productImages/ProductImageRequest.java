package poly.edu.java6.feature.product.dto.productImages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequest {
    private Integer imageId;
    private String imageUrl;
    private Boolean isPrimary;
}
