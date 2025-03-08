package jpabook.jpashop2.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Address {

    private String city;
    private String street;
    private String zipcode;
}