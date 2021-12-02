package kd.jwt.tutorial.service;

import java.util.Collections;
import kd.jwt.tutorial.dto.UserDto;
import kd.jwt.tutorial.entity.Authority;
import kd.jwt.tutorial.entity.User;
import kd.jwt.tutorial.repository.UserRepository;
import kd.jwt.tutorial.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User signup(UserDto userDto) {
    if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
      throw new RuntimeException("이미 가입되어 있는 유저입니다.");
    }

    Authority authority = Authority.builder()
        .authorityName("ROLE_USER")
        .build();

    User user = User.builder()
        .username(userDto.getUsername())
        .password(passwordEncoder.encode(userDto.getPassword()))
        .nickname(userDto.getNickname())
        .authorities(Collections.singleton(authority))
        .activated(true)
        .build();

    return userRepository.save(user);
  }

  // username 으로 유저객체와 권한정보를 가져오는 메소드
  @Transactional(readOnly = true)
  public User getUserWithAuthorities(String username) {
    return userRepository.findOneWithAuthoritiesByUsername(username).orElse(null);
  }
  // 현재 SecurityContext에 저장된 유저정보와 권한정보를 가져오는 메소드
  @Transactional(readOnly = true)
  public User getMyUserWithAuthorities() {
    return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null);
  }

}