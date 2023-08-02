package com.board.post.service;

import com.board.member.service.MemberService;
import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final MemberService memberService;
    private final PostRepository postRepository;
    public Post createPost(Post post) {
        memberService.findMember(post.getMember().getMemberId());

        return postRepository.save(post);
    }

    public Page<Post> findPosts(int page, int size, String sortDir, String sortBy) {
        PageRequest request;

        request = PageRequest.of(page, size, Sort.Direction.valueOf(sortDir), sortBy);

        return postRepository.findAll(request);
    }
}
