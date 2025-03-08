package jpabook.jpashop2.dmain;

import jakarta.persistence.*;
import jpabook.jpashop2.dmain.item.Item;
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

    public Order(Member member, Delivery delivery, OrderStatus orderStatus) {
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

    //===비즈니스 메서드===

    /**
     * 주문 취소 시 각 아이템의 재고 증가
     */
    public void cancel() {
        this.orderStatus = OrderStatus.CANCEL;

        for (OrderItem orderItem : orderItems) {
            Item item = orderItem.getItem();
            item.addStock(orderItem.getCount());
        }
    }
}