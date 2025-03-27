package jpabook.jpashop2.dto;

import jpabook.jpashop2.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

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

    public OrderItemDto(Long itemId, int orderPrice, int count) {
        this.itemId = itemId;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
