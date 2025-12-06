package poly.edu.java6.feature.order.service;

import poly.edu.java6.feature.order.dto.CreateOrderRequest;

public interface OrderService {
    void createOrder(CreateOrderRequest createOrderRequest, String username);
}
