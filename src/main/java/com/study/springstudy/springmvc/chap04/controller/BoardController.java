package com.study.springstudy.springmvc.chap04.controller;

import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardPostDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.repositroy.BoardRepository;
import com.study.springstudy.springmvc.chap04.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

//    private final BoardRepository repository;
    private final BoardService service;

    // 1. 목록 조회 요청 (/board/list : GET)
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boardList = service.getList();

        List<BoardListResponseDto> bList = boardList.stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());

        model.addAttribute("bList", bList);
        return "board/list";
    }


    // 2. 글쓰기 양식 화면 열기 요청 (/board/write : GET)
    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    // 3. 게시글 등록 요청 (/board/write : POST)
    // -> 목록조회 요청 리다이렉션
    @PostMapping("/write")
    public String register(BoardPostDto dto) {
        Board board = new Board(dto);
        service.insert(board);
        return "redirect:/board/list";
    }

    // 4. 게시글 삭제 요청 (/board/delete : GET)
    // -> 목록조회 요청 리다이렉션
    @GetMapping("/delete")
    public String remove(int boardNo) {
//        System.out.println("보드넘버는" + boardNo);
        service.deleteBoard(boardNo);
        return "redirect:/board/list";
    }

    // 5. 게시글 상세 조회 요청 (/board/detail :GET)
    @GetMapping("/detail")
    public String detail(int boardNo, Model model) {

        Board board = service.search(boardNo);
        BoardDetailResponseDto b = new BoardDetailResponseDto(board);

        model.addAttribute("b",b);
        service.addViewCount(boardNo);
        return "board/detail";
    }
}
