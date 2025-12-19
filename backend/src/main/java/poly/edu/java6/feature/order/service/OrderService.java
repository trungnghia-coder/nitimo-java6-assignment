package poly.edu.java6.feature.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import poly.edu.java6.feature.order.dto.CreateOrderRequest;
import poly.edu.java6.feature.order.dto.CreateOrderResponse;
import poly.edu.java6.feature.order.dto.FetchOrderHistory;
import poly.edu.java6.feature.order.dto.orderManege.FetchOrdersInfoDTO;
import poly.edu.java6.feature.order.dto.orderManege.UpdateStatusOrderRequest;
import poly.edu.java6.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    ResponseEntity<CreateOrderResponse> createOrder(CreateOrderRequest createOrderRequest, String username);
    List<FetchOrderHistory> getOrderHistory(String username);

    //Order-manage
    Page<FetchOrdersInfoDTO> getOrders(Pageable pageable);
    Optional<FetchOrdersInfoDTO> findOrderById(String orderId);
    Order updateOrder(UpdateStatusOrderRequest request);
    void deleteOrder(String orderId);
}
