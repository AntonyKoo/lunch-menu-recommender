package com.springboot.hello.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImageService {
    /**
     * 이미지를 원본 그대로 저장합니다.
     * @param imageFile 저장할 이미지 파일
     * @return 저장된 이미지 파일의 경로
     * @throws IOException 이미지 처리 중 발생한 IO 예외
     */

    String saveImage(MultipartFile imageFile) throws IOException;
    String cropImageToBase64(String imagePath);
}
