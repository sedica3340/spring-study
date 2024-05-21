package com.study.springstudy.springmvc.chap03.mapper;


import com.study.springstudy.springmvc.chap03.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ScoreMapper {

    // 저장소에 데이터 추가하기
    boolean save(Score score);


    // 저장소에서 데이터 전체조회하기
    List<Score> findAll(String sort);
    // 저장소에서 데이터 개별조회하기

    Score findOne(Long stuNum);

    // 저장소에서 데이터 삭제하기
    boolean remove(long stuNum);

    // 저장소에서 국영수 점수 수정하기
    boolean updateScore(Score s);

//    List<Integer> findRankByStuNum(long stuNum);

    default String ranking(Score score) {
        List<Score> scoreList = findAll("avg");
        List<Score> rankingList = scoreList.stream()
                .sorted(Comparator.comparing(Score::getAverage).reversed())
                .collect(Collectors.toList());
        int rank = -1;
        for (int i = 0; i < rankingList.size(); i++) {
            if (rankingList.get(i).getStuNum() == (score.getStuNum())) {
                rank = i + 1;
            }
        }
        return rank + "/" + rankingList.size();
    }


}
