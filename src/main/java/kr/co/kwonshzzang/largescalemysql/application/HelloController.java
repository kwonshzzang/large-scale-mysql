package kr.co.kwonshzzang.largescalemysql.application;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HelloController {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @GetMapping("/hello")
    public Integer hello() {
        return jdbcTemplate.queryForObject("SELECT 1", new MapSqlParameterSource(), Integer.class);
    }
}
