package com.board.post.service;

import com.board.exception.BusinessLogicException;
import com.board.exception.ExceptionCode;
import com.board.member.service.MemberService;
import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Post findPost(Long postId) {
        return findVerifiedPostById(postId);
    }
    private Post findVerifiedPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        return optionalPost.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    public Post updatePost(Post post, Long memberId) {
        Post findPost = findPost(post.getPostId());

        if (!findPost.getMember().getMemberId().equals(memberId)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_MEMBER);
        }

        Optional.ofNullable(post.getTitle()).ifPresent(findPost::setTitle);
        Optional.ofNullable(post.getContent()).ifPresent(findPost::setContent);

        return findPost;
    }

    public void deletePost(Long postId, Long memberId) {
        Post findPost = findVerifiedPostById(postId);

        if (!findPost.getMember().getMemberId().equals(memberId)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_MEMBER);
        }

        postRepository.deleteById(postId);
    }
}
