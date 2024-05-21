package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper mapper;

    public List<BoardListResponseDto> getList() {
        return mapper.findAll().stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }

    public boolean insert(Board board) {
        return mapper.save(board);
    }

    public boolean deleteBoard(int boardNo) {
        return mapper.delete(boardNo);
    }

    public BoardDetailResponseDto search(int boardNo) {
        return new BoardDetailResponseDto(mapper.findOne(boardNo));
    }

    public void addViewCount(int boardNo) {
        mapper.addShowCount(boardNo);
    }
}
