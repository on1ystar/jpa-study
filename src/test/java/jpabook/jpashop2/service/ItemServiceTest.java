package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.item.Book;
import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.dto.ItemUpdateDto;
import jpabook.jpashop2.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품등록() {
        //given
        Item item1 = new Book("jpa", 1000, 10, "회원1", "ㅇㅇ");

        //when
        Long register = itemService.register(item1);

        //then
        Item findItem = itemRepository.findById(register);
        assertThat(findItem).isEqualTo(item1);
    }

    @Test
    public void 상품목록() {
        //given
        Item item1 = new Book("jpa1", 1000, 10, "회원1", "ㅇㅇ");
        Item item2 = new Book("jpa2", 1000, 10, "회원1", "ㅇㅇ");

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> items = itemService.findItems();

        //then
        assertThat(items).hasSize(2);
    }

    @Test
    public void 상품수정() {
        //given
        Item item1 = new Book("jpa", 1000, 10, "회원1", "ㅇㅇ");
        itemRepository.save(item1);

        ItemUpdateDto itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setId(item1.getId());
        itemUpdateDto.setName("qwer");
        itemUpdateDto.setPrice(1);
        itemUpdateDto.setStockQuantity(1);

        //when
        itemService.update(itemUpdateDto);

        //then
        Item findItem = itemRepository.findById(item1.getId());
        assertThat(findItem.getName()).isEqualTo(itemUpdateDto.getName());
        assertThat(findItem.getPrice()).isEqualTo(itemUpdateDto.getPrice());
        assertThat(findItem.getStockQuantity()).isEqualTo(itemUpdateDto.getStockQuantity());
    }

    @Test
    public void 상품수정_실패() {
        //given
        Item item1 = new Book("jpa", 1000, 10, "회원1", "ㅇㅇ");
        itemRepository.save(item1);

        ItemUpdateDto itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setId(item1.getId());
        itemUpdateDto.setName("qwer");
        itemUpdateDto.setPrice(1);
        itemUpdateDto.setStockQuantity(-1);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> itemService.update(itemUpdateDto));

        //then
        assertThat(exception.getMessage()).isEqualTo("재고 수량은 0보다 작을 수 없습니다.");
    }
}