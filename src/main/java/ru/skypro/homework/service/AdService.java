package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

/**
 * Сервис для работы с объявлениями.
 */
public interface AdService {
    Ads getAllAds();

    Ad addAd(CreateOrUpdateAd ad, MultipartFile image);

    ExtendedAd getAds(Integer id);

    void removeAd(Integer id);

    Ad updateAds(Integer id, CreateOrUpdateAd ad);

    Ads getAdsMe();

    byte[] updateImage(Integer id, MultipartFile image);
}