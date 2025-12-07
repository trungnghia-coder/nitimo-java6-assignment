package poly.edu.java6.feature.order.service;

import org.springframework.http.ResponseEntity;
import poly.edu.java6.feature.order.dto.CreateOrderRequest;
import poly.edu.java6.feature.order.dto.CreateOrderResponse;
import poly.edu.java6.feature.order.dto.FetchOrderHistory;

import java.util.List;

public interface OrderService {
    ResponseEntity<CreateOrderResponse> createOrder(CreateOrderRequest createOrderRequest, String username);
    List<FetchOrderHistory> getOrderHistory(String username);
}
