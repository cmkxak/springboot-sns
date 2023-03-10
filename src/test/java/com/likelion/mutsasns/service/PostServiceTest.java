package com.likelion.mutsasns.service;

import com.likelion.mutsasns.domain.dto.request.post.PostRequest;
import com.likelion.mutsasns.domain.entity.Post;
import com.likelion.mutsasns.domain.entity.User;
import com.likelion.mutsasns.exception.AppException;
import com.likelion.mutsasns.exception.ErrorCode;
import com.likelion.mutsasns.repository.PostRepository;
import com.likelion.mutsasns.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceTest {
    private PostService postService;

    private User user;
    private User mockUser;

    private Post post;
    private Post mockPost;

    private PostRepository postRepository = mock(PostRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    private Integer postId;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, userRepository);

        mockUser = mock(User.class);
        user = User.registerUser("testUser", "1q2w3e4r");

        post = Post.createPost("제목 입니다.", "내용 입니다.", user);
        mockPost = mock(Post.class);

        postId = 100;
    }

    @Test
    @DisplayName("포스트 상세 조회 - 성공")
    void get_one_post() {

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        Assertions.assertDoesNotThrow(() -> {
            postService.findOne(post.getId());
        });
    }

    @Test
    @DisplayName("포스트 등록 - 성공")
    void create_post_success() {
        when(userRepository.findByUserName(mockUser.getUserName())).thenReturn(Optional.of(mockUser));
        when(postRepository.save(any())).thenReturn(mockPost);

        Assertions.assertDoesNotThrow(() -> {
            postService.createPost(new PostRequest("제목입니다.", "내용입니다."), mockUser.getUserName());
        });
    }

    @Test
    @DisplayName("포스트 등록 - 실패 : 유저가 존재하지 않음")
    void create_post_fail() {
        when(userRepository.findByUserName(mockUser.getUserName())).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(mockPost);

        AppException appException = assertThrows(AppException.class, () -> {
            postService.createPost(new PostRequest("제목입니다.", "내용입니다."), mockUser.getUserName());
        });

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, appException.getErrorCode());
    }

    @Test
    @DisplayName("포스트 수정 - 실패 : 포스트 존재하지 않음")
    @WithMockUser
    void update_post_fail() {
        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        AppException appException = Assertions.assertThrows(AppException.class, () -> {
            postService.updatePost(postId, new PostRequest("변경된 제목.", "변경된 내용"), user.getUserName());
        });

        assertEquals(ErrorCode.POST_NOT_FOUND, appException.getErrorCode());
    }

    @Test
    @DisplayName("포스트 수정 - 실패 : 작성자와 유저가 다른 경우")
    void update_post_fail2() {
        User otherUser = User.registerUser("유저2", "1q2w3e4r5");

        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post(postId, "제목", "내용", user)));

        Assertions.assertThrows(AppException.class, () -> {
            postService.updatePost(postId, new PostRequest("변경된 제목", "변경된 내용"), otherUser.getUserName());
        });
    }

    @Test
    @DisplayName("포스트 수정 - 실패 : 유저 존재하지 않음")
    void update_post_fail3() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post(postId, "제목", "내용", mockUser)));

        AppException appException = Assertions.assertThrows(AppException.class, () -> {
            postService.updatePost(postId, new PostRequest("변경된 제목", "변경된 내용"), user.getUserName());
        });

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, appException.getErrorCode());
    }

    @Test
    @DisplayName("포스트 삭제 - 실패 : 유저 존재하지 않음")
    void delete_post_fail() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post(postId, "제목", "내용", mockUser)));
        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.empty());

        AppException appException = Assertions.assertThrows(AppException.class, () -> {
            postService.deletePost(postId, user.getUserName());
        });

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, appException.getErrorCode());
    }

    @Test
    @DisplayName("포스트 삭제 - 실패 : 포스트 존재하지 않음")
    void delete_post_fail2() {
        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class, () -> {
            postService.deletePost(postId, user.getUserName());
        });

        assertEquals(ErrorCode.POST_NOT_FOUND, appException.getErrorCode());
    }
}