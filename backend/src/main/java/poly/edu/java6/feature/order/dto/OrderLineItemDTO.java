package poly.edu.java6.feature.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemDTO {
    String productCode;
    String productName;
    Integer quantity;
    BigDecimal unitPrice;
    BigDecimal discountProduct;
    BigDecimal subtotalProduct;
}
