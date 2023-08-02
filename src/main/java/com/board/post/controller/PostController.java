package com.board.post.controller;

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

    @PostMapping
    public ResponseEntity<Void> postCompanion(@Valid @RequestBody PostDto.Post requestBody) {
        Post post = postMapper.postPostToPost(requestBody);

        Post createPost = postService.createPost(post);
        URI location = UriCreator.createUri(COMPANION_DEFAULT_URL, createPost.getPostId());
        return ResponseEntity.created(location).build();
    }


}
