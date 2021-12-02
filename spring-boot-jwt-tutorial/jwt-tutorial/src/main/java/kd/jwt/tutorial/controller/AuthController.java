package kd.jwt.tutorial.controller;

import kd.jwt.tutorial.dto.LoginDto;
import kd.jwt.tutorial.dto.TokenDto;
import kd.jwt.tutorial.jwt.JwtFilter;
import kd.jwt.tutorial.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
    this.tokenProvider = tokenProvider;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

    /*
     authenticate메소드가 실행이 될 때
     CustomUserDetailsService의 loadUserByUsername메소드가 실행된다.
     */
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication); // Authentication객체를 SecurityContext에 저장

    String jwt = tokenProvider.createToken(authentication);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    // JWT Token을 Response Header에도 넣어주고 TokenDto를 이용해서 Response Body에도 넣어서 리턴한다. 
    return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
  }
}