package com.study.springstudy.springmvc.chap05.rest;

import com.study.springstudy.database.chap01.Person;
import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap04.common.PageMaker;
import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/rest")
@RestController // 서버가 클라이언트에게 순수한 데이터를 전달할 때 사용
@RequiredArgsConstructor
public class RestApiController {
    private final BoardService boardService;

    @GetMapping("/hello")
    public String hello() {

        return "hello mister my yesterday";
    }

    @GetMapping(value = "/hobby", produces = "application/json")
    public List<String> hobby() {

        return List.of("태권도", "장기", "댄스");
    }


    @GetMapping("/person")
    public Person person() {
        return new Person(100, "핑구", 10);
    }


    @GetMapping("/board")
    public Map<String, Object> board() {
        List<BoardListResponseDto> list = boardService.getList(new Search());

        HashMap<String, Object> map = new HashMap<>();
        map.put("page", new PageMaker(new Page(), boardService.getCount(new Search())));
        map.put("articles", list);
        return map;
    }

    /*
         RestController에서 리턴타입을 ResponseEntity를 쓰는 이유

         - 클라이언트에게 응답할 때는 메시지 바디안에 들어 있는 데이터도 중요하지만
         - 상태코드와 헤더정보를 포함해야 한다
         - 근데 일반 리턴타입은 상태코드와 헤더정보를 전송하기 어렵다
     */
    @GetMapping("/people")
    public ResponseEntity<?> people() {
        Person p1 = new Person(111, "딸긔겅듀", 30);
        Person p2 = new Person(222, "잔망루피", 31);
        Person p3 = new Person(333, "뽀로로쉐", 32);
        List<Person> people = List.of(p1, p2, p3);

        HttpHeaders headers = new HttpHeaders();
        headers.add("mypet", "nyan");
        headers.add("money", "100won");

        return ResponseEntity
//                .status(200)
                .ok()
                .headers(headers)
//                .notFound()
//                .internalServerError()
                .body(people);

    }

    @GetMapping("/bmi")
    public ResponseEntity<?> bmi(
            @RequestParam(required = false) Double cm,
            @RequestParam(required = false) Double kg
    ) {

        if (cm == null || kg == null) {
            return ResponseEntity
                    .badRequest()
                    .body("키와 몸무게를 전달하세요!");
        }
        double m = cm / 100;
        double bmi = kg / (m * m);


        return ResponseEntity
                .ok()
                .body(bmi);
    }

}
