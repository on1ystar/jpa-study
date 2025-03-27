package jpabook.jpashop2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, Address address) {
        this.name = name;
    }

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    //===비즈니스 메서드===

    /**
     * 회원 이름 수정
     */
    public void updateName(String name) {
        this.name = name;
    }
}
