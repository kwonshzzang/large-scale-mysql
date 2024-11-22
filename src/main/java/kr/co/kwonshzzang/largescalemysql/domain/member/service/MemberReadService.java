package kr.co.kwonshzzang.largescalemysql.domain.member.service;

import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberNickNameHistoryDto;
import kr.co.kwonshzzang.largescalemysql.domain.member.entity.Member;
import kr.co.kwonshzzang.largescalemysql.domain.member.entity.MemberNicknameHistory;
import kr.co.kwonshzzang.largescalemysql.domain.member.repository.MemberNicknameHistoryRepository;
import kr.co.kwonshzzang.largescalemysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long id) {
        var member = memberRepository.findById(id).orElseThrow(null);
        return toMemberDto(member);
    }

    public List<MemberNickNameHistoryDto> getNicknameHistories(Long memberId) {
        var  memberNicknameHistories = memberNicknameHistoryRepository.findAllByMemberId(memberId);
        return memberNicknameHistories.stream().map(this::toMemberNickNameHistoryDto).toList();
    }

    public MemberDto toMemberDto(Member member) {
        return new MemberDto(
                member.getId(), member.getEmail(), member.getNickname(), member.getBirthday()
        );
    }

    public MemberNickNameHistoryDto toMemberNickNameHistoryDto(MemberNicknameHistory memberNicknameHistory) {
        return new MemberNickNameHistoryDto(
                memberNicknameHistory.getId(), memberNicknameHistory.getMemberId(),
                memberNicknameHistory.getNickname(), memberNicknameHistory.getCreatedAt()
        );
    }

}