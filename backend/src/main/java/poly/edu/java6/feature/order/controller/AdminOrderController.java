package poly.edu.java6.feature.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.edu.java6.feature.auth.dto.MessageResponse;
import poly.edu.java6.feature.order.dto.orderManege.FetchOrdersInfoDTO;
import poly.edu.java6.feature.order.dto.orderManege.UpdateStatusOrderRequest;
import poly.edu.java6.feature.order.service.OrderService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public Page<FetchOrdersInfoDTO> findAllOrder(Pageable pageable) {
        Page<FetchOrdersInfoDTO> orderList = orderService.getOrders(pageable);
        return orderList;
    }

    @GetMapping("/{id}")
    public Optional<FetchOrdersInfoDTO> findById(@PathVariable String id) {
        return orderService.findOrderById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCustomer(@RequestBody UpdateStatusOrderRequest request) {
        orderService.updateOrder(request);
        return  ResponseEntity.ok(new MessageResponse("Update successful", true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCustomer(@PathVariable String id) {
        orderService.deleteOrder(id);
        return  ResponseEntity.ok(new MessageResponse("Delete successful", true));
    }
}
