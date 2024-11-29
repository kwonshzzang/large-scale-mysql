package kr.co.kwonshzzang.largescalemysql.domain.post.dto;

public record PostCommand(
        Long memberId,
        String contents
) {
}
