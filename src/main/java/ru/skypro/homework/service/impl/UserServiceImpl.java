package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

/**
 * Реализация сервиса для работы с профилем пользователя.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void setPassword(NewPassword newPassword) {
        // TODO: Логика смены пароля
    }

    @Override
    public User getUser() {
        // TODO: Логика получения профиля
        return new User();
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser) {
        // TODO: Логика обновления профиля
        return new UpdateUser();
    }

    @Override
    public void updateUserImage(String imagePath) {
        // TODO: Логика загрузки аватара
    }
}
