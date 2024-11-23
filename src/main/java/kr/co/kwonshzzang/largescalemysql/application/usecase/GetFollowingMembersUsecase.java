package kr.co.kwonshzzang.largescalemysql.application.usecase;

import kr.co.kwonshzzang.largescalemysql.domain.follow.entity.Follow;
import kr.co.kwonshzzang.largescalemysql.domain.follow.service.FollowReadService;
import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingMembersUsecase {
    private final FollowReadService followReadService;
    private final MemberReadService memberReadService;

    public List<MemberDto> execute(Long memberId) {
        var followings = followReadService.getFollowings(memberId);
        var followingsMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingsMemberIds);
    }
}
