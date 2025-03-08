package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.domain.item.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 회원에_해당하는_주문내역_조회() {
        //given
        Address address = new Address("city", "street", "1231");
        Member member = new Member("회원1", address);
        em.persist(member);

        Delivery delivery1 = new Delivery(address, DeliveryStatus.DELIVERY);
        Delivery delivery2 = new Delivery(address, DeliveryStatus.DELIVERY);
        em.persist(delivery1);
        em.persist(delivery2);

        Order order1 = new Order(member, delivery1, OrderStatus.ORDER);
        Order order2 = new Order(member, delivery2, OrderStatus.ORDER);
        em.persist(order1);
        em.persist(order2);

        Book book1 = new Book("book1", 1000, 10, "ooo", "ooo");
        Book book2 = new Book("book2", 1000, 10, "ooo", "ooo");
        em.persist(book1);
        em.persist(book2);

        OrderItem orderItem1 = new OrderItem(order1, book1, 1000, 10);
        OrderItem orderItem2 = new OrderItem(order1, book2, 1000, 10);
        em.persist(orderItem1);
        em.persist(orderItem2);

        //when
        List<Order> findOrders = orderRepository.findByMember(member);

        //then
        Assertions.assertThat(findOrders.size()).isEqualTo(2);
    }
}