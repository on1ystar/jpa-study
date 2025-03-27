package jpabook.jpashop2.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public SaveMemberResponse saveMember(@RequestBody @Valid SaveMemberRequest request) {
        Long savedMemberId = memberService.join(new Member(request.getName()));
        return new SaveMemberResponse(savedMemberId);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    static class SaveMemberRequest {

        @NotBlank
        private String name;
    }

    @AllArgsConstructor
    @Getter @Setter
    static class SaveMemberResponse {

        @NotNull
        private Long id;
    }
}
