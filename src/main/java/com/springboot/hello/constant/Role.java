package com.springboot.hello.constant;

import lombok.Getter;

@Getter // 상수니깐 세터 없이 게터만 구성했긔
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    Role(String value) {
        this.value = value;
    }

    private String value;
}
