package jpabook.jpashop2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter @Setter
public class ItemUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @Range(min = 0, max = 1_000_000)
    private int price;

    @Min(0)
    private int stockQuantity;
}
