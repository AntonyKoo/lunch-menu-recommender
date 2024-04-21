package com.springboot.hello.controller;

import com.springboot.hello.domain.Member;
import com.springboot.hello.dto.MemberFormDto;
import com.springboot.hello.service.memberService.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value="/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        // 비밀번호 확인 입력 오류 구현하기

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
            return "members/new";

        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/new";
        }

        return "redirect:/";
    }
}
