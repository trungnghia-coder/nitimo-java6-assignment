package poly.edu.java6.feature.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchOrderHistory {
    String orderId;
    LocalDateTime orderDate;
    BigDecimal orderAmount;
    String paymentMethod;
    String orderStatus;
}
