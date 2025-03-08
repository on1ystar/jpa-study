package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import jpabook.jpashop2.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemCategory extends BaseEntity {

    public ItemCategory(Item item, Category category) {
        this.item = item;
        this.category = category;
    }

    @Id
    @GeneratedValue
    @Column(name = "item_category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
