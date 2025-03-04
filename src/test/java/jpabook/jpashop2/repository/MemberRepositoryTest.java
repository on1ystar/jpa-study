package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.dmain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    void saveMemberTest() {
        //given
        Member member1 = new Member("member1");

        //when
        memberRepository.save(member1);

        //then
        Member findMember = em.find(Member.class, member1.getId());
        assertThat(findMember.getId()).isEqualTo(member1.getId());
        assertThat(findMember.getName()).isEqualTo(member1.getName());

    }
}