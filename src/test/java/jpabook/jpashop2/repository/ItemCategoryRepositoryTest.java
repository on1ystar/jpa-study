package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Category;
import jpabook.jpashop2.domain.ItemCategory;
import jpabook.jpashop2.domain.item.Book;
import jpabook.jpashop2.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemCategoryRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    ItemCategoryRepository repository;

    @Test
    void 카테고리로_아이템_조회() {
        //given
        Book book1 = new Book("book1", 1000, 10, "ooo", "ooo");
        Book book2 = new Book("book2", 1000, 10, "ooo", "ooo");
        em.persist(book1);
        em.persist(book2);

        Category category = new Category("책");
        em.persist(category);

        ItemCategory itemCategory1 = new ItemCategory(book1, category);
        ItemCategory itemCategory2 = new ItemCategory(book2, category);
        em.persist(itemCategory1);
        em.persist(itemCategory2);

        //when
        List<ItemCategory> findItemCategories = repository.findItemByCategory(category);

        //then
        Assertions.assertThat(findItemCategories.size()).isEqualTo(2);
        for (ItemCategory findItemCategory : findItemCategories) {
            System.out.println("findItemCategory.getItem().getName() = " + findItemCategory.getItem().getName());
        }
    }
}