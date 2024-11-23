package kr.co.kwonshzzang.largescalemysql.domain.follow.service;

import kr.co.kwonshzzang.largescalemysql.domain.follow.dto.FollowDto;
import kr.co.kwonshzzang.largescalemysql.domain.follow.entity.Follow;
import kr.co.kwonshzzang.largescalemysql.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowReadService {
    private final FollowRepository followRepository;

    public List<Follow> getFollowings(Long memberId) {
        return followRepository.findAllByFromMemberId(memberId);
    }

    public FollowDto toFollowDto(Follow follow) {
        return new FollowDto(
                follow.getId(), follow.getFromMemberId(), follow.getToMemberId(), follow.getCreatedAt()
        );
    }
}
