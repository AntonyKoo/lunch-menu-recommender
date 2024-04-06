package com.springboot.hello.controller;

import com.springboot.hello.domain.Menu;
import com.springboot.hello.dto.MenuRequestDto;
import com.springboot.hello.dto.MenuResponseDto;
import com.springboot.hello.repository.MenuRepository;
import com.springboot.hello.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;

    // 홈으로 랜딩하면, 등록된 메뉴 데이터가 있는 경우 보여주는 GET-API
    @GetMapping("/")
    public ModelAndView home() throws IOException {
        ModelAndView modelAndView = new ModelAndView("index");
        List<MenuResponseDto> menuList = menuService.getAllMenu(); // 메뉴를 가져오는 서비스 메소드 호출
        modelAndView.addObject("menuList", menuList); // 메뉴 리스트를 모델에 담아주기
        return modelAndView;
        // 해당 url로 액세스 했을때, 보여줄 화면 view 이름 정해주기
    }

    // 메뉴 추천받기 요청에 대한 getAPI
    @GetMapping("/api/v1/recommend/menu")
    @ResponseBody
    public ModelAndView recommendMenu() throws IOException {
        // 랜덤하게 메뉴를 하나 가져옴
        ModelAndView modelAndView = new ModelAndView("index");
        List<MenuResponseDto> menuList = menuService.getAllMenu();
        Menu recommendedMenu = menuService.getRandomMenu();

        modelAndView.addObject("menuList",menuList);
        modelAndView.addObject("recommendedMenu", recommendedMenu);

        return modelAndView;
    }

    // 단일 메뉴 저장 POST-API
    @PostMapping(value="/api/v1/register/menu")
    public ResponseEntity<?> addMenu(@ModelAttribute MenuRequestDto menuRequestDto) {

        try {
            MenuResponseDto menuResponseDto = menuService.saveMenu(menuRequestDto);

            return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            // 메뉴 등록에 살패한 경우에 대한 예외처리 로깅
            // 여기서는 콘솔에 간단히 출력. 로깅은 TBD
            System.out.println("Failed to save menu: " + menuRequestDto.getName());

            // 클라이언트에게 실패 메시지 반환
            String errorMessage = "Failed to save menu: " +menuRequestDto.getName();

            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }




    // 여러 메뉴를 한 번에 등록하기 위한 POST API
    // TBD 메뉴마다 다른 여러 이미지를 받아서 처리하는 로직은 controller, service/serviceImple, Menu 등에서 추가로 코드를 작성해야 할듯
//    @PostMapping(value="/register/menus")
//    public ResponseEntity<?> addMenus(@RequestBody List<MenuRequestDto> menuRequestDtoList,
//                                      @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
//
//        List<MenuResponseDto> responses = new ArrayList<>();
//        List<String> failedMenuNames = new ArrayList<>();
//
//
//        for (MenuRequestDto menuRequestDto : menuRequestDtoList) {
//
//            try {
//                MenuResponseDto menuResponseDto = menuService.saveMenu(menuRequestDto);
//                responses.add(menuResponseDto);
//            } catch (Exception e) {
//                // 저장에 실패한 메뉴의 이름을 저장합니다.
//                failedMenuNames.add(menuRequestDto.getName());
//
//                // 저장에 실패한 메뉴에 대한 예외처리 로그를 기록
//                // 여기서는 간단히 콘솔 출력, Logging 은 TBD
//                System.out.println("Failed to save menu: " + menuRequestDto.getName());
//            }
//        }
//
//        // 저장에 실패한 메뉴 정보를 클라이언트에게 해당 정보를 포함하여 응답을 보내줌
//        if (!failedMenuNames.isEmpty()) {
//            String errorMessage = "Failed to save menus: " + String.join(", ", failedMenuNames);
//
//            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//        }
//
//        // 모든 메뉴가 성공적으로 등록되었을 때 응답을 반환
//        return new ResponseEntity<>(responses, HttpStatus.CREATED);
//
//    }


}
