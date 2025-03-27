package jpabook.jpashop2.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.dto.OrderDto;
import jpabook.jpashop2.dto.OrderItemDto;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.service.OrderService;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/api/orders")
    public Result<SaveOrderResponse> saveOrder(@RequestBody @Valid SaveOrderRequest request) {
        Long orderedId = orderService.order(request.getMemberId(), request.getOrderItemDtos());
        return new Result<>(new SaveOrderResponse(orderedId));
    }

    @GetMapping("/api/v1/orders")
    public Result<List<Order>> ordersV1() {
        List<Order> findOrders = orderRepository.findAll();
        findOrders.forEach(o -> {
            o.getMember().getName();                                  //Member 강제 초기화
            o.getOrderItems().forEach(oi -> oi.getItem().getName());  //OrderItem, Item 강제 초기화
        });

        return new Result<>(findOrders);
    }

    @GetMapping("/api/v2/orders")
    public Result<List<OrderDto>> ordersV2() {
        List<Order> findOrders = orderRepository.findAll();
        return new Result<>(findOrders.stream()
                .map(OrderDto::new)
                .toList());
    }

    @GetMapping("/api/v3/orders")
    public Result<List<OrderDto>> ordersV3() {
        List<Order> findOrders = orderRepository.findAllWithItem();
        return new Result<>(findOrders.stream().map(OrderDto::new).toList());
    }

    @GetMapping("/api/v4/orders")
    public Result<List<OrderDto>> ordersV4() {
        List<Order> findOrders = orderRepository.findAllWithItem(0, 2);
        return new Result<>(findOrders.stream().map(OrderDto::new).toList());
    }

    @AllArgsConstructor
    @Data
    static class SaveOrderRequest {

        @NotNull
        private Long memberId;

        @NotNull
        private List<OrderItemDto> orderItemDtos;
    }

    @AllArgsConstructor
    @Data
    static class SaveOrderResponse {

        private Long id;
    }
}
