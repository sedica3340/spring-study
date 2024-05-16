package com.study.springstudy.springmvc.chap03.controller;


import com.study.springstudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springstudy.springmvc.chap03.entity.Score;
import com.study.springstudy.springmvc.chap03.repository.ScoreJdbcRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/score")
public class ScoreController {

    // 의존객체 설정
    private ScoreJdbcRepository repository = new ScoreJdbcRepository();


    /*
    # 요청 URL
    1. 학생 성적정보 등록화면을 보여주고 및 성적정보 목록조회 처리
    - /score/list : GET
*/
    @GetMapping("/list")
    public String list(String sort, Model model) {
        System.out.println("/score/list : GET!");

        List<Score> scoreList = repository.findAll();

        model.addAttribute("sList", scoreList);

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
        Score score = new Score(dto);

        repository.save(score);

        return "redirect:/score/list";
    }
    /*
    3. 성적정보 삭제 요청
    - /score/remove : Get
    */
    @GetMapping("/remove")
    public String remove(Long stuNum) {
        System.out.println("score/remove : GET!");
        repository.remove(stuNum);
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
        Score score = repository.findOne(stuNum);
        // 3. DB에서 조회한 회원정보 JSP에게 전달
        String rank = repository.ranking(score);
        model.addAttribute("s", score);
        model.addAttribute("rank", rank);

        return "score/score-detail";
    }
}
