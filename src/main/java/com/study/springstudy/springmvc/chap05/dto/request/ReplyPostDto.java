package com.study.springstudy.springmvc.chap05.dto.request;

import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ReplyPostDto {

    private String text; // 댓글 내용
    private String author; //댓글작성자
    private long bno; // 원본 글번호
}
