package poly.edu.java6.feature.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poly.edu.java6.feature.order.dto.CreateOrderRequest;
import poly.edu.java6.feature.order.service.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public void createOrder(@RequestBody CreateOrderRequest createOrderRequest, Principal principal) {
        String name = principal.getName();
        orderService.createOrder(createOrderRequest, name);
    }
}
