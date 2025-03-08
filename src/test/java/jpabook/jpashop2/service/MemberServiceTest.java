package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given
        Member member1 = new Member("회원1");

        //when
        Long joined = memberService.join(member1);

        //then
        Member findMember = memberRepository.findById(joined);
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    public void 중복_회원가입() {
        //given
        Member member1 = new Member("회원1");
        Member member2 = new Member("회원1");
        memberService.join(member1);

        //when
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 회원조회() {
        //given
        Member member1 = new Member("회원1");
        memberRepository.save(member1);

        //when
        Member findMember = memberService.findMember(member1.getId());

        //then
        assertThat(findMember).isEqualTo(member1);
        assertThat(findMember.getName()).isEqualTo(member1.getName());
    }
}