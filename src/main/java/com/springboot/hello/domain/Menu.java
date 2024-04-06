package com.springboot.hello.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="menu")
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 메뉴 코드

    @Column(name= "menu_name", nullable = false, length = 50)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Lob
    @Column(name = "review", nullable = true)
    private String review;

    @Column(name = "image_path", nullable=true)
    private String imagePath; // 이미지 파일을 저장할 파일 시스템 경로

    // 시간정보는 Timestamped 추상 메소드로 AuditingEntityListener 사용하여 extends 할 수도 있음
    @CreatedDate
    private LocalDateTime createdAt; // 메뉴_정보_등록일

    @LastModifiedDate
    private LocalDateTime modifiedAt; // 메뉴_정보_수정일

}
