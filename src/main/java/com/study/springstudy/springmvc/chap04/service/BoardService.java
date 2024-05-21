package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper mapper;

    public List<Board> getList() {
        return mapper.findAll();
    }

    public boolean insert(Board board) {
        return mapper.save(board);
    }

    public boolean deleteBoard(int boardNo) {
        return mapper.delete(boardNo);
    }

    public Board search(int boardNo) {
        return mapper.findOne(boardNo);
    }

    public void addViewCount(int boardNo) {
        mapper.addShowCount(boardNo);
    }
}
