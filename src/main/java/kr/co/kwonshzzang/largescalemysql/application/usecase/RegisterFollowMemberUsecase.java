package kr.co.kwonshzzang.largescalemysql.application.usecase;

import kr.co.kwonshzzang.largescalemysql.domain.follow.entity.Follow;
import kr.co.kwonshzzang.largescalemysql.domain.follow.service.FollowWriteService;
import kr.co.kwonshzzang.largescalemysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterFollowMemberUsecase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;


    public Follow execute(Long fromMemberId, Long toMemberId) {
        /**
         * 1. 입력받은 memberId로 회원조회
         * 2. FollowWriteService.register()
         */
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);
        return followWriteService.register(fromMember, toMember);
    }
}
