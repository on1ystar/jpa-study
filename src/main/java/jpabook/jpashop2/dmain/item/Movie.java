package jpabook.jpashop2.dmain.item;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Movie extends Item {

    public Movie(String name, int price, int stockQuantity, String director, String screenwriter) {
        super(name, price, stockQuantity);
        this.director = director;
        this.screenwriter = screenwriter;
    }

    private String director;
    private String screenwriter;
}
