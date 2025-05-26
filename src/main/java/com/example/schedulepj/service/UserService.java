package com.example.schedulepj.service;

import com.example.schedulepj.dto.UserDto;
import com.example.schedulepj.entity.User;
import com.example.schedulepj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 회원가입
    public UserDto.Response signup(UserDto.SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User(request.getUsername(), request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);

        return convertToResponse(savedUser);
    }

    // 로그인
    public User login(UserDto.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    // 모든 유저 조회
    @Transactional(readOnly = true)
    public List<UserDto.Response> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 유저 조회
    @Transactional(readOnly = true)
    public UserDto.Response findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return convertToResponse(user);
    }

    // 유저 수정 (비밀번호 제외)
    public UserDto.Response updateUser(Long id, UserDto.UpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        return convertToResponse(user);
    }

    // 유저 삭제
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        userRepository.deleteById(id);
    }

    // Entity -> DTO 변환
    private UserDto.Response convertToResponse(User user) {
        return new UserDto.Response(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
