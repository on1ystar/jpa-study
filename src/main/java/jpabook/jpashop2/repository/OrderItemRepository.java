package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<OrderItem> findByOrder(Order order) {
        return em.createQuery("select oi from OrderItem oi " +
                        "join fetch oi.item " +
                        "where oi.order = :order", OrderItem.class)
                .setParameter("order", order)
                .getResultList();
    }
}
