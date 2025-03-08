package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepository {

    private final EntityManager em;

    public void save(Delivery delivery) {
        em.persist(delivery);
    }

    public Delivery findById(Long id) {
        return em.find(Delivery.class, id);
    }
}
