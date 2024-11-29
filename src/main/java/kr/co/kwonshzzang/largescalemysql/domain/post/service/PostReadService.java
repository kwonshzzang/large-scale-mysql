package kr.co.kwonshzzang.largescalemysql.domain.post.service;

import kr.co.kwonshzzang.largescalemysql.domain.post.dto.DailyPostCount;
import kr.co.kwonshzzang.largescalemysql.domain.post.dto.DailyPostCountRequest;
import kr.co.kwonshzzang.largescalemysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService {
    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /**
         * 반환 값 -> 리스트(작성일자, 작성회원, 작성 게시물 갯수)
         * select memberId, createdDate, count(id)
         * from post
         * where memberId = :memberId
         * and createdDate between :firstDate and :lastDate
         * group by createdDate, memberId
         */
        return postRepository.groupByCreatedDate(request);

    }
}
