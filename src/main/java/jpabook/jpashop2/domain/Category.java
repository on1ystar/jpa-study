package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category extends BaseEntity {

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parent) {
        this.name = name;

        setParent(parent);
    }

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;

    //===연관관계 편의 메서드===
    private void setParent(Category category) {
        this.parent = category;
        category.getChildren().add(this);
    }
}
