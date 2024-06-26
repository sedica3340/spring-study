package com.study.springstudy.springmvc.chap04.repositroy;

import com.study.springstudy.springmvc.chap04.entity.Board;

import java.util.List;

// 게시판 CRUD
public interface BoardRepository {

    // 게시물 목록 조회
    List<Board> findAll();

    // 게시물 상세 조회
    Board findOne(int boardNo);

    // 게시물 등록
    boolean save(Board board);

    // 게시물 삭제
    boolean delete(int boardNo);

    void addShowCount(int boardNo);
}
