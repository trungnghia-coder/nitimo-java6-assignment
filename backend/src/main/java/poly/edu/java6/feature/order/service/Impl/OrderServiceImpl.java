package poly.edu.java6.feature.order.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.java6.feature.auth.dto.userManage.FindUserDTO;
import poly.edu.java6.feature.auth.service.AuthService;
import poly.edu.java6.feature.cart.service.CartService;
import poly.edu.java6.feature.order.dto.CreateOrderRequest;
import poly.edu.java6.feature.order.dto.CreateOrderResponse;
import poly.edu.java6.feature.order.dto.FetchOrderHistory;
import poly.edu.java6.feature.order.dto.orderManege.FetchOrdersInfoDTO;
import poly.edu.java6.feature.order.dto.orderManege.UpdateStatusOrderRequest;
import poly.edu.java6.feature.order.repository.OrderDetailRepository;
import poly.edu.java6.feature.order.repository.OrderRepository;
import poly.edu.java6.feature.order.service.OrderService;
import poly.edu.java6.feature.product.repository.ProductVariantRepository;
import poly.edu.java6.feature.product.service.ProductService;
import poly.edu.java6.feature.product.service.ProductVariantService;
import poly.edu.java6.model.*;
import poly.edu.java6.utils.IdUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CartService cartService;
    @Autowired
    ProductVariantService productVariantService;
    @Autowired
    ProductService productService;
    @Autowired
    AuthService authService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductVariantRepository productVariantRepository;

    @Override
    @Transactional
    public ResponseEntity<CreateOrderResponse> createOrder(CreateOrderRequest createOrderRequest, String username) {
        User user = authService.findUserByUsername(username);
        Cart activeCart = cartService.findByUserAndStatus(username);
        if (activeCart == null) {
            throw new RuntimeException("Người dùng không có giỏ hàng đang hoạt động.");
        }

        List<CartItem> items = activeCart.getItems();

        Map<Integer, ProductVariant> variantMap = new HashMap<>();
        Map<String, Product> productMap = new HashMap<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem ci : items) {
            ProductVariant v = productVariantService.findById(ci.getVariant().getVariantId());
            if (v.getStock() < ci.getQuantity()) {
                throw new RuntimeException("Variant " + v.getVariantId() + " out of stock");
            }

            Product p = productService.findByProductCode(v.getProduct().getProductCode());
            variantMap.put(v.getVariantId(), v);
            productMap.put(p.getProductCode(), p);
            BigDecimal unitPrice = p.getPrice().subtract(p.getDiscount());
            totalAmount = totalAmount.add(unitPrice.multiply(BigDecimal.valueOf(ci.getQuantity())));
        }

        BigDecimal totalDiscountAmount = BigDecimal.ZERO;
        String voucherId = createOrderRequest.getVoucherId();
        if (voucherId != null && !voucherId.trim().isEmpty()) {
            System.out.println("Voucher " + voucherId + " được gửi, nhưng tạm thời không áp dụng.");
        }
        totalAmount = totalAmount.subtract(totalDiscountAmount);
        String orderId = IdUtils.generateNewId();
        String transactionId = IdUtils.generateNewId();

        Order order = new Order();
        order.setOrderCode(orderId);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(createOrderRequest.getShippingAddress());
        order.setPaymentMethod(Order.PaymentMethod.valueOf(createOrderRequest.getPaymentMethod()));
        order.setPaymentStatus(Order.PaymentStatus.UNPAID);
        order.setTransactionId(transactionId);
        orderRepository.save(order);

        for (CartItem ci : items) {
            ProductVariant v = variantMap.get(ci.getVariant().getVariantId());
            Product p = productMap.get(v.getProduct().getProductCode());

            OrderDetail od = new OrderDetail();

            // CRITICAL: Phải khởi tạo OrderDetailId trước
            OrderDetail.OrderDetailId odId = new OrderDetail.OrderDetailId();
            odId.setOrderCode(order.getOrderCode());
            odId.setProductCode(p.getProductCode());
            odId.setVariantId(v.getVariantId());
            od.setId(odId);

            od.setOrder(order);
            od.setProduct(p);
            od.setVariant(v);
            od.setQuantity(ci.getQuantity());
            BigDecimal unitPrice = p.getPrice().subtract(p.getDiscount());
            od.setUnitPrice(unitPrice);
            od.setSubTotal(unitPrice.multiply(BigDecimal.valueOf(ci.getQuantity())));
            od.setDiscount(p.getDiscount() != null ? p.getDiscount() : BigDecimal.ZERO);
            orderDetailRepository.save(od);

            v.setStock(v.getStock() - ci.getQuantity());
            productVariantRepository.save(v);
        }

        cartService.deactive(activeCart);
        return ResponseEntity.ok(new CreateOrderResponse(orderId, true, "Create order successful"));
    }

    @Override
    public List<FetchOrderHistory> getOrderHistory(String username) {
        List<Order> order = orderRepository.findOrderByUser_Username(username);

        List<FetchOrderHistory> fetchOrderHistoryList = new ArrayList<>();
        for (Order o : order) {
            FetchOrderHistory fh = new FetchOrderHistory();
            fh.setOrderId(o.getOrderCode());
            fh.setOrderDate(o.getOrderDate());
            fh.setOrderAmount(o.getTotalAmount());
            fh.setPaymentMethod(o.getPaymentMethod().toString());
            fh.setOrderStatus(o.getStatus().toString());
            fetchOrderHistoryList.add(fh);
        }
        return fetchOrderHistoryList;
    }

    @Override
    public Page<FetchOrdersInfoDTO> getOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(order -> new FetchOrdersInfoDTO(
                order.getOrderCode(),
                order.getUser().getFullName(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus().toString(),
                order.getPaymentStatus().toString()));
    }

    @Override
    public Optional<FetchOrdersInfoDTO> findOrderById(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(o -> new FetchOrdersInfoDTO(
                o.getOrderCode(),
                o.getUser().getFullName(),
                o.getOrderDate(),
                o.getTotalAmount(),
                o.getStatus().toString(),
                o.getPaymentStatus().toString()
        ));
    }

    @Override
    public Order updateOrder(UpdateStatusOrderRequest request) {
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() -> new NullPointerException("User not found with username: " + request.getOrderId()));
        String statusStr = request.getOrderStatus().toUpperCase();
        Order.OrderStatus newStatus = Order.OrderStatus.valueOf(statusStr);
        order.setStatus(newStatus);
        String paymentStr = request.getPaymentStatus().toUpperCase();
        Order.PaymentStatus payment = Order.PaymentStatus.valueOf(paymentStr);
        order.setPaymentStatus(payment);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NullPointerException("User not found with username: " + orderId));
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
