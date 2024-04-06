package com.springboot.hello.controller;

import com.springboot.hello.domain.Member;
import com.springboot.hello.dto.MemberFormDto;
import com.springboot.hello.service.memberService.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value="/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/new";
        }

        return "redirect:/";
    }
}
