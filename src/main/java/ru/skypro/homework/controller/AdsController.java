package ru.skypro.homework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdService;

/**
 * Контроллер для работы с объявлениями.
 */
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "Методы для работы с объявлениями")
public class AdsController {

    private final AdService adService;

    public AdsController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping
    @Operation(summary = "Получение всех объявлений", responses = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление объявления", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Ad> addAd(
            @RequestPart("properties") String propertiesJson,
            @RequestPart("image") MultipartFile image) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CreateOrUpdateAd properties = mapper.readValue(propertiesJson, CreateOrUpdateAd.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.addAd(properties, image));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об объявлении", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<ExtendedAd> getAds(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getAds(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление объявления", responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id) {
        adService.removeAd(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление информации об объявлении", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Ad> updateAds(@PathVariable("id") Integer id,
                                        @RequestBody CreateOrUpdateAd updateAd) {
        return ResponseEntity.ok(adService.updateAds(id, updateAd));
    }

    @GetMapping("/me")
    @Operation(summary = "Получение объявлений авторизованного пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Ads> getAdsMe() {
        return ResponseEntity.ok(adService.getAdsMe());
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление картинки объявления", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<byte[]> updateImage(@PathVariable("id") Integer id,
                                              @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(adService.updateImage(id, image));
    }
}