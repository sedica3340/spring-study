package com.study.springstudy.springmvc.chap03.controller;


import com.study.springstudy.springmvc.chap03.dto.ScoreDetailResponseDto;
import com.study.springstudy.springmvc.chap03.dto.ScoreListResponseDto;
import com.study.springstudy.springmvc.chap03.dto.ScoreModifyRequestDto;
import com.study.springstudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springstudy.springmvc.chap03.entity.Score;
import com.study.springstudy.springmvc.chap03.repository.ScoreJdbcRepository;
import com.study.springstudy.springmvc.chap03.repository.ScoreRepository;
import com.study.springstudy.springmvc.chap03.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/score")
@RequiredArgsConstructor
public class ScoreController {

    // 의존객체 설정
    private final ScoreRepository repository;
    private final ScoreService service;

    // lombok 으로 생성시킴
//    @Autowired
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    /*
    # 요청 URL
    1. 학생 성적정보 등록화면을 보여주고 및 성적정보 목록조회 처리
    - /score/list : GET
*/
    @GetMapping("/list")
    public String list(String sort, Model model) {
        System.out.println("/score/list : GET!");

//        List<Score> scoreList = repository.findAll(sort);
        List<ScoreListResponseDto> dtos = service.getList(sort);



        model.addAttribute("sList", dtos);

        return "score/score-list";
    }


    /*
    2. 성적 정보 등록 처리 요청
    - /score/register : POST
    */
    @PostMapping("/register")
    public String register(ScorePostDto dto) {
        System.out.println("/score/register : POST!");
        System.out.println(dto);


        // 데이터베이스 저장 위임..
//        Score score = new Score(dto);

//        repository.save(score);
        service.insert(dto);

        return "redirect:/score/list";
    }

    /*
    3. 성적정보 삭제 요청
    - /score/remove : Get
    */
    @GetMapping("/remove")
    public String remove(Long stuNum) {
        System.out.println("score/remove : GET!");
//        repository.remove(stuNum);
        service.deleteScore(stuNum);
        return "redirect:/score/list";
    }


    /*
    4. 성적정보 상세 조회 요청
    - /score/detail : GET
 */
    @GetMapping("/detail")
    public String detail(Long stuNum, Model model) {
        System.out.println("score/detail : GET!");

        // 1. 상세조회를 원하는 학번을 읽기
        // 2. DB에 상세조회 요청
        ScoreDetailResponseDto dto = service.retrieve(stuNum);
        // 3. DB에서 조회한 회원정보 JSP에게 전달
        model.addAttribute("s", dto);
//        model.addAttribute("rank", rank);

        return "score/score-detail";
    }

    @GetMapping("/modify")
    public String modify(long stuNum, Model model) {
        ScoreDetailResponseDto dto = service.retrieve(stuNum);
        model.addAttribute("s", dto);

        return "score/score-modify";
    }
    @PostMapping("/modify")
    public String postModify(ScoreModifyRequestDto dto) {
        // 1. 수정을 원하는 새로운 데이터 읽기
        service.update(dto);

        return "redirect:/score/detail?stuNum=" + dto.getStuNum();
    }


}
