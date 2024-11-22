package kr.co.kwonshzzang.largescalemysql.util;

import kr.co.kwonshzzang.largescalemysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {

    public static Member create() {
        var params = new EasyRandomParameters();
        return new EasyRandom(params).nextObject(Member.class);
    }

    public static Member create(Long seed) {
        var params = new EasyRandomParameters().seed(seed);
        return new EasyRandom(params).nextObject(Member.class);
    }
}
