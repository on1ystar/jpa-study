package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findById(Long id) {
        Order order = em.find(Order.class, id);

        if (order == null) {
            throw new IllegalStateException("id에 해당하는 회원이 없습니다.");
        }

        return order;
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Orders o", Order.class)
                .getResultList();
    }
}
