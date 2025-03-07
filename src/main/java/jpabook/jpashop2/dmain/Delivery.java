package jpabook.jpashop2.dmain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
public class Delivery extends BaseEntity {

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
}
