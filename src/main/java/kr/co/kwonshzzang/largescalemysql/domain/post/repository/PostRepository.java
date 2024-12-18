package kr.co.kwonshzzang.largescalemysql.domain.post.repository;

import kr.co.kwonshzzang.largescalemysql.domain.post.dto.DailyPostCount;
import kr.co.kwonshzzang.largescalemysql.domain.post.dto.DailyPostCountRequest;
import kr.co.kwonshzzang.largescalemysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String TABLE =  "Post";
    private final RowMapper<Post> rowMapper = (ResultSet rs, int rowNum) ->
            Post.builder()
                    .id(rs.getLong("id"))
                    .memberId(rs.getLong("memberId"))
                    .contents(rs.getString("contents"))
                    .createdDate(rs.getObject("createdDate", LocalDate.class))
                    .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                    .build();

    private final RowMapper<DailyPostCount> DAILY_POST_COUNT_ROWMAPPER = (ResultSet rs, int rowNum) ->
            new DailyPostCount(
                    rs.getLong("memberId"),
                    rs.getObject("createdDate", LocalDate.class),
                    rs.getLong("cnt")
            );

    public Post save(Post post) {
        if(post.getId() == null) {
            return insert(post);
        }
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    public void bulkInsert(List<Post> posts) {
        var sql = String.format("INSERT INTO %s" +
                " (memberId, contents, createdDate, createdAt)" +
                "VALUES (:memberId, :contents, :createdDate, :createdAt)"
                ,TABLE);

        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        var sql = String.format("SELECT createdDate, memberId, count(id) as cnt" +
                                " FROM %s" +
                                " WHERE memberId = :memberId AND" +
                                " createdDate BETWEEN :firstDate AND :lastDate" +
                                " GROUP BY createdDate, memberId ",TABLE);

        var params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_ROWMAPPER);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }


}
