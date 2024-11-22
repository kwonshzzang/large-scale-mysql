package kr.co.kwonshzzang.largescalemysql.domain.member.repository;

import kr.co.kwonshzzang.largescalemysql.domain.member.entity.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String TABLE = "MemberNicknameHistory";

    private final RowMapper<MemberNicknameHistory> rowMapper = (ResultSet rs, int rowNum) ->
            MemberNicknameHistory.builder()
                    .id(rs.getLong("id"))
                    .memberId(rs.getLong("memberId"))
                    .nickname(rs.getString("nickname"))
                    .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                    .build();

    public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory) {
        /**
         *  Member Id를 보고 갱신 또는 삽입을 정함.
         *  반환값은 ID를 담아서 반환한다.
         *
         */
        if (memberNicknameHistory.getId() == null) {
            return insert(memberNicknameHistory);
        }
        throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다. ");
    }

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
        var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE);
        var params = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    private MemberNicknameHistory insert(MemberNicknameHistory memberNicknameHistory) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(memberNicknameHistory);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return memberNicknameHistory.builder()
                .id(id)
                .memberId(memberNicknameHistory.getMemberId())
                .nickname(memberNicknameHistory.getNickname())
                .createdAt(memberNicknameHistory.getCreatedAt())
                .build();
    }
}
