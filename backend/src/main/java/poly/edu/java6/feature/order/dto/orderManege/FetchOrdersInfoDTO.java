package poly.edu.java6.feature.order.dto.orderManege;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchOrdersInfoDTO {
    String orderId;
    String customerName;
    LocalDateTime orderDate;
    BigDecimal orderAmount;
    String orderStatus;
    String paymentStatus;
}
