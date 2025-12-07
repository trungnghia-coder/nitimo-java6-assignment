package poly.edu.java6.feature.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.edu.java6.feature.order.dto.CreateOrderRequest;
import poly.edu.java6.feature.order.dto.CreateOrderResponse;
import poly.edu.java6.feature.order.dto.FetchOrderHistory;
import poly.edu.java6.feature.order.service.OrderService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest, Principal principal) {
        String name = principal.getName();
        return orderService.createOrder(createOrderRequest, name);
    }

    @GetMapping("/fetch-order-history")
    public List<FetchOrderHistory> fetchOrderHistory(Principal principal) {
        String name = principal.getName();
        return orderService.getOrderHistory(name);
    }
}
