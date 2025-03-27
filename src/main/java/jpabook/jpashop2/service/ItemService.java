package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Item;
import jpabook.jpashop2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     * @param item to register item
     * @return registered item id
     */
    @Transactional
    public Long register(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 상품 목록 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 상품 조회
     */
    public Item findItem(Long id) {
        return itemRepository.findById(id);
    }

}
