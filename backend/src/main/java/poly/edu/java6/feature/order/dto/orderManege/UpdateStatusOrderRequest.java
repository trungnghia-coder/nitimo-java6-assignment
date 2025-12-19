package poly.edu.java6.feature.order.dto.orderManege;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusOrderRequest {
    String orderId;
    String orderStatus;
    String paymentStatus;
}
