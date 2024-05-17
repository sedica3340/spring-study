package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ScoreSpringJdbcRepository implements ScoreRepository{

    private final JdbcTemplate template;

    @Override
    public boolean save(Score score) {
        String sql = "INSERT INTO tbl_score " +
                "(stu_name, kor, eng, math, total, average, grade) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql, score.getStuName(),
                score.getKor(), score.getEng(), score.getMath(),
                score.getTotal(), score.getAverage(), score.getGrade().toString()) == 1;
    }

    @Override
    public List<Score> findAll() {
        String sql = "SELECT * FROM tbl_score";
        return template.query(sql, (rs,rowNum) -> new Score(rs));
    }

    @Override
    public Score findOne(Long stuNum) {
        String sql = "SELECT * FROM tbl_score WHERE stu_Num = ?";

        return template.queryForObject(sql, (rs,rowNum)-> new Score(rs), stuNum);
    }

    @Override
    public String ranking(Score score) {
        List<Score> scoreList = findAll();
        List<Score> rankingList = scoreList.stream()
                .sorted(Comparator.comparing(Score::getAverage).reversed())
                .collect(Collectors.toList());
        int rank = -1;
        for (int i = 0; i < rankingList.size(); i++) {
            if (rankingList.get(i).getStuNum() == (score.getStuNum())) {
                rank = i + 1;
            }
        }
        String ranking = rank + "/" + rankingList.size();


        return ranking;
    }

    @Override
    public void remove(long stuNum) {
        String sql = "DELETE FROM tbl_score WHERE stu_Num = ?";
        template.update(sql, stuNum);

    }
}
