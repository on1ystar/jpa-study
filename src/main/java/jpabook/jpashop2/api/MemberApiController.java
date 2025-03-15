package jpabook.jpashop2.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public SaveMemberResponse saveMember(@RequestBody @Valid SaveMemberRequest request) {
        Long savedMemberId = memberService.join(new Member(request.getName(), request.getAddress()));
        return new SaveMemberResponse(savedMemberId);
    }

    @GetMapping("/api/members")
    public Result<List<MemberDto>> members() {
        List<MemberDto> dtoList = memberService.findMembers().stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result<>(dtoList);
    }

    @PatchMapping("/api/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id,
                                             @RequestBody @Valid UpdateMemberRequest request) {
        memberService.updateName(id, request.getName());
        Member findMember = memberService.findMember(id);
        return new UpdateMemberResponse(findMember.getName());
    }

    @AllArgsConstructor
    @Getter
    @Setter
    static class MemberDto {

        private String name;
    }


    @AllArgsConstructor
    @Getter
    @Setter
    static class SaveMemberRequest {

        @NotBlank
        private String name;

        @NotNull
        private Address address;
    }

    @AllArgsConstructor
    @Getter @Setter
    static class SaveMemberResponse {

        @NotBlank
        private Long id;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    static class UpdateMemberRequest {

        private String name;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    static class UpdateMemberResponse {

        private String updatedName;
    }
}
