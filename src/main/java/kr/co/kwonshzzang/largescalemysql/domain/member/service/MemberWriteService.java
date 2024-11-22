package kr.co.kwonshzzang.largescalemysql.domain.member.service;

import kr.co.kwonshzzang.largescalemysql.domain.member.dto.RegisterMemberCommand;
import kr.co.kwonshzzang.largescalemysql.domain.member.entity.Member;
import kr.co.kwonshzzang.largescalemysql.domain.member.entity.MemberNicknameHistory;
import kr.co.kwonshzzang.largescalemysql.domain.member.repository.MemberNicknameHistoryRepository;
import kr.co.kwonshzzang.largescalemysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member register(RegisterMemberCommand command) {
        /**
         * 목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다.
         *      - 닉네임은 10자를 넘길 수 없다.
         * 파라미터 - memberRegisterCommand

         * val member = Member.of(memberRegisterCommand)
         * memberRepository.save(member)
         */
        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();
        var savedMember = memberRepository.save(member);

        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname) {
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        var savedMember = memberRepository.save(member);

       saveMemberNicknameHistory(savedMember);
    }

    private void saveMemberNicknameHistory(Member member) {
        var memberHistory = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryRepository.save(memberHistory);

    }
}
