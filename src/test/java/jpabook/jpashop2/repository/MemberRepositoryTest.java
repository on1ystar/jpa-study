package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveMemberTest() {
        //given
        Address address = new Address("대전", "용문로", "3333");
        Member member1 = new Member("member1", address);

        //when
        memberRepository.save(member1);

        //then
        Member findMember = em.find(Member.class, member1.getId());
        assertThat(findMember.getId()).isEqualTo(member1.getId());
        assertThat(findMember.getName()).isEqualTo(member1.getName());

        for (Order order : findMember.getOrders()) {
            System.out.println("order.getMember() = " + order.getMember());
        }
    }
}