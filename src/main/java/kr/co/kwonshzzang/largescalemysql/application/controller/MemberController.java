package kr.co.kwonshzzang.largescalemysql.application.controller;

import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberNickNameHistoryDto;
import kr.co.kwonshzzang.largescalemysql.domain.member.dto.RegisterMemberCommand;
import kr.co.kwonshzzang.largescalemysql.domain.member.entity.Member;
import kr.co.kwonshzzang.largescalemysql.domain.member.service.MemberReadService;
import kr.co.kwonshzzang.largescalemysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping("")
    public MemberDto registerMember(@RequestBody RegisterMemberCommand command) {
        var member =  memberWriteService.register(command);
        return memberReadService.toMemberDto(member);
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return memberReadService.getMember(id);
    }

    @GetMapping("/{memberId}/nicknamehistories")
    public List<MemberNickNameHistoryDto> getMemberNickNameHistoris(@PathVariable Long memberId) {
        return  memberReadService.getNicknameHistories(memberId);
    }

    @PutMapping("/{id}/name")
    public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        memberWriteService.changeNickname(id, nickname);
        return memberReadService.getMember(id);
    }
}
