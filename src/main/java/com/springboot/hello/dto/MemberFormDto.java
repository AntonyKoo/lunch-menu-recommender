package com.springboot.hello.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {

    @Size(min=3, max=25)
    @NotBlank(message="사용자ID는 필수항목 입니다.")
    private String username;

    @NotEmpty(message="이메일은 필수항목 입니다.")
    @Email(message="올바른 이메일 형식을 입력해 주세요.")
    private String email;

    @NotEmpty(message="비밀번호는 필수항목 입니다.")
    @Length(min=8, max=16, message="비밀번호는 8자 이상, 16자 이하로 입력해 주세요.")
    private String password;
}
