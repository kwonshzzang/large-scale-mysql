package kr.co.kwonshzzang.largescalemysql.domain.follow.service;

import kr.co.kwonshzzang.largescalemysql.domain.follow.entity.Follow;
import kr.co.kwonshzzang.largescalemysql.domain.follow.repository.FollowRepository;
import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
@RequiredArgsConstructor
public class FollowWriteService {
    private final FollowRepository followRepository;

    public Follow register(MemberDto fromMember, MemberDto toMember) {
        Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일한다.");
        var follow =Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();
        return followRepository.save(follow);
    }
}
