package jpabook.jpashop2;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("회원1");
            em.persist(member);

            Item book1 = createItem("책1", 10000, 10);
            Item book2 = createItem("책2", 10000, 10);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 1);

            Order order = Order.createOrder(member, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = em.find(Member.class, 1L);

            Item book3 = createItem("책3", 20000, 100);
            Item book4 = createItem("책4", 20000, 100);
            em.persist(book3);
            em.persist(book4);

            OrderItem orderItem1 = OrderItem.createOrderItem(book3, book3.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book4, book4.getPrice(), 1);

            Order order = Order.createOrder(member, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name) {
            return new Member(name);
        }

        private Item createItem(String name, int price, int stockQuantity) {
            return new Item(name, price, stockQuantity);
        }

    }
}
