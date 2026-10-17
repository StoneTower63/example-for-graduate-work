package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * DTO для авторизации пользователя.
 */
@Data
@Schema(description = "Авторизация пользователя")
public class Login {

    @Schema(description = "Логин", minLength = 4, maxLength = 32)
    @Size(min = 4, max = 32)
    private String username;

    @Schema(description = "Пароль", minLength = 8, maxLength = 16)
    @Size(min = 8, max = 16)
    private String password;
}