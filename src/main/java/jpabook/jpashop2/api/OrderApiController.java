package jpabook.jpashop2.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.dto.OrderItemDto;
import jpabook.jpashop2.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public Result<SaveOrderResponse> saveOrder(@RequestBody @Valid SaveOrderRequest request) {
        Long orderedId = orderService.order(request.getMemberId(), request.getOrderItemDtos());
        return new Result<>(new SaveOrderResponse(orderedId));
    }

    @GetMapping("/api/orders")
    public Result<List<SimpleOrderDto>> orders(@RequestBody OrdersRequest request) {
        return new Result<>(orderService.findOrders(request.getMemberId()).stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList()));
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
            this.createdDate = order.getCreatedDate();
            this.orderStatus = order.getOrderStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}
