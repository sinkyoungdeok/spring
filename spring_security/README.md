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

### 인증 API - HTTP Basic 인증, BasicAuthenticationFilter
![image](https://user-images.githubusercontent.com/28394879/134798416-434154aa-99f8-45e8-849c-9c38fef1015b.png)
- HTTP는 자체적인 인증 관련 기능을 제공하며 HTTP 표준에 정의된 가장 단순한 인증 방법이다.
- 간단한 설정과 Stateless가 장점 - Session Cookie(JSESSIONID) 사용하지 않음
- 보호 자원 접근시 서버가 클라이언트에게 401 Unauthorized 응답과 함께 WWW-Authenticate header를 기술해서 인증교수를 보냄
- Client는 ID:Password 값을 Base64로 Encoding한 문자열을 Authorization Header에 추가한 뒤 Server에 Resource를 요청
    - Authorization: Basic cmVzdDpyZXN0
- ID, Password가 Base64로 Encoding되어 있어 ID, Password가 외부에게 쉽게 노출되는 구조이기 떄문에 SSL이나 TLS는 필수이다.

**HTTP Basic 인증 코드**
```java
protected void configure(HttpSecurity http) throws Exception {
	http.httpBasic();
}

```

**BasicAuthenticationFilter**

![image](https://user-images.githubusercontent.com/28394879/134798517-a0ce77f2-999d-45dd-b337-5643e32cb22f.png)

</details>

<details> <summary> 3. Form Login 인증 </summary>

## 3. Form Login 인증

![image](https://user-images.githubusercontent.com/28394879/134798648-bba75da6-91e8-419c-bf9f-9294bb273842.png)

**http.formLogin()**

- Form 로그인 인증 기능이 작동함

```java
protected void configure(HttpSecurity http) throws Exception {
	 http.formLogin()
                .loginPage("/login.html")   				// 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/home")				// 로그인 성공 후 이동 페이지
	         .failureUrl("/login.html?error=true")		// 로그인 실패 후 이동 페이지
                .usernameParameter("username")			// 아이디 파라미터명 설정
                .passwordParameter("password")			// 패스워드 파라미터명 설정
                .loginProcessingUrl("/login")			// 로그인 Form Action Url
                .successHandler(loginSuccessHandler())		// 로그인 성공 후 핸들러
                .failureHandler(loginFailureHandler())		// 로그인 실패 후 핸들러
}

```

</details>

<details> <summary> 4. Form Login 인증 필터: UsernamePasswordAuthenticationFilter </summary>

## 4. Form Login 인증 필터: UsernamePasswordAuthenticationFilter

### 인증 API - Login Form 인증

![image](https://user-images.githubusercontent.com/28394879/134799534-64da8c33-622a-483d-8fac-eb9d63ec1ed4.png)

### 인증 API - UsernamePasswordAuthenticationFilter

![image](https://user-images.githubusercontent.com/28394879/134799584-77d9210c-da02-445e-9d90-38a4227890dd.png)

</details>

<details> <summary> 5. Logout 처리, LogoutFilter </summary>

## 5. Logout 처리, LogoutFilter

### 인증 API - Form 인증
![image](https://user-images.githubusercontent.com/28394879/135270088-65f21896-8d8f-45c8-9e8f-29a92b37210f.png)

### 인증 API - Logout
- http.logout(): 로그아웃 기능이 작동함
```java
protected void configure(HttpSecurity http) throws Exception {
	 http.logout()						// 로그아웃 처리
                .logoutUrl(＂/logout＂)				// 로그아웃 처리 URL
	         .logoutSuccessUrl(＂/login＂)			// 로그아웃 성공 후 이동페이지
                .deleteCookies(＂JSESSIONID“, ＂remember-me＂) 	// 로그아웃 후 쿠키 삭제
	         .addLogoutHandler(logoutHandler())		 // 로그아웃 핸들러
                .logoutSuccessHandler(logoutSuccessHandler()) 	// 로그아웃 성공 후 핸들러
}
```
![image](https://user-images.githubusercontent.com/28394879/135270356-6eb73d19-e879-4482-b490-458abe2b08ee.png)

### 인증 API - LogoutFilter
![image](https://user-images.githubusercontent.com/28394879/135270525-3e63ba59-6509-41a1-b06e-8a95540ec951.png)



</details>

<details> <summary> 6. Remember Me 인증 </summary>

## 6. Remember Me 인증
1. 세션이 만료되고 웹 브라우저가 종료된 후에도 어플리케이션이 사용자를 기억하는 기능
2. Remember-Me 쿠키에 대한 Http 요청을 확인한 후 토큰 기반 인증을 사용해 유효성을 검사하고 토큰이 검증되면 사용자는 로그인이 된다
3. 사용자 라이프 사이클
    - 인증 성공(Remember-Me쿠키 설정)
    - 인증 실패(쿠키가 존재하면 쿠키 무효화)
    - 로그아웃(쿠키가 존재하면 쿠키 무효화)

### API - Remember Me 인증
- http.rememberMe(): rememberMe 기능이 작동함
```java
protected void configure(HttpSecurity http) throws Exception {
	http.rememberMe()
		.rememberMeParameter("remember") // 기본 파라미터명은 remember-me
		.tokenValiditySeconds(3600) // Default 는 14일
		.alwaysRemember(true) // 리멤버 미 기능이 활성화되지 않아도 항상 실행
		.userDetailsService(userDetailsService)
}
```


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
