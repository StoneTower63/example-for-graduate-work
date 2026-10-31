package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

/**
 * Сервис для работы с комментариями.
 */
public interface CommentService {
    Comments getComments(Integer id);

    Comment addComment(Integer id, CreateOrUpdateComment comment);

    void deleteComment(Integer adId, Integer commentId);

    Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment);
}
