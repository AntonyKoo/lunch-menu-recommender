package com.springboot.hello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MenuRequestDto {
    private String name;
    private int price;
    private String review;
    private MultipartFile imageFile; // 이미지 파일
}
