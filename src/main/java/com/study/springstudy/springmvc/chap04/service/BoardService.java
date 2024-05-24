package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;

    public List<BoardListResponseDto> getList(Search page) {

        // 조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5 이상인 게시물에 특정 마킹을 한다

        return boardMapper.findAll(page).stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }

    public boolean insert(Board board) {
        return boardMapper.save(board);
    }

    public boolean deleteBoard(int boardNo) {
        return boardMapper.delete(boardNo);
    }

    public BoardDetailResponseDto search(int boardNo) {

        List<Reply> replies = replyMapper.findAll(boardNo);

        BoardDetailResponseDto dto = new BoardDetailResponseDto(boardMapper.findOne(boardNo));
        dto.setReplies(replies);
        return dto;
    }

    public void addViewCount(int boardNo) {
        boardMapper.addShowCount(boardNo);
    }

    public int getCount(Search page) {
        return boardMapper.count(page);
    }
}
