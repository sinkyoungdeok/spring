package kd.jwt.tutorial.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(
            "/h2-console/**"
            ,"/favicon.ico"
            ,"/error"
        );
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
        .antMatchers("/api/hello").permitAll() // "/api/hello"에 대한 요청은 인증없이 접근을 허용
        .anyRequest().authenticated(); // 나머지 요청들은 인증을 받아야됨
  }

}
