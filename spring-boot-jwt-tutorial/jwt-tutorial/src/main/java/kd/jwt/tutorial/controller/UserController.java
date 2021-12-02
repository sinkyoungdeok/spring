package kd.jwt.tutorial.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import kd.jwt.tutorial.dto.UserDto;
import kd.jwt.tutorial.entity.User;
import kd.jwt.tutorial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("hello");
  }

  @PostMapping("/test-redirect")
  public void testRedirect(HttpServletResponse response) throws IOException {
    response.sendRedirect("/api/user");
  }

  @PostMapping("/signup")
  public ResponseEntity<User> signup(
      @Valid @RequestBody UserDto userDto
  ) {
    return ResponseEntity.ok(userService.signup(userDto));
  }

  @GetMapping("/user")
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public ResponseEntity<User> getMyUserInfo(HttpServletRequest request) {
    return ResponseEntity.ok(userService.getMyUserWithAuthorities());
  }

  @GetMapping("/user/{username}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<User> getUserInfo(@PathVariable String username) {
    return ResponseEntity.ok(userService.getUserWithAuthorities(username));
  }
}
