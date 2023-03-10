package com.likelion.mutsasns.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.mutsasns.domain.dto.request.post.PostRequest;
import com.likelion.mutsasns.domain.dto.response.post.PostCreateResponse;
import com.likelion.mutsasns.domain.dto.response.post.PostDeleteResponse;
import com.likelion.mutsasns.domain.dto.response.post.PostResponse;
import com.likelion.mutsasns.domain.dto.response.post.PostUpdateResponse;
import com.likelion.mutsasns.exception.AppException;
import com.likelion.mutsasns.exception.ErrorCode;
import com.likelion.mutsasns.service.PostService;
import com.likelion.mutsasns.support.PostFixture;
import com.likelion.mutsasns.support.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostApiController.class)
class PostApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    PostRequest saveRequest = PostRequest.builder()
            .title("????????? ??????")
            .body("???????????????.")
            .build();

    @Test
    @DisplayName("????????? ????????? ?????? ??????")
    @WithMockUser
    void find_post_success() throws Exception {
        UserFixture userFixture = new UserFixture();
        PostFixture postFixture = new PostFixture(userFixture.createUser());

        when(postService.findAllPost(any(Pageable.class)))
                .thenReturn(PostResponse.of(new PageImpl<>(List.of(postFixture.createPost()))));

        mockMvc.perform(get("/api/v1/posts")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ??????")
    @WithMockUser
    void find_one_post_success() throws Exception {
        PostResponse postResponse = PostResponse.builder()
                .id(1)
                .title("???????????????.")
                .body("???????????????.")
                .createdAt(LocalDateTime.now())
                .userName("root")
                .build();

        when(postService.findOne(any())).thenReturn(postResponse);

        mockMvc.perform(get("/api/v1/posts/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.title").exists())
                .andExpect(jsonPath("$.result.body").exists())
                .andExpect(jsonPath("$.result.userName").exists());
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    @WithMockUser
    void create_post_success() throws Exception {
        when(postService.createPost(any(), any())).thenReturn(new PostCreateResponse("????????? ?????? ??????", 1));

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists());
    }

    @Test
    @DisplayName("????????? ?????? ?????? - Bearer Token??? ?????? ??????")
    @WithAnonymousUser
    void create_post_fail_not_bearer_token() throws Exception {
        when(postService.createPost(any(), any())).thenThrow(new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage()));

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_TOKEN.getHttpStatus().value()));
    }

    @Test
    @DisplayName("????????? ?????? ?????? - JWT??? ???????????? ?????? ??????")
    @WithAnonymousUser
    void create_post_fail_invalid_token() throws Exception {
        when(postService.createPost(any(), any())).thenThrow(new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage()));

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_TOKEN.getHttpStatus().value()));
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    @WithMockUser
    void update_post_success() throws Exception {
        when(postService.updatePost(any(), any(), any()))
                .thenReturn(new PostUpdateResponse("????????? ?????? ??????", 1));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("????????? ?????? ?????? - ?????? ??????")
    @WithAnonymousUser
    void update_post_fail_authentication_fail() throws Exception {
        when(postService.updatePost(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }

    @Test
    @DisplayName("????????? ?????? ?????? - ????????? ?????????")
    @WithMockUser
    void update_post_fail_not_same_user() throws Exception {
        when(postService.updatePost(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }

    @Test
    @DisplayName("????????? ?????? ?????? - ?????????????????? ??????")
    @WithMockUser
    void update_post_fail_database_error() throws Exception {
        when(postService.updatePost(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.DATABASE_ERROR, ErrorCode.DATABASE_ERROR.getMessage()));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    @WithMockUser
    void delete_post_success() throws Exception {
        when(postService.deletePost(any(), any()))
                .thenReturn(new PostDeleteResponse("????????? ?????? ??????", 1));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("????????? ?????? ?????? - ?????? ??????")
    @WithAnonymousUser
    void delete_post_fail_authentication_fail() throws Exception {
        when(postService.deletePost(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("????????? ?????? ?????? - ????????? ?????????")
    @WithMockUser
    void delete_post_fail_not_same_user() throws Exception {
        when(postService.deletePost(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }

    @Test
    @DisplayName("????????? ?????? ?????? - ?????????????????? ??????")
    @WithMockUser
    void delete_post_fail_database_error() throws Exception {
        when(postService.deletePost(any(), any()))
                .thenThrow(new AppException(ErrorCode.DATABASE_ERROR, ErrorCode.DATABASE_ERROR.getMessage()));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ??????")
    @WithMockUser
    void find_own_post_success() throws Exception {
        UserFixture userFixture = new UserFixture();
        PostFixture postFixture = new PostFixture(userFixture.createUser());

        when(postService.findOwn(any(), any()))
                .thenReturn(PostResponse.of(new PageImpl<>(List.of(postFixture.createPost()))));

        mockMvc.perform(get("/api/v1/posts/my")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ?????? - ????????? ?????? ?????? ??????")
    void find_own_post_fail() throws Exception {
        when(postService.findOwn(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(get("/api/v1/posts/my")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }
}