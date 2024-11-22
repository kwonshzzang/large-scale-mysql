package kr.co.kwonshzzang.largescalemysql.domain.member.repository;

import kr.co.kwonshzzang.largescalemysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String TABLE =  "Member";
    private final RowMapper<Member> rowMapper = (ResultSet rs, int rowNum) ->
            Member.builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .nickname(rs.getString("nickname"))
                    .birthday(rs.getObject("birthday", LocalDate.class))
                    .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                    .build();


    public Member save(Member member) {
        /**
         *  Member Id를 보고 갱신 또는 삽입을 정함.
         *  반환값은 ID를 담아서 반환한다.
         *
         */
        if(member.getId() == null) {
            return insert(member);
        }
        return  update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }


    private Member update(Member member) {
        var sql = String.format( "UPDATE %s SET email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE );
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, params);
        return member;
    }

    public Optional<Member> findById(long id) {
        /**
         *  select * from member where id = :id
         */
        var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var params = new MapSqlParameterSource().addValue("id", id);
        var member = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
        return Optional.ofNullable(member);
    }
}
