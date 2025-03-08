package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.item.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 책_조회() {
        //given
        Book book = new Book("book1", 1000, 10, "ooo", "ooo");
        em.persist(book);

        em.flush();
        em.clear();

        //when
        Book findBook = itemRepository.findBookById(1L);

        //then
        Assertions.assertThat(findBook.getName()).isEqualTo("book1");
    }

}