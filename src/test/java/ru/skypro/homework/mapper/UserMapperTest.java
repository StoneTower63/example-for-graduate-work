package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testToDto() {
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setEmail("test@mail.com");
        entity.setFirstName("Ivan");
        entity.setLastName("Ivanov");
        entity.setPhone("+79991234567");
        entity.setRole(ru.skypro.homework.dto.Role.USER);

        User dto = userMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("test@mail.com", dto.getEmail());
        assertEquals("Ivan", dto.getFirstName());
        assertEquals("Ivanov", dto.getLastName());
        assertEquals("+79991234567", dto.getPhone());
        assertEquals(ru.skypro.homework.dto.Role.USER, dto.getRole());
    }

    @Test
    void testToEntity() {
        Register register = new Register();
        register.setUsername("test@mail.com");
        register.setPassword("password");
        register.setFirstName("Ivan");
        register.setLastName("Ivanov");
        register.setPhone("+79991234567");
        register.setRole(ru.skypro.homework.dto.Role.USER);

        UserEntity entity = userMapper.toEntity(register);

        assertNotNull(entity);
        assertEquals("test@mail.com", entity.getEmail());
        assertEquals("Ivan", entity.getFirstName());
        assertEquals("Ivanov", entity.getLastName());
        assertEquals("+79991234567", entity.getPhone());
        assertEquals(ru.skypro.homework.dto.Role.USER, entity.getRole());
    }
}