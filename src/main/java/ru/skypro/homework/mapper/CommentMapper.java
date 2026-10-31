package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;

/**
 * Маппер для преобразования сущности комментария в DTO и обратно.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorImage", source = "author.image")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    Comment toDto(CommentEntity entity);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    CommentEntity toEntity(CreateOrUpdateComment dto);
}