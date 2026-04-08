package com.example.loginapi.controller;

import com.example.loginapi.dto.*;
import com.example.loginapi.entity.Order;
import com.example.loginapi.entity.OrderItem;
import com.example.loginapi.entity.Product;
import com.example.loginapi.repository.OrderRepository;
import com.example.loginapi.repository.ProductRepository;
import com.example.loginapi.util.TokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CARD");
        if (request.getCardNumber() != null && request.getCardNumber().length() >= 4) {
            String digits = request.getCardNumber().replaceAll("\\D", "");
            order.setCardLast4(digits.length() >= 4 ? digits.substring(digits.length() - 4) : null);
        }

        double total = 0;
        for (OrderItemRequest itemReq : request.getItems()) {
            Optional<Product> optProduct = productRepository.findById(itemReq.getProductId());
            if (optProduct.isEmpty() || itemReq.getQuantity() == null || itemReq.getQuantity() <= 0) {
                continue;
            }
            Product p = optProduct.get();
            OrderItem item = new OrderItem(order, p.getId(), p.getName(), p.getPrice(), itemReq.getQuantity());
            order.getItems().add(item);
            total += p.getPrice() * itemReq.getQuantity();
        }

        if (order.getItems().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        order.setTotalAmount(total);
        order = orderRepository.save(order);

        OrderResponse response = toResponse(order);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = TokenUtil.getUserIdFromAuthHeader(authHeader);
        List<Order> orders;
        if (userId != null) {
            orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        } else {
            orders = orderRepository.findAllByOrderByCreatedAtDesc();
        }
        List<OrderResponse> result = new ArrayList<>();
        for (Order o : orders) {
            result.add(toResponse(o));
        }
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(o -> ResponseEntity.ok(toResponse(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse r = new OrderResponse();
        r.setId(order.getId());
        r.setCustomerName(order.getCustomerName());
        r.setCustomerEmail(order.getCustomerEmail());
        r.setShippingAddress(order.getShippingAddress());
        r.setPaymentMethod(order.getPaymentMethod());
        r.setCardLast4(order.getCardLast4());
        r.setCreatedAt(order.getCreatedAt());
        r.setTotalAmount(order.getTotalAmount());
        List<OrderItemResponse> items = new ArrayList<>();
        for (OrderItem i : order.getItems()) {
            OrderItemResponse ir = new OrderItemResponse();
            ir.setProductId(i.getProductId());
            ir.setProductName(i.getProductName());
            ir.setUnitPrice(i.getUnitPrice());
            ir.setQuantity(i.getQuantity());
            items.add(ir);
        }
        r.setItems(items);
        return r;
    }
}
