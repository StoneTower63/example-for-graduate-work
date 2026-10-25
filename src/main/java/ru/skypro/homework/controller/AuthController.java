package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;

/**
 * Контроллер для регистрации и авторизации пользователей.
 */
@RestController
@Tag(name = "Авторизация", description = "Методы для регистрации и входа")
public class AuthController {

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Void> register(@RequestBody Register register) {
        // TODO: Здесь будет логика регистрации на Этапе III
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> login(@RequestBody Login login) {
        // TODO: Здесь будет логика логина на Этапе III
        return ResponseEntity.ok().build();
    }
}