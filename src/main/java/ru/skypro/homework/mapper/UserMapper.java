package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

/**
 * Маппер для преобразования сущности пользователя в DTO и обратно.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "comments", ignore = true)
    UserEntity toEntity(Register register);
}
