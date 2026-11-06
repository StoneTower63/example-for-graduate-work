package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class AdMapperTest {

    @Autowired
    private AdMapper adMapper;

    @Test
    void testToDto() {
        UserEntity author = new UserEntity();
        author.setId(1);

        AdEntity entity = new AdEntity();
        entity.setPk(2);
        entity.setTitle("Test Ad");
        entity.setPrice(1000);
        entity.setDescription("Test Description");
        entity.setImage("image.jpg");
        entity.setAuthor(author);

        Ad dto = adMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(2, dto.getPk());
        assertEquals("Test Ad", dto.getTitle());
        assertEquals(1000, dto.getPrice());
        assertEquals("image.jpg", dto.getImage());
        assertEquals(1, dto.getAuthor());
    }

    @Test
    void testToExtendedDto() {
        UserEntity author = new UserEntity();
        author.setId(1);
        author.setFirstName("Ivan");
        author.setLastName("Ivanov");
        author.setEmail("test@mail.com");
        author.setPhone("+79991234567");

        AdEntity entity = new AdEntity();
        entity.setPk(2);
        entity.setTitle("Test Ad");
        entity.setPrice(1000);
        entity.setDescription("Test Description");
        entity.setImage("image.jpg");
        entity.setAuthor(author);

        ExtendedAd dto = adMapper.toExtendedDto(entity);

        assertNotNull(dto);
        assertEquals(2, dto.getPk());
        assertEquals("Test Ad", dto.getTitle());
        assertEquals("Ivan", dto.getAuthorFirstName());
        assertEquals("Ivanov", dto.getAuthorLastName());
        assertEquals("test@mail.com", dto.getEmail());
        assertEquals("+79991234567", dto.getPhone());
    }

    @Test
    void testToEntity() {
        CreateOrUpdateAd dto = new CreateOrUpdateAd();
        dto.setTitle("Test Ad");
        dto.setPrice(1000);
        dto.setDescription("Test Description");

        AdEntity entity = adMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Test Ad", entity.getTitle());
        assertEquals(1000, entity.getPrice());
        assertEquals("Test Description", entity.getDescription());
    }
}