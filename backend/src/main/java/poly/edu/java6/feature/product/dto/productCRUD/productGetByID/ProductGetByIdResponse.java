package poly.edu.java6.feature.product.dto.productCRUD.productGetByID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.edu.java6.feature.product.dto.productImages.ProductImageRequest;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetByIdResponse {
    private String productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private BigDecimal discount;
    private String productCategory;
    private List<ProductImageRequest> images;
    private List<ProductGetByIdResponse.ProductVariantDTO> productVariant;
    private List<ProductGetByIdResponse.SizeDTO> size;

    @Data
    public static class ProductVariantDTO{
        private Integer variantId;
        private Integer size;
        private Integer stock;
    }

    @Data
    public static class SizeDTO{
        private Integer sizeId;
        private String sizeName;
    }
}
