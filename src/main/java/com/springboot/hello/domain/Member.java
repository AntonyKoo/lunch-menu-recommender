package com.springboot.hello.domain;

import com.springboot.hello.constant.Role;
import com.springboot.hello.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Table(name="member")
@Entity
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }

}
