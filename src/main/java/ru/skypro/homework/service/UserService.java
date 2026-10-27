package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

/**
 * Сервис для работы с профилем пользователя.
 */
public interface UserService {
    void setPassword(NewPassword newPassword);

    User getUser();

    UpdateUser updateUser(UpdateUser updateUser);

    void updateUserImage(String imagePath);
}
