package com.springboot.hello.config;

import com.springboot.hello.service.memberService.MemberService;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
//                                .requestMatchers("/","/index","/api/v1/recommend/menu","/login").permitAll())
                                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//                                .csrf((csrf) -> csrf
//                                        .ignoringRequestMatchers("/login"))
                                        // CSRF 방어를 해제할 url을 설정, H2 콘솔 등을 등록하기도 함
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                                // CSRF 빙어로 인해 헤더가 DENY 되어 페이지 프레임이 깨지지 않도록 하기
                .formLogin((formLogin) -> formLogin
                        // 로그인 URL을 등록하기, 여기에 URL을 설정했다면, 컨트롤러에 매핑해야 함
                        // 나 같은 경우는 별도 로그인 페이지를 구성하지 않아서 필요 ㄴㄴ
                        .loginProcessingUrl("/login")
                        // 로그인 폼의 action 속성을 지정하는 것과 같음
                        .permitAll()
                        .defaultSuccessUrl("/")
                        .failureUrl("/?error=true"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));
        return http.build();
    }

    // 비번 암호화 해주는 인코더
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }


}
