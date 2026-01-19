package com.spartabugkiller.ecommercebackofficeproject.order.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.AdminNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.request.OrderCancelRequest;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.request.OrderCreateRequest;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.request.OrderStatusUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.response.*;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import com.spartabugkiller.ecommercebackofficeproject.order.exception.*;
import com.spartabugkiller.ecommercebackofficeproject.order.repository.OrderRepository;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.ProductNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final OrderNumberGenerator orderNumberGenerator;

    /**
     * 주문 등록
     */
    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest request, Long adminId) {
        Admin loginAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new OrderCustomerNotFoundException(ErrorCode.ORDER_CUSTOMER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        // 단종,품절,재고부족 검증
        validationProductForOrder(product, request.getQuantity());

        // 재고 차감, 상태 변경
        product.updateStock(product.getStock() - request.getQuantity());

        // 주문 번호
        String orderNum = orderNumberGenerator.generate();

        Order order = Order.builder()
                .orderNum(orderNum)
                .status(OrderStatus.READY)
                .quantity(request.getQuantity())
                .totalPrice(product.getPrice() * request.getQuantity())
                .customer(customer)
                .product(product)
                .admin(loginAdmin)
                .build();

        Order savedOrder = orderRepository.save(order);

        return new OrderCreateResponse(savedOrder);
    }

    /**
     * 단종,품절,재고부족 검증
     */
    private void validationProductForOrder(Product product, int quantity) {
        // 단종 상태 확인
        if (product.getStatus() == ProductStatus.DISCONTINUED) {
            throw new OrderProductDiscontinuedException(ErrorCode.ORDER_PRODUCT_DISCONTINUED);
        }

        // 수량 1 이상 검증
        if (quantity < 1) {
            throw new OrderQuantityInvalidException(ErrorCode.ORDER_QUANTITY_INVALID);
        }

        // 품절 상태 확인
        if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
            throw new OrderOutOfStockException(ErrorCode.ORDER_OUT_OF_STOCK);
        }

        // 재고 부족 확인
        if (product.getStock() < quantity) {
            throw new OrderOutOfStockException(ErrorCode.ORDER_OUT_OF_STOCK);
        }
    }

    /**
     * 주문 상세 조회
     */
    public OrderDetailResponse getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(ErrorCode.ORDER_NOT_FOUND));
        return new OrderDetailResponse(order);
    }

    /**
     * 주문 리스트 조회
     */
    @Transactional(readOnly = true)
    public Page<OrderListResponse> getOrderList(
            String keyword,
            OrderStatus status,
            int page,
            int size,
            String sortBy,
            String order
    ) {
        Sort sort = Sort.by(
                "asc".equalsIgnoreCase(order)
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                sortBy
        );

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> orders =
                orderRepository.findWithFilters(keyword, status, pageable);

        return orders.map(OrderListResponse::new);
    }

    /**
     * 주문 상태 변경
     */
    @Transactional
    public OrderStatusUpdateResponse updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
        // 예외처리
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(ErrorCode.ORDER_NOT_FOUND));
        // 변경전 상태 저장
        OrderStatus beforeStatus = order.getStatus();
        // 상태 변경
        order.changeStatus(request.getStatus());

        return OrderStatusUpdateResponse.from(order, beforeStatus);
    }

    /**
     * 주문 취소
     */
    @Transactional
    public OrderCancelResponse cancelOrder(Long orderId, OrderCancelRequest request) {

        // 예외처리
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(ErrorCode.ORDER_NOT_FOUND));
        OrderStatus beforeStatus = order.getStatus();

        order.cancel(request.getCancelReason());

        // 재고 복구
        Product product = order.getProduct();
        product.restoreStock(order.getQuantity());

        return OrderCancelResponse.from(order, beforeStatus);
    }
}
