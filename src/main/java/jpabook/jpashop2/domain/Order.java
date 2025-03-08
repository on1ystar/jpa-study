package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import jpabook.jpashop2.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;

@Entity(name = "Orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseEntity {

    private Order(Member member, Delivery delivery, OrderStatus orderStatus) {
        this.orderStatus = orderStatus;

        setMember(member);
        setDelivery(delivery);
    }

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "delivery_id", unique = true)
    private Delivery delivery;

    @Enumerated(value = STRING)
    private OrderStatus orderStatus;

    //===연관관계 편의 메서드===
    private void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    private void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    private void addOrderItem(OrderItem orderItem) {
        this.getOrderItems().add(orderItem);
        orderItem.setOrder(this);
    }

    //===생성 메서드===
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order(member, delivery, OrderStatus.ORDER);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        return order;
    }

    //===비즈니스 메서드===

    public void cancel() {
        if (this.orderStatus == OrderStatus.CANCEL) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 주문 총 가격
     * @return
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalOrderPrice();
        }

        return totalPrice;
    }
}