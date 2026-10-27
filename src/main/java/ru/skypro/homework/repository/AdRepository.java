package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;

/**
 * Репозиторий для работы с объявлениями.
 */
public interface AdRepository extends JpaRepository<AdEntity, Integer> {
    List<AdEntity> findAllByAuthor(UserEntity author);
}