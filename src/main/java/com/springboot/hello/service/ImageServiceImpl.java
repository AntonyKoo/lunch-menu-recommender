package com.springboot.hello.service;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;


@Service
public class ImageServiceImpl implements ImageService {

    @Value("${image.upload.directory}")
    private String uploadDirectory;

    public String saveImage(MultipartFile imageFile) throws IOException {
        // 이미지 파일이 비어 있는지 확인
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일이 비어 있습니다.");
        }

        // 이미지 파일 이름 가져오기
        String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());

        // 파일 이름에서 확장자만 추출
        String extension = StringUtils.getFilenameExtension(originalFilename);

        // 파일 이름에 랜덤한 UUID를 추가하여 중복을 방지하고 저장
//        String filename = UUID.randomUUID().toString() + "-" + originalFilename;
        // 일단은 파일의 원본 이름을 버리고 나중에 해싱하자
        String uuid = UUID.randomUUID().toString();

        String filename = uuid + "." + extension;

        // 이미지 파일이 저장될 디렉토리 경로 설정
        Path uploadPath = Paths.get(uploadDirectory);

        // 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

//        String hashedFilename = generateHashedFilename(filename);

        // 파일 저장 경로 설정
        Path filePath = uploadPath.resolve(filename);

        // 파일을 지정된 경로에 저장
        Files.copy(imageFile.getInputStream(), filePath);

        // 이미지 파일 경로 반환
        return filename;

    }

    // SHA-256 해시 함수를 사용하여 파일이름 생성
    private String generateHashedFilename(String filename) {
        try {
            // 파일 이름을 바이트 배열로 변환하여 해시 함수에 전달
            byte[] bytes = filename.getBytes();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(bytes);

            // 해시된 바이트 배열을 문자열로 변환하여 반환
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("해시 알고리즘이 지원되지 않습니다.", e);
        }
    }

    // Image crop method
    public String cropImageToBase64(String imagePath) {
        try{
            // 이미지 파일 읽기
            File file = new File("/Users/dongyun_dev/Documents/hello/src/main/resources/static/images/" + imagePath);
            BufferedImage originalImage = ImageIO.read(file);

            // 이미지 크롭
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int cropSize = Math.min(width,height);
            BufferedImage croppedImage = Thumbnails.of(originalImage)
                    .sourceRegion(Positions.CENTER, cropSize, cropSize)
                    .size(200,200) // 원하는 비율로 설정 ㄱㄴ
                    .asBufferedImage();

            // 이미지를 Base64로 인코딩
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String extension = StringUtils.getFilenameExtension(imagePath); // 파일 이름에서 확장자 추출
            ImageIO.write(croppedImage, extension, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return Base64.getEncoder().encodeToString(imageInByte);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
