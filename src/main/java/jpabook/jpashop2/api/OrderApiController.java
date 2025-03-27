package jpabook.jpashop2.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.dto.OrderItemDto;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.service.OrderService;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.EAGER;

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

    @GetMapping("/api/orders")
    public Result<List<SimpleOrderDto>> orders(@RequestBody OrdersRequest request) {
        return new Result<>(orderService.findOrders(request.getMemberId()).stream().map(SimpleOrderDto::new).collect(Collectors.toList()));
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

    @Getter
    @Setter
    static class OrderDto {

        private Long orderId;
        private String memberName;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.memberName = order.getMember().getName();
            this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).toList();
        }
    }

    @Getter
    @Setter
    static class OrderItemDto {

        private Long itemId;
        private String name;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemId = orderItem.getId();
            this.name = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }

    @AllArgsConstructor
    @Data
    static class SaveOrderRequest {

        @NotNull
        private Long memberId;

        @NotNull
        private List<jpabook.jpashop2.dto.OrderItemDto> orderItemDtos;
    }

    @AllArgsConstructor
    @Data
    static class SaveOrderResponse {

        private Long id;
    }

    @Data
    static class OrdersRequest {

        private Long memberId;
    }

    @Data
    static class SimpleOrderDto {

        private Long orderId;
        private String memberName;
        private LocalDateTime createdDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.memberName = order.getMember().getName();
        }
    }
}
