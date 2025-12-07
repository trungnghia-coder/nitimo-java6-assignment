package poly.edu.java6.feature.order.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.auth.service.AuthService;
import poly.edu.java6.feature.order.dto.FetchOrderDetail;
import poly.edu.java6.feature.order.dto.OrderLineItemDTO;
import poly.edu.java6.feature.order.repository.OrderDetailRepository;
import poly.edu.java6.feature.order.repository.OrderRepository;
import poly.edu.java6.feature.order.service.OrderDetailService;
import poly.edu.java6.model.*;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired OrderDetailRepository orderDetailRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired AuthService authService;

    @Override
    public FetchOrderDetail fetchOrderDetail(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        FetchOrderDetail fetchOrderDetail = new FetchOrderDetail();
        fetchOrderDetail.setOrderDate(order.getOrderDate());
        fetchOrderDetail.setPaymentMethod(order.getPaymentMethod().toString());
        fetchOrderDetail.setPaymentStatus(order.getPaymentStatus().toString());
        fetchOrderDetail.setTotal(order.getTotalAmount());
        BigDecimal discount = BigDecimal.ZERO;
        fetchOrderDetail.setDiscount(discount);
        fetchOrderDetail.setUsername(order.getUser().getFullName());
        fetchOrderDetail.setPhone(order.getUser().getPhone());
        fetchOrderDetail.setAddress(order.getUser().getAddress());

        List<OrderLineItemDTO> items = order.getDetails().stream()
                .map(od -> {
                    OrderLineItemDTO itemDto = new OrderLineItemDTO();
                    itemDto.setProductCode(od.getProduct().getProductCode());
                    itemDto.setProductName(od.getProduct().getName() + " - " +  od.getVariant().getSize().getName());
                    itemDto.setQuantity(od.getQuantity());
                    itemDto.setUnitPrice(od.getProduct().getPrice());
                    itemDto.setDiscountProduct(od.getDiscount());
                    itemDto.setSubtotalProduct(od.getSubTotal());
                    return itemDto;
                })
                .toList();
        fetchOrderDetail.setItems(items);
        return fetchOrderDetail;
    }
}
