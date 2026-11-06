package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с комментариями.
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, AdRepository adRepository,
                              UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    private UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @Override
    public Comments getComments(Integer id) {
        List<Comment> comments = commentRepository.findAllByAd_Pk(id).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
        Comments result = new Comments();
        result.setCount(comments.size());
        result.setResults(comments);
        return result;
    }

    @Override
    public Comment addComment(Integer id, CreateOrUpdateComment comment) {
        log.info("Добавление комментария к объявлению с id: {}", id);
        UserEntity user = getCurrentUser();
        AdEntity ad = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));

        CommentEntity commentEntity = commentMapper.toEntity(comment);
        commentEntity.setAuthor(user);
        commentEntity.setAd(ad);
        commentEntity.setCreatedAt(System.currentTimeMillis());

        CommentEntity savedComment = commentRepository.save(commentEntity);
        log.info("Комментарий создан с id: {}", savedComment.getPk());
        return commentMapper.toDto(savedComment);
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        log.info("Удаление комментария с id: {}", commentId);
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        UserEntity user = getCurrentUser();
        if (!comment.getAuthor().getId().equals(user.getId())) {
            log.warn("Попытка удаления чужого комментария пользователем {}", user.getEmail());
            throw new RuntimeException("Нет прав на удаление этого комментария");
        }
        commentRepository.delete(comment);
        log.info("Комментарий с id: {} удален", commentId);
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment) {
        log.info("Обновление комментария с id: {}", commentId);
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        UserEntity user = getCurrentUser();
        if (!commentEntity.getAuthor().getId().equals(user.getId())) {
            log.warn("Попытка редактирования чужого комментария пользователем {}", user.getEmail());
            throw new RuntimeException("Нет прав на редактирование этого комментария");
        }
        commentEntity.setText(comment.getText());
        CommentEntity updatedComment = commentRepository.save(commentEntity);
        log.info("Комментарий с id: {} обновлен", commentId);
        return commentMapper.toDto(updatedComment);
    }
}