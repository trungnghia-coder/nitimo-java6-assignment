package poly.edu.java6.feature.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchOrderDetail {
    LocalDateTime orderDate;
    String paymentMethod;
    String paymentStatus;
    BigDecimal total;
    BigDecimal discount;
    String username;
    String phone;
    String address;
    private List<OrderLineItemDTO> items;
}
