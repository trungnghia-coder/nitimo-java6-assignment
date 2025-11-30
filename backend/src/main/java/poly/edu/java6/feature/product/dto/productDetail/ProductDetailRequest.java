package poly.edu.java6.feature.product.dto.productDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.edu.java6.feature.product.dto.productImages.ProductImageRequest;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    private String productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private BigDecimal discount;
    private List<ProductImageRequest> images;
    private List<ProductVariantDTO> productVariant;
    private List<SizeDTO> size;

    @Data
    public static class ProductVariantDTO{
        private Integer variantId;
        private Integer sizeId;
        private Integer stock;
    }

    @Data
    public static class SizeDTO{
        private String sizeName;
    }
}
