package com.wolves.mainproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RedirectController {
    @GetMapping("/")
    public String redirect(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Set-Cookie", request.getCookies()[0].getValue());

        return "redirect:https://ildanenglish.com/";
    }
}
