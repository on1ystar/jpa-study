package jpabook.jpashop2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class OrderItemDto {

    private Long itemId;
    private int orderPrice;
    private int count;
}
