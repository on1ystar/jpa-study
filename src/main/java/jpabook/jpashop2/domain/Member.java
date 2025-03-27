package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    public Member(String name) {
        this.name = name;
    }

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
}
