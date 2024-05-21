package com.study.springstudy.springmvc.chap04.dto;

import com.study.springstudy.springmvc.chap04.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 서버에서 조회한 데이터중 화면에 필요한 데이터만 모아놓은 클래스
@Getter @Setter @ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponseDto {

    private int boardNo;
    private String title;
    private String content;
    private String date;
    private int view;
    private boolean hit;
    private boolean newArticle;


    public BoardListResponseDto(Board b) {
        this.boardNo = b.getBoardNo();
        this.title = b.getTitle();
        this.content = b.getContent();
        this.date = dateFormatting(b.getRegDateTime());
        this.view = b.getViewCount();
        this.hit = b.getViewCount() >= 5;
        this.newArticle = !(b.getRegDateTime().plusHours(1).isBefore(LocalDateTime.now()));
    }

    private String dateFormatting(LocalDateTime regDateTime) {
        DateTimeFormatter pattern
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return pattern.format(regDateTime);
    }
}
