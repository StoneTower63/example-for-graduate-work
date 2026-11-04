package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Реализация сервиса для работы с картинками.
 * Сохраняет картинки в файловую систему.
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${path.to.images}")
    private String imagesPath;

    @Override
    public String saveImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            return null;
        }

        Path folderPath = Paths.get(imagesPath);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String originalFilename = image.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID() + extension;

        Path filePath = folderPath.resolve(fileName);
        image.transferTo(filePath.toFile());

        return fileName;
    }

    @Override
    public byte[] getImage(String fileName) throws IOException {
        Path filePath = Paths.get(imagesPath, fileName);
        if (!Files.exists(filePath)) {
            return new byte[0];
        }
        return Files.readAllBytes(filePath);
    }

    @Override
    public void deleteImage(String fileName) throws IOException {
        Path filePath = Paths.get(imagesPath, fileName);
        Files.deleteIfExists(filePath);
    }
}