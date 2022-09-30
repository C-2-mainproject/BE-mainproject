package com.wolves.mainproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RedirectController {
    @GetMapping("/")
    public String redirect(HttpServletRequest request, HttpServletResponse response){
        response.addCookie(request.getCookies()[0]);
        return "redirect:http://localhost:3000";
    }
}
