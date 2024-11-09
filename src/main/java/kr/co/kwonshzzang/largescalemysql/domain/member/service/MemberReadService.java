package kr.co.kwonshzzang.largescalemysql.domain.member.service;

import kr.co.kwonshzzang.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.domain.member.entity.Member;
import kr.co.kwonshzzang.largescalemysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberRepository memberRepository;

    public MemberDto getMember(Long id) {
        var member = memberRepository.findById(id).orElseThrow(null);
        return toMemberDto(member);
    }

    public MemberDto toMemberDto(Member member) {
        return new MemberDto(
                member.getId(), member.getEmail(), member.getNickname(), member.getBirthday()
        );
    }
}