package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.domain.item.Book;
import jpabook.jpashop2.dto.OrderItemDto;
import jpabook.jpashop2.repository.ItemRepository;
import jpabook.jpashop2.repository.MemberRepository;
import jpabook.jpashop2.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private ItemRepository itemRepository;

    @Test
    public void 주문생성() {
        //given
        Member member = new Member("회원1");
        memberRepository.save(member);

        Book book1 = new Book("아이템1", 1000, 10, "", "");
        Book book2 = new Book("아이템2", 1000, 10, "", "");
        itemRepository.save(book1);
        itemRepository.save(book2);

        List<OrderItemDto> dtoList = new ArrayList<>();
        dtoList.add(new OrderItemDto(book1.getId(), book1.getPrice(), 5));
        dtoList.add(new OrderItemDto(book2.getId(), book2.getPrice(), 5));

        //when
        Long orderedId = orderService.order(member.getId(), dtoList);

        //then
        Order findOrder = orderRepository.findById(orderedId);
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems()).hasSize(2);
        assertThat(findOrder.getTotalPrice())
                .isEqualTo(findOrder.getOrderItems().get(0).getTotalOrderPrice()
                        + findOrder.getOrderItems().get(1).getTotalOrderPrice());
    }

    @Test
    public void 주문_취소() {
        //given
        Member member = new Member("회원1");
        memberRepository.save(member);

        Book book1 = new Book("아이템1", 1000, 10, "", "");
        itemRepository.save(book1);

        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(book1, book1.getPrice(), 10);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        //when
        orderService.cancelOrder(order.getId());

        //then
        Order findOrder = orderRepository.findById(order.getId());
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    public void 이미_취소된_주문은_취소_불가() {
        //given
        Member member = new Member("회원1");
        memberRepository.save(member);

        Book book1 = new Book("아이템1", 1000, 10, "", "");
        itemRepository.save(book1);

        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(book1, book1.getPrice(), 10);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        orderService.cancelOrder(order.getId());

        //when
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> orderService.cancelOrder(order.getId()));

        //then
        assertThat(ex.getMessage()).isEqualTo("이미 취소된 주문입니다.");
    }
}