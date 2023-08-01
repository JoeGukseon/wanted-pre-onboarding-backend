package com.board.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;

public class MemberDto {
    @Getter
    @Builder
    public static class Post {
        @Pattern(regexp = ".*@.*", message = "이메일에 @가 포함되어있지 않습니다")
        private String email;

        @Pattern(regexp = "^.{8,}$", message = "비밀번호는 8자 이상이여야 합니다.")
        private String password;
    }
}
