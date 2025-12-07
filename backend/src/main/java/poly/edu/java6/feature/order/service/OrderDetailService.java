package poly.edu.java6.feature.order.service;

import poly.edu.java6.feature.order.dto.FetchOrderDetail;

public interface OrderDetailService {
    FetchOrderDetail fetchOrderDetail(String orderId);
}
