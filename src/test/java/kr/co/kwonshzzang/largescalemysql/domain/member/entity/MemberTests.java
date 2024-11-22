package kr.co.kwonshzzang.largescalemysql.domain.member.entity;

import kr.co.kwonshzzang.largescalemysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTests {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    void changeNickname() {
        var member = MemberFixtureFactory.create();
        var newNickname = "pnu";
        member.changeNickname(newNickname);
        assertThat(newNickname).isEqualTo(member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    void changeNicknameMaxLength() {
        var member = MemberFixtureFactory.create();
        var overMaxLengthNickname = "pnupnupunpunpunpun";

        assertThatThrownBy(() -> member.changeNickname(overMaxLengthNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최대 길이를 초과했습니다.");
    }

}