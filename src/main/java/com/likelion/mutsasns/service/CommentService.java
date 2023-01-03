package com.likelion.mutsasns.service;

import com.likelion.mutsasns.domain.dto.request.comment.CommentRequest;
import com.likelion.mutsasns.domain.dto.response.comment.CommentDeleteResponse;
import com.likelion.mutsasns.domain.dto.response.comment.CommentResponse;
import com.likelion.mutsasns.domain.entity.Comment;
import com.likelion.mutsasns.domain.entity.Post;
import com.likelion.mutsasns.domain.entity.User;
import com.likelion.mutsasns.exception.AppException;
import com.likelion.mutsasns.exception.ErrorCode;
import com.likelion.mutsasns.repository.CommentRepository;
import com.likelion.mutsasns.repository.PostRepository;
import com.likelion.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private static final String DELETE_COMMENT_MESSAGE = "댓글 삭제 완료";
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<CommentResponse> findAll(Integer postId, Pageable pageable) {
        validatePostExists(postId);
        return CommentResponse.of(commentRepository.findAllByPostId(postId, pageable));
    }

    public CommentResponse createComment(Integer postId, CommentRequest request, String userName) {
        Post findPost = validatePostExists(postId);
        User findUser = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Comment savedComment = commentRepository.save(Comment.createComment(request.getComment(), findPost, findUser));
        return CommentResponse.of(savedComment);
    }

    public CommentResponse updateComment(Integer postId, Integer commentId, CommentRequest request, String userName) {
        validatePostExists(postId);
        Comment comment = validateAuthorizedUser(commentId, userName);
        comment.updateComment(request.getComment());
        return CommentResponse.of(comment);
    }

    public CommentDeleteResponse deleteComment(Integer postId, Integer commentId, String userName) {
        validatePostExists(postId);
        validateAuthorizedUser(commentId, userName);
        commentRepository.deleteById(commentId);
        return CommentDeleteResponse.of(DELETE_COMMENT_MESSAGE, commentId);
    }

    private Comment validateAuthorizedUser(Integer commentId, String userName) {
        User findUser = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));
        if (comment.getUser().getId() == findUser.getId())
            return comment;
        else throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
    }

    private Post validatePostExists(Integer postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
    }
}