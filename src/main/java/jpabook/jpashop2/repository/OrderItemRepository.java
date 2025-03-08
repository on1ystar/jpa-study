package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final EntityManager em;

    public void save(OrderItem orderItem) {
        em.persist(em);
    }

    public OrderItem findById(Long id) {
        return em.find(OrderItem.class, id);
    }
}
