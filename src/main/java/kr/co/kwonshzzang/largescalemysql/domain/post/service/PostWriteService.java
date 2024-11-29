package kr.co.kwonshzzang.largescalemysql.domain.post.service;

import kr.co.kwonshzzang.largescalemysql.domain.post.dto.PostCommand;
import kr.co.kwonshzzang.largescalemysql.domain.post.entity.Post;
import kr.co.kwonshzzang.largescalemysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteService {
    private final PostRepository postRepository;

    public Long save(PostCommand command) {
        var post = Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();
        return postRepository.save(post).getId();
    }
}
