package jpabook.jpashop2.dmain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {

    protected Member() {
    }

    public Member(String name) {
        this.name = name;
    }

    @Id @GeneratedValue
    private Long id;

    private String name;
}
