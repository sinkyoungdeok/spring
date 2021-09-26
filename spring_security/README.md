# [1. 스프링 시큐리티 기본 API 및 Filter 이해](./1.spring-security-basic-api-and-filter-understanding)

<details> <summary> 1. 프로젝트 구성 및 의존성 추가 </summary>

## 1. 프로젝트 구성 및 의존성 추가

**pom.xml**
```xml
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**스프링 시큐리티의 의존성 추가 시 일어나는 일들**
- 서버가 기동되면 스프링 시큐리티의 초기화 작업 및 보안 설정이 이루어진다.
- 별도의 설정이나 구현을 하지 않아도 기본적인 웹 보안 기능이 현재 시스템에 연동되어 작동함
    1. 모든 요청은 인증이 되어야 자원에 접근이 가능하다.
    2. 인증 밧깅느 폼 로그인 방식과 httpBasic 로그인 방식을 제공한다.
    3. 기본 로그인 페이지를 제공한다.
    4. 기본 계정 한개를 제공한다 - username: user / password: 랜덤 문자열

**문제점**
- 계정 추가, 권한 추가, DB 연동 등
- 기본적인 보안 기능 외에 시스템에서 필요로 하는 더 세부적이고 추가적인 보안기능이 필요


</details>

<details> <summary> 2. 사용자 정의 보안 기능 구현 </summary>

## 2. 사용자 정의 보안 기능 구현

![image](https://user-images.githubusercontent.com/28394879/134797847-a8142a0e-457a-460e-89c4-d8efe4844add.png)

### 인증 API - SecurityConfig 설정
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

@Override
protected void configure(HttpSecurity http) throws Exception { 
	http
		.authorizeRequests()
		.anyRequest().authenticated()
	.and()
		.formLogin();
}

```

</details>

<details> <summary> 3. Form Login 인증 </summary>

</details>

<details> <summary> 4. Form Login 인증 필터: UsernamePasswordAuthenticationFilter </summary>

</details>

<details> <summary> 5. Logout 처리, LogoutFilter </summary>

</details>

<details> <summary> 6. Remember Me 인증 </summary>

</details>
<details> <summary> 7. Remember Me 인증 필터: RememberMeAuthenticationFilter </summary>

</details>

<details> <summary> 8. 익명사용자 인증 필터: AnonymousAuthenticationFilter </summary>

</details>

<details> <summary> 9. 동시 세션 제어, 세션 고정 보호, 세션 정책 </summary>

</details>

<details> <summary> 10. 세션 제어 필터: SessionManagementFilter, ConcurrentSessionFilter </summary>

</details>

<details> <summary> 11. 권한설정과 표현식 </summary>

</details>

<details> <summary> 12. 예외 처리 및 요청 캐시 필터: ExceptionTranslationFilter, RequestCacheAwareFilter </summary>

</details>

<details> <summary> 13. 사이트 간 요청 위조 - CSRF, CsrfFilter </summary>

</details>
