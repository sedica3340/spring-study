package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ScoreJdbcRepository implements ScoreRepository {
    private String url = "jdbc:mariadb://localhost:3306/spring5";
    private String username = "root";
    private String password = "mariadb";

    public ScoreJdbcRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean save(Score score) {

        try (Connection conn = connect()) {

            String sql = "INSERT INTO tbl_score " +
                    "(stu_name, kor, eng, math, total, average, grade) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, score.getStuName());
            pstmt.setInt(2, score.getKor());
            pstmt.setInt(3, score.getEng());
            pstmt.setInt(4, score.getMath());
            pstmt.setInt(5, score.getTotal());
            pstmt.setDouble(6, score.getAverage());
            pstmt.setString(7, score.getGrade().toString());

            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Score> findAll() {
        List<Score> scoreList = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM tbl_score";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Score s = new Score(rs);
                scoreList.add(s);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return scoreList;
    }

    @Override
    public Score findOne(Long stuNum) {

        Score score = null;
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM tbl_score WHERE stu_Num = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, stuNum);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            score = new Score(rs);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return score;
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
        try (Connection conn = connect()) {
            String sql = "DELETE FROM tbl_score WHERE stu_Num = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, stuNum);

            pstmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
