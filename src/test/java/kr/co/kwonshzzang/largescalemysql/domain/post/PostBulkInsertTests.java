package kr.co.kwonshzzang.largescalemysql.domain.post;

import kr.co.kwonshzzang.largescalemysql.domain.post.entity.Post;
import kr.co.kwonshzzang.largescalemysql.domain.post.repository.PostRepository;
import kr.co.kwonshzzang.largescalemysql.util.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
class PostBulkInsertTests {
    @Autowired
    private PostRepository postRepository;

    @Test
    void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(3L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1));

        var createObjectTime = new StopWatch();
        createObjectTime.start();

        var posts = IntStream.range(0, 10000 * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        createObjectTime.stop();

        System.out.println("객체 생성 시간 : " + createObjectTime.getTotalTimeSeconds());

        var queryTime = new StopWatch();
        queryTime.start();
        postRepository.bulkInsert(posts);

        queryTime.stop();
        System.out.println("쿼리 시간 : " + queryTime.getTotalTimeSeconds());
    }
}
