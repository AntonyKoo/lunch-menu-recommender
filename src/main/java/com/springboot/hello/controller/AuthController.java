package com.springboot.hello.controller;

import com.springboot.hello.dto.MenuResponseDto;
import com.springboot.hello.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/login")
    public String loginPage() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // 로그인 처리 성공하는 경우
        if (username.equals("user") && password.equals("password")) {
            return "redirect:/";
        } else {
            // 실패하는 경우,
            model.addAttribute("error",true);
            return "index";
        }

    }

}
