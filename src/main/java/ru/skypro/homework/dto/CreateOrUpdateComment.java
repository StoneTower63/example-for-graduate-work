package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO для создания или обновления комментария.
 */
@Data
@Schema(description = "Создание или обновление комментария")
public class CreateOrUpdateComment {

    @Schema(description = "Текст комментария", minLength = 8, maxLength = 64)
    @NotNull
    @Size(min = 8, max = 64)
    private String text;
}