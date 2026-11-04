package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void testToDto() {
        UserEntity author = new UserEntity();
        author.setId(1);
        author.setFirstName("Ivan");
        author.setImage("avatar.jpg");

        AdEntity ad = new AdEntity();
        ad.setPk(2);

        CommentEntity entity = new CommentEntity();
        entity.setPk(3);
        entity.setText("Test Comment");
        entity.setCreatedAt(123456789L);
        entity.setAuthor(author);
        entity.setAd(ad);

        Comment dto = commentMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(3, dto.getPk());
        assertEquals("Test Comment", dto.getText());
        assertEquals(123456789L, dto.getCreatedAt());
        assertEquals(1, dto.getAuthor());
        assertEquals("Ivan", dto.getAuthorFirstName());
        assertEquals("avatar.jpg", dto.getAuthorImage());
    }

    @Test
    void testToEntity() {
        CreateOrUpdateComment dto = new CreateOrUpdateComment();
        dto.setText("Test Comment");

        CommentEntity entity = commentMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Test Comment", entity.getText());
    }
}