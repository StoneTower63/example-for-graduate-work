package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

/**
 * Реализация сервиса для работы с профилем пользователя.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    private UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @Override
    public void setPassword(NewPassword newPassword) {
        log.info("Запрос на смену пароля для пользователя");
        UserEntity user = getCurrentUser();
        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            log.warn("Неверный текущий пароль для пользователя {}", user.getEmail());
            throw new RuntimeException("Неверный текущий пароль");
        }
        user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(user);
        log.info("Пароль успешно изменен для пользователя {}", user.getEmail());
    }

    @Override
    public User getUser() {
        UserEntity user = getCurrentUser();
        return userMapper.toDto(user);
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser) {
        log.info("Обновление профиля пользователя");
        UserEntity user = getCurrentUser();
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        userRepository.save(user);
        log.info("Профиль пользователя {} обновлен", user.getEmail());
        return updateUser;
    }

    @Override
    public void updateUserImage(MultipartFile image) {
        log.info("Обновление аватара пользователя");
        UserEntity user = getCurrentUser();
        try {
            if (user.getImage() != null) {
                imageService.deleteImage(user.getImage());
            }
            String fileName = imageService.saveImage(image);
            user.setImage(fileName);
            userRepository.save(user);
            log.info("Аватар пользователя {} обновлен", user.getEmail());
        } catch (IOException e) {
            log.error("Ошибка при сохранении аватара", e);
            throw new RuntimeException("Ошибка при сохранении картинки", e);
        }
    }
}