package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.dto.OrderItemDto;
import jpabook.jpashop2.repository.ItemRepository;
import jpabook.jpashop2.repository.MemberRepository;
import jpabook.jpashop2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 상품 주문
     */
    @Transactional
    public Long order(Long memberId, List<OrderItemDto> dtoList) {
        Member findMember = memberRepository.findById(memberId);

        Delivery delivery = new Delivery(findMember.getAddress(), DeliveryStatus.READY);

        OrderItem[] orderItems = new OrderItem[dtoList.size()];
        for (int i = 0; i < orderItems.length; i++) {
            OrderItemDto dto = dtoList.get(i);
            Item findItem = itemRepository.findById(dto.getItemId());
            orderItems[i] = OrderItem.createOrderItem(findItem, dto.getOrderPrice(), dto.getCount());
        }

        Order order = Order.createOrder(findMember, delivery, orderItems);

        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    public void cancelOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId);
        findOrder.cancel();
    }

//    public List<Order> findOrders() {
//
//    }

}
