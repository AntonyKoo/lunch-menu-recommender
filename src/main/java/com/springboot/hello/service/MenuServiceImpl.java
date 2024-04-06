package com.springboot.hello.service;

import com.springboot.hello.domain.Menu;
import com.springboot.hello.dto.MenuRequestDto;
import com.springboot.hello.dto.MenuResponseDto;
import com.springboot.hello.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ImageService imageService;

    @Value("${image.upload.directory}")
    private String uploadDirectory;


    @Transactional
    public List<MenuResponseDto> getAllMenu() {

        List<Menu> menuList = menuRepository.findAll();
        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();

        for (Menu menu : menuList) {
            String base64Image = imageService.cropImageToBase64(menu.getImagePath());
            MenuResponseDto menuResponseDto = MenuResponseDto.builder()
                            .id(menu.getId())
                            .name(menu.getName())
                            .price(menu.getPrice())
                            .review(menu.getReview())
                            .imagePath(base64Image)
                            .build();
            menuResponseDtoList.add(menuResponseDto);
        }
        return menuResponseDtoList;
    }

    @Transactional
    public Menu getRandomMenu() {
        // 모든 메뉴를 디비에서 가져옴
        List<Menu> allMenu = menuRepository.findAll();

        // 랜덤하게 하나의 메뉴를 선택
        Random random = new Random();
        int randomIndex = random.nextInt(allMenu.size());
        return allMenu.get(randomIndex);
    }

    @Transactional
    public MenuResponseDto saveMenu(MenuRequestDto menuRequestDto) {

        // 이미지 파일을 저장하고 경로를 반환
        String imagePath = null;
        if (menuRequestDto.getImageFile() != null) {
            try {
                imagePath = imageService.saveImage(menuRequestDto.getImageFile());
            } catch (IOException e) {
                // 이미지 저장 실패 시, 예외처리
                e.printStackTrace();
            }
        }

        // 메뉴 객체(엔티티) 생성
        Menu menu = Menu.builder()
                .name(menuRequestDto.getName())
                .price(menuRequestDto.getPrice())
                .review(menuRequestDto.getReview())
                .imagePath(imagePath)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        // if 이미지 파일이 존재하면 -> 이미지 처리

        // 이미지 포함하는 menu 객체를 DB에 저장
        try {
            menuRepository.save(menu);
            System.out.println(String.format("메뉴 등록 성공!!: %s", menu.getName()));
        } catch (Exception e) {
            System.out.println("Error during save menu: " + e.getMessage());
        }

        // 메뉴 저장 후, response로 나갈 DTO 생성/반환
        MenuResponseDto menuResponseDto = MenuResponseDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .review(menu.getReview())
                .imagePath(menu.getImagePath())
                .createdAt(menu.getCreatedAt())
                .build();

        return menuResponseDto;
    }
}

