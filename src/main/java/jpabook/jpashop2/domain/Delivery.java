package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery extends BaseEntity {

    public Delivery(Address address, DeliveryStatus deliveryStatus) {
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(value = STRING)
    private DeliveryStatus deliveryStatus;

    // ===연관관계 편의 메서드===
    public void setOrder(Order order) {
        this.order = order;
    }
}
