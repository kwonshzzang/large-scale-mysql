package kr.co.kwonshzzang.largescalemysql.controller;

import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.domain.member.dto.RegisterMemberCommand;
import kr.co.kwonshzzang.largescalemysql.domain.member.entity.Member;
import kr.co.kwonshzzang.largescalemysql.domain.member.service.MemberReadService;
import kr.co.kwonshzzang.largescalemysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping("/members")
    public MemberDto registerMember(@RequestBody RegisterMemberCommand command) {
        var member =  memberWriteService.register(command);
        return memberReadService.toMemberDto(member);
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return memberReadService.getMember(id);
    }
}
