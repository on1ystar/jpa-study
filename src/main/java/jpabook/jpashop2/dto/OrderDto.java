package jpabook.jpashop2.dto;

import jpabook.jpashop2.domain.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderDto {

    private Long orderId;
    private String memberName;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.memberName = order.getMember().getName();
        this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).toList();
    }
}