package com.board.post.mapper;

import com.board.member.entity.Member;
import com.board.post.dto.PostDto;
import com.board.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "member", source = "memberId")
    Post postPostToPost(PostDto.Post requestBody,Long memberId);

    @Mapping(target = "memberId", source = "post.member.memberId")
    PostDto.Response postToPostResponse(Post post);
    List<PostDto.Response> postsToPostResponses(List<Post> posts);

    Post postPatchToPost(PostDto.Patch requestBody);

    default Member mapMemberIdToMember(Long memberId) {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}
