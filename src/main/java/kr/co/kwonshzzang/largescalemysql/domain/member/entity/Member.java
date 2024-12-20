package kr.co.kwonshzzang.largescalemysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    private final Long id;
    private String nickname;
    private final String email;
    private final LocalDate birthday;
    private final LocalDateTime createdAt;

    private static final Long NICKNAME_MAX_LENGTH = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        this.id = id;

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void changeNickname(String nickname) {
        Objects.requireNonNull(nickname);
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NICKNAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }
}
