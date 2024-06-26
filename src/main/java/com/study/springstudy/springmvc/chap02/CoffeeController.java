package com.study.springstudy.springmvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coffee/*")
public class CoffeeController {


    /**
     * @request-uri : /coffee/order
     * @return : WEB-INF/views/mvc/coffee-form.jsp
     */
    // GET 요청만 받겠다
    @GetMapping("/order")
    public String order() {
        return "mvc/coffee-form";
    }

    // POST 요청만 받겠다
    @PostMapping("/result")
    public String result(String menu, int price, Model model) {
        model.addAttribute("mmm", menu);
        model.addAttribute("ppp", price);

        return "mvc/coffee-result";
    }
}
