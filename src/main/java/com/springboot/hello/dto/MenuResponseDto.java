package com.springboot.hello.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private String review;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
