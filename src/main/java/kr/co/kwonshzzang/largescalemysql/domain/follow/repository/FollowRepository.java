package kr.co.kwonshzzang.largescalemysql.domain.follow.repository;

import kr.co.kwonshzzang.largescalemysql.domain.follow.entity.Follow;
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
public class FollowRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String TABLE =  "Follow";
    private final RowMapper<Follow> rowMapper = (ResultSet rs, int rowNum) ->
            Follow.builder()
                    .id(rs.getLong("id"))
                    .fromMemberId(rs.getLong("fromMemberId"))
                    .toMemberId(rs.getLong("toMemberId"))
                    .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                    .build();


    public Follow save(Follow follow) {
        if(follow.getId() == null) {
            return insert(follow);
        }
        throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다. ");
    }

    public List<Follow> findAllByFromMemberId(Long fromMemberId) {
        var sql = String.format("SELECT * FROM %s WHERE fromMemberId = :fromMemberId", TABLE);
        var params = new MapSqlParameterSource("fromMemberId", fromMemberId);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    private Follow insert(Follow follow) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Follow.builder()
                .id(id)
                .fromMemberId(follow.getFromMemberId())
                .toMemberId(follow.getToMemberId())
                .createdAt(follow.getCreatedAt())
                .build();
    }
}
