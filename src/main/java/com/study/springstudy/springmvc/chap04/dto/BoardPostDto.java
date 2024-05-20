package com.study.springstudy.springmvc.chap04.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@EqualsAndHashCode
public class BoardPostDto {

    private String title;// 글제목
    private String content;// 내용
    private String writer; // 작성자

}
