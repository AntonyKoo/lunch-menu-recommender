package com.springboot.hello.service;

import com.springboot.hello.domain.Menu;
import com.springboot.hello.dto.MenuRequestDto;
import com.springboot.hello.dto.MenuResponseDto;
import java.util.List;

public interface MenuService {
    MenuResponseDto saveMenu(MenuRequestDto menuRequestDto);
    List<MenuResponseDto> getAllMenu();

    Menu getRandomMenu();
}
