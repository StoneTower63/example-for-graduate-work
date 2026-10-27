package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

/**
 * Реализация сервиса для работы с комментариями.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public Comments getComments(Integer id) {
        return new Comments();
    }

    @Override
    public Comment addComment(Integer id, CreateOrUpdateComment comment) {
        return new Comment();
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        // TODO: Логика удаления
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment) {
        return new Comment();
    }
}
