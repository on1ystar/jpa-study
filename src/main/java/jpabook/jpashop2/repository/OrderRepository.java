package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Member;
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
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    public List<Order> findOrdersWithMemberAndDeliveryByMemberId(Long memberId) {
        return em.createQuery("select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " where o.member.id = :memberId", Order.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
