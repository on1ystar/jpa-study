package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item{

    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private int price;

    private int stockQuantity;

    //===비즈니스 메서드===

    /**
     * 재고 감소 (주문 시)
     * @param count
     */
    public void removeStock(int count) {
        if (count > stockQuantity) {
            throw new IllegalArgumentException("입력하신 수량이 현 재고 수량을 초과했습니다.");
        }
        stockQuantity -= count;
    }
}