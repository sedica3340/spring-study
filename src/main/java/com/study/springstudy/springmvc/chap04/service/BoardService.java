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

        // 조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5 이상인 게시물에 특정 마킹을 한다

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
