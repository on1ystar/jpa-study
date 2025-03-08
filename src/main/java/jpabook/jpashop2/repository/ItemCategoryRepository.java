package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Category;
import jpabook.jpashop2.domain.ItemCategory;
import jpabook.jpashop2.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemCategoryRepository {

    private final EntityManager em;

    public void save(ItemCategory itemCategory) {
        em.persist(itemCategory);
    }

    public ItemCategory findById(Long id) {
        return em.find(ItemCategory.class, id);
    }

    public List<ItemCategory> findItemByCategory(Category category) {
        return em.createQuery("select ic from ItemCategory ic " +
                        "join fetch ic.item " +
                        "where ic.category = :category", ItemCategory.class)
                .setParameter("category", category)
                .getResultList();
    }
}