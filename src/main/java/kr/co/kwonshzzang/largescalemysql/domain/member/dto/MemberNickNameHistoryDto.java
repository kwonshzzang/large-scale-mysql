package kr.co.kwonshzzang.largescalemysql.domain.member.dto;

import java.time.LocalDateTime;

public record MemberNickNameHistoryDto(
        Long id,
        Long memberId,
        String nickname,
        LocalDateTime createdAt
) {
}
