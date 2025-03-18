package jpabook.jpashop2;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.domain.item.Book;
import jpabook.jpashop2.domain.item.Item;
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
            Member member = createMember("회원1", "도시1", "거리1", "11111");
            em.persist(member);

            Item book1 = createBook("책1", 10000, 10, "작가1", "기타1");
            Item book2 = createBook("책2", 10000, 10, "작가2", "기타2");
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 1);

            Delivery delivery = createDelivery(member.getAddress(), DeliveryStatus.READY);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = em.find(Member.class, 1L);

            Item book3 = createBook("책3", 20000, 100, "작가3", "기타3");
            Item book4 = createBook("책4", 20000, 100, "작가4", "기타4");
            em.persist(book3);
            em.persist(book4);

            OrderItem orderItem1 = OrderItem.createOrderItem(book3, book3.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book4, book4.getPrice(), 1);

            Delivery delivery = createDelivery(member.getAddress(), DeliveryStatus.READY);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            return new Member(name, new Address(city, street, zipcode));
        }

        private Item createBook(String name, int price, int stockQuantity, String artist, String etc) {
            return new Book(name, price, stockQuantity, artist, etc);
        }

        private Delivery createDelivery(Address address, DeliveryStatus deliveryStatus) {
            return new Delivery(address, deliveryStatus);
        }
    }
}
