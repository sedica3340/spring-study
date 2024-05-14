package com.study.springstudy.webservlet.chap02.v3.controller;

import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.ModelAndView;
import com.study.springstudy.webservlet.View;
import com.study.springstudy.webservlet.chap02.v2.controller.ControllerV2;
import com.study.springstudy.webservlet.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShowController implements ControllerV3 {

    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    public ModelAndView process(Map<String, String> paramMap) {

        // 1. 적절한 저장소에서 회원정보들을 가져옴
        List<Member> memberList = repo.findAll();
        // 2. 해당 회원정보를 JSP 파일에 전송하기 위한 세팅을 함

        // 3. 적절한 JSP 를 찾아 화면 렌더링
        ModelAndView modelAndView = new ModelAndView("v3/m-list");
        modelAndView.addAttribute("memberList", memberList);

        return modelAndView;
    }
}
