package com.study.springstudy.springmvc.chap03.dto;

import com.study.springstudy.springmvc.chap03.entity.Score;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScoreDetailResponseDto {
    // 상세정보 화면을 렌더링하기 위한 데이터
    private long stuNum;
    private String stuName;
    private int kor, eng, math, total;
    private double average;
    private String grade;
    private String rank;

    public ScoreDetailResponseDto(Score s, String rank) {
        this.stuNum = s.getStuNum();
        this.stuName = s.getStuName();
        this.kor = s.getKor();
        this.eng = s.getEng();
        this.math = s.getMath();
        this.total = s.getTotal();
        this.average = s.getAverage();
        this.grade = s.getGrade().toString();
        this.rank = rank;
    }
}
