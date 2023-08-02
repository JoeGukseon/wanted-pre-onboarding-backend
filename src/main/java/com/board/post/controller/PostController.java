package com.board.post.controller;

import com.board.auth.jwt.JwtTokenizer;
import com.board.dto.MultiResponseDto;
import com.board.post.dto.PostDto;
import com.board.post.entity.Post;
import com.board.post.mapper.PostMapper;
import com.board.post.service.PostService;
import com.board.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Validated
public class PostController {
    private static final String COMPANION_DEFAULT_URL = "/posts";
    private final PostMapper postMapper;
    private final PostService postService;
    private final JwtTokenizer jwtTokenizer;

    @PostMapping
    public ResponseEntity<Void> postCompanion(@Valid @RequestBody PostDto.Post requestBody,
                                              @RequestHeader("Authorization") String accessToken) {
        Long memberId = jwtTokenizer.extractMemberIdFromAccessToken(accessToken.replace("Bearer ", ""));
        Post post = postMapper.postPostToPost(requestBody,memberId);

        Post createPost = postService.createPost(post);
        URI location = UriCreator.createUri(COMPANION_DEFAULT_URL, createPost.getPostId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<PostDto.Response>> getCompanions(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                                            @RequestParam(value = "sortDir", required = false, defaultValue = "DESC") String sortDir,
                                                                            @RequestParam(value = "sortBy", required = false, defaultValue = "postId") String sortBy) {
        Page<Post> postPage = postService.findPosts(page - 1, size, sortDir, sortBy);
        List<Post> posts = postPage.getContent();
        return ResponseEntity.ok(new MultiResponseDto<>(postMapper.postsToPostResponses(posts), postPage));
    }


}
