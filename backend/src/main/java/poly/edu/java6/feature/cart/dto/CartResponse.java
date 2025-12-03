package poly.edu.java6.feature.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String cartCode;
    private String productName;
    private String imageUrl;
    private Integer quantity;
    private Double price;
    private String size;
    private Integer variantId;
}
