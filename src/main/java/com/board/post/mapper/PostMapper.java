package com.board.post.mapper;

import com.board.member.entity.Member;
import com.board.post.dto.PostDto;
import com.board.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "member", source = "requestBody.memberId")
    Post postPostToPost(PostDto.Post requestBody);

    @Mapping(target = "memberId", source = "post.member.memberId")
    PostDto.Response postToPostResponse(Post post);
    List<PostDto.Response> postsToPostResponses(List<Post> posts);

    default Member mapMemberIdToMember(Long memberId) {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}