package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;

/**
 * Маппер для преобразования сущности объявления в DTO и обратно.
 */
@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "author", source = "author.id")
    Ad toDto(AdEntity entity);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    ExtendedAd toExtendedDto(AdEntity entity);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", ignore = true)
    AdEntity toEntity(CreateOrUpdateAd dto);
}