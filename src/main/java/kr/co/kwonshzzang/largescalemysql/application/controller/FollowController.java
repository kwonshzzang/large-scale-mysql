package kr.co.kwonshzzang.largescalemysql.application.controller;

import kr.co.kwonshzzang.largescalemysql.application.usecase.GetFollowingMembersUsecase;
import kr.co.kwonshzzang.largescalemysql.application.usecase.RegisterFollowMemberUsecase;
import kr.co.kwonshzzang.largescalemysql.domain.follow.dto.FollowDto;
import kr.co.kwonshzzang.largescalemysql.domain.follow.service.FollowReadService;
import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final RegisterFollowMemberUsecase registerFollowMemberUsecase;
    private final GetFollowingMembersUsecase getFollowingMembersUsecase;
    private final FollowReadService followReadService;

    @PostMapping("/{fromMemberId}/{toMemberId}")
    public FollowDto registerFollow(@PathVariable Long fromMemberId, @PathVariable Long toMemberId) {
        var follow =  registerFollowMemberUsecase.execute(fromMemberId, toMemberId);
        return followReadService.toFollowDto(follow);
    }

    @GetMapping("/members/{memberId}")
    public List<MemberDto> getFollowingMembers(@PathVariable Long memberId) {
        return getFollowingMembersUsecase.execute(memberId);
    }
}
