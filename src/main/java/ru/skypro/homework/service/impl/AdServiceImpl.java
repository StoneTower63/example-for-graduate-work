package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с объявлениями.
 */
@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;

    public AdServiceImpl(AdRepository adRepository, UserRepository userRepository,
                         AdMapper adMapper, ImageService imageService) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.adMapper = adMapper;
        this.imageService = imageService;
    }

    private UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @Override
    public Ads getAllAds() {
        List<Ad> ads = adRepository.findAll().stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
        Ads result = new Ads();
        result.setCount(ads.size());
        result.setResults(ads);
        return result;
    }

    @Override
    public Ad addAd(CreateOrUpdateAd ad, MultipartFile image) {
        UserEntity user = getCurrentUser();
        AdEntity adEntity = adMapper.toEntity(ad);
        adEntity.setAuthor(user);

        try {
            String fileName = imageService.saveImage(image);
            adEntity.setImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении картинки", e);
        }

        AdEntity savedAd = adRepository.save(adEntity);
        return adMapper.toDto(savedAd);
    }

    @Override
    public ExtendedAd getAds(Integer id) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
        return adMapper.toExtendedDto(adEntity);
    }

    @Override
    public void removeAd(Integer id) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
        UserEntity user = getCurrentUser();
        if (!adEntity.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Нет прав на удаление этого объявления");
        }
        try {
            if (adEntity.getImage() != null) {
                imageService.deleteImage(adEntity.getImage());
            }
        } catch (IOException e) {
        }
        adRepository.delete(adEntity);
    }

    @Override
    public Ad updateAds(Integer id, CreateOrUpdateAd ad) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
        UserEntity user = getCurrentUser();
        if (!adEntity.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Нет прав на редактирование этого объявления");
        }
        adEntity.setTitle(ad.getTitle());
        adEntity.setPrice(ad.getPrice());
        adEntity.setDescription(ad.getDescription());
        AdEntity updatedAd = adRepository.save(adEntity);
        return adMapper.toDto(updatedAd);
    }

    @Override
    public Ads getAdsMe() {
        UserEntity user = getCurrentUser();
        List<Ad> ads = adRepository.findAllByAuthor(user).stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
        Ads result = new Ads();
        result.setCount(ads.size());
        result.setResults(ads);
        return result;
    }

    @Override
    public byte[] updateImage(Integer id, MultipartFile image) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
        UserEntity user = getCurrentUser();
        if (!adEntity.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Нет прав на редактирование картинки");
        }

        try {
            if (adEntity.getImage() != null) {
                imageService.deleteImage(adEntity.getImage());
            }
            String fileName = imageService.saveImage(image);
            adEntity.setImage(fileName);
            adRepository.save(adEntity);
            return imageService.getImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при обновлении картинки", e);
        }
    }
}