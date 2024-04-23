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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 페이지 요청 -> 그런데 난 회원가입 페이지는 필요 없음
//    @GetMapping("/signup")
//    public String signup(MemberFormDto memberFormDto) {
//        return "/signup_form"; // 회원가입 위치를 html을 반환해 준다.
//    }

    // 회원가입 POST 요청
    @PostMapping(value="/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        // 비밀번호 제대로 입력했는지 확인하는 form & 입력 오류 구현하기

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
            return "members/new"; // 이거 말고 가입 완료한 사용자를 빌더로 반환하는게 나을듯,,?

        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/new";  // 이것도 잘못됨
        }

        return "redirect:/"; // 회원가입이 완료된 후, 홈으로 리디렉션 -> 로그인 유도하는 뭔가 구성하자.
    }
}
