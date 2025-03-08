package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import jpabook.jpashop2.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem extends BaseEntity {

    private OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int count;

    //===연관관계 편의 메서드===
    public void setOrder(Order order) {
        this.order = order;
    }

    //===생성 메서드===
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem(item, orderPrice, count);
        item.removeStock(count);
        return orderItem;
    }

    //===비즈니스 메서드===

    /**
     * 주문 취소 시 상품 재고 수량 증가
     */
    public void cancel() {
        this.item.addStock(this.count);
    }

    /**
     * 총 주문 금액
     * @return orderPrice * count
     */
    public int getTotalOrderPrice() {
        return this.orderPrice * this.count;
    }
}
