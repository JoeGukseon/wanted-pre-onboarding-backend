package com.board.member.mapper;

import com.board.member.dto.MemberDto;
import com.board.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostToMember(MemberDto.Post requestBody);
}
