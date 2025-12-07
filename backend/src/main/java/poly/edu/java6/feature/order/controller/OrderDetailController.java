package poly.edu.java6.feature.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import poly.edu.java6.feature.order.dto.FetchOrderDetail;
import poly.edu.java6.feature.order.service.OrderDetailService;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {
    @Autowired OrderDetailService orderDetailService;

    @GetMapping("/fetch")
    public FetchOrderDetail fetchOrderDetail(@RequestParam String orderId) {
        return orderDetailService.fetchOrderDetail(orderId);
    }
}
