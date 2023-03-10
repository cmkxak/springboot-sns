package com.likelion.mutsasns.service;

import com.likelion.mutsasns.domain.dto.request.user.UserChangeRoleRequest;
import com.likelion.mutsasns.domain.entity.User;
import com.likelion.mutsasns.enumerate.UserRole;
import com.likelion.mutsasns.exception.AppException;
import com.likelion.mutsasns.exception.ErrorCode;
import com.likelion.mutsasns.repository.UserRepository;
import com.likelion.mutsasns.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public User join(String userName, String password) {
        validateDuplicateUser(userName);
        String encPassword = passwordEncoder.encode(password);
        User user = User.registerUser(userName, encPassword);
        return userRepository.save(user);
    }

    public String login(String userName, String password) {
        User findUser = findUserByUserName(userName);
        if (!passwordEncoder.matches(password, findUser.getPassword()))
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 비밀번호 입니다.");
        return tokenProvider.createToken(userName, findUser.getRole().getValue());
    }

    /**
     * ADMIN 회원이 일반 회원을 ADMIN 혹은 USER로 등급을 변경하는 기능
     */
    @Transactional
    public UserRole changeRole(Integer id, UserChangeRoleRequest request) {
        User findUser = findUserById(id);
        findUser.changeRole(request.getRole());
        return findUser.getRole();
    }

    private void validateDuplicateUser(String userName) {
        if (userRepository.existsByUserName(userName))
            throw new AppException(ErrorCode.DUPLICATED_USER_NAME, userName + "는 이미 있습니다.");
    }

    private User findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND, "변경하려는 유저가 존재하지 않습니다."));
    }

    private User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND, userName + "는 존재하지 않는 유저입니다."));
    }
}
