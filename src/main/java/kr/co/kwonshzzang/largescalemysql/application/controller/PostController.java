package kr.co.kwonshzzang.largescalemysql.application.controller;

import jakarta.validation.Valid;
import kr.co.kwonshzzang.largescalemysql.domain.post.dto.DailyPostCount;
import kr.co.kwonshzzang.largescalemysql.domain.post.dto.DailyPostCountRequest;
import kr.co.kwonshzzang.largescalemysql.domain.post.dto.PostCommand;
import kr.co.kwonshzzang.largescalemysql.domain.post.service.PostReadService;
import kr.co.kwonshzzang.largescalemysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;

    @PostMapping
    public Long save(@RequestBody PostCommand command) {
        return postWriteService.save(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@Valid DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }
}
