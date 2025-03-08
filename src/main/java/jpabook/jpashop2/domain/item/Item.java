package jpabook.jpashop2.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop2.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Item extends BaseEntity {

    protected Item(String name, int price, int stockQuantity) {
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
     * 재고 증가 (주문 취소 시)
     * @param count
     */
    public void addStock(int count) {
        stockQuantity += count;
    }

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

    /**
     * 상품 수정
     */
    public void update(String name, int price, int stockQuantity) {
        if (0 > price || 1_000_000 < price) {
            throw new IllegalStateException("상품 가격은 0 이상 1,000,000 이하여야 합니다.");
        }

        if (stockQuantity < 0) {
            throw new IllegalArgumentException("재고 수량은 0보다 작을 수 없습니다.");
        }

        this.price = price;
        this.name = name;
        this.stockQuantity = stockQuantity;
    }
}
