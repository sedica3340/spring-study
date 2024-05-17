package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;

import java.util.List;

public class ScoreMemoryRepository implements ScoreRepository{
    @Override
    public boolean save(Score score) {
        return false;
    }

    @Override
    public List<Score> findAll() {
        return List.of();
    }

    @Override
    public Score findOne(Long stuNum) {
        return null;
    }

    @Override
    public String ranking(Score score) {
        return "";
    }

    @Override
    public void remove(long stuNum) {

    }
}
