package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdService;

/**
 * Реализация сервиса для работы с объявлениями.
 */
@Service
public class AdServiceImpl implements AdService {
    @Override
    public Ads getAllAds() {
        return new Ads();
    }

    @Override
    public Ad addAd(CreateOrUpdateAd ad, MultipartFile image) {
        return new Ad();
    }

    @Override
    public ExtendedAd getAds(Integer id) {
        return new ExtendedAd();
    }

    @Override
    public void removeAd(Integer id) {
        // TODO: Логика удаления
    }

    @Override
    public Ad updateAds(Integer id, CreateOrUpdateAd ad) {
        return new Ad();
    }

    @Override
    public Ads getAdsMe() {
        return new Ads();
    }

    @Override
    public byte[] updateImage(Integer id, MultipartFile image) {
        return new byte[0];
    }
}