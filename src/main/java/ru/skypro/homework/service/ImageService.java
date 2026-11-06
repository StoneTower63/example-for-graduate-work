package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Сервис для работы с картинками.
 */
public interface ImageService {
    /**
     * Сохраняет картинку в файловую систему и возвращает имя файла.
     */
    String saveImage(MultipartFile image) throws IOException;

    /**
     * Возвращает байты картинки по её имени.
     */
    byte[] getImage(String fileName) throws IOException;

    /**
     * Удаляет картинку по её имени.
     */
    void deleteImage(String fileName) throws IOException;
}