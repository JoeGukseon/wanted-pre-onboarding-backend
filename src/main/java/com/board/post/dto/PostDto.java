package com.board.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

public class PostDto {
    @Getter
    @Setter
    public static class Post {
        private String title;
        private String content;
        @Positive
        private Long memberId;
    }

    @Getter
    @Setter
    public static class Response {
        private Long postId;
        private String title;
        private String content;
        private Long memberId;
    }

    @Getter
    @Setter
    public static class Patch {
        private Long postId;
        private String title;
        private String content;
    }
}
