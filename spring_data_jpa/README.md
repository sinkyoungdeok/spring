# [1. 프로젝트 환경설정](./1.project-setting)

<details> <summary> 1. 프로젝트 생성 </summary>

</details>

<details> <summary> 2. 라이브러리 살펴보기</summary>

- gradle 의존관계 보기
    - `./gradlew dependencies -configuration compileClasspath`

**스프링 부트 라이브러리 살펴보기**
- spring-boot-starter-web
    - spring-boot-starter-tomcat: 톰캣 (웹서버)
    - spring-webmvc: 스프링 웹 MVC
- spring-boot-starter-data-jpa
    - spring-boot-starter-aop
    - spring-boot-starter-jdbc
        - HikariCP 커넥션 풀 (부트 2.0 기본)
    - hibernate + JPA: 하이버네이트 + JPA
    - spring-data-jpa: 스프링 데이터 JPA
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
- spring-boot
    - spring-core
- spring-boot-starter-logging
    - logback, slf4j

### 테스트 라이브러리
- spring-boot-starter-test
    - junit: 테스트 프레임워크, 스프링 부트 2.2부터 junit5( `jupiter` ) 사용
        - 과거 버전은 `vintage`
    - mockito: 목 라이브러리
    - assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
        - https://joel-costigliola.github.io/assertj/index.html
    - spring-test: 스프링 통합 테스트 지원
- 핵심 라이브러리
    - 스프링 MVC
    - 스프링 ORM
    - JPA, 하이버네이트
    - 스프링 데이터 JPA
- 기타 라이브러리
    - H2 데이터베이스 클라이언트
    - 커넥션 풀: 부트 기본은 HikariCP
    - 로깅 SLF4J & LogBack
    - 테스트


</details>

<details> <summary> 3. H2 데이터베이스 설치 </summary>

- https://www.h2database.com/html/main.html
- 다운로드 및 설치
- h2 데이터베이스 버전은 스프링 부트 버전에 맞춘다.
- 권한 주기: `chmod 755 h2.sh`
- 데이터 베이스 파일 생성 방법
    - `jdbc:h2:~/datajpa` (최소 한번)
    - `~/datajpa.mv.db`파일 생성 확인
    - 이후 부터는 `jdbc:h2:tcp://localhost/~/datajpa` 이렇게 접속

- 주의: H2 데이터베이스의 MVCC 옵션은 G2 1.4.198 버전부터 제거되었다. 1.4.200 버전에서는 MVCC옵션을 사용하면 오류가 발생한다.

</details>

<details> <summary> 4. 스프링 데이터 JPA와 DB 설정, 동작 확인 </summary>

`main/resources/application.yml`
```
spring:
 datasource:
 url: jdbc:h2:tcp://localhost/~/jpashop
 username: sa
 password:
 driver-class-name: org.h2.Driver
 jpa:
 hibernate:
 ddl-auto: create
 properties:
 hibernate:
# show_sql: true
 format_sql: true
logging.level:
 org.hibernate.SQL: debug
# org.hibernate.type: trace
```

- spring.jpa.hibernate.ddl-auto: create
    - 이 옵션은 애플리케이션 실행 시점에 테이블을 drop 하고, 다시 생성한다.

> 참고: 모든 로그 출력은 가급적 로거를 통해 남겨야 한다
> `show_sql` : 옵션은 `System.out` 에 하이버네이트 실행 SQL을 남긴다.
> `org.hibernate.SQL` : 옵션은 logger를 통해 하이버네이트 실행 SQL을 남긴다

### 실제 동작하는지 확인하기
- Entity, Repository 동작 확인
- jar 빌드해서 동작 확인

> 참고: 스프링 부트를 통해 복잡한 설정이 다 자동화 되었다. `persistence.xml`도 없고,
> `LocalContainerEntityManagerFactoryBean`도 없다. 스프링 부트를 통한 추가 설정은
> 스프링 부트 메뉴얼을 참고하고, 스프링 부트를 사용하지 않고 순수 스프링과 JPA 설정 방법은 자바
> ORM표준 JPA 프로그래밍 책을 참고

### 쿼리 파라미터 로그 남기기
- 로그에 다음을 추가하기 `org.hiberrnate.type`: SQL 실행 파라미터를 로그로 남긴다.
- 외부 라이브 러리 사용
    - https://github.com/gavlyukovskiy/spring-boot-data-source-decorator
    - 스프링 부트를 사용하면 이 라이브러리만 추가하면 된다.<br>
    `implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6'`

> 참고: 쿼리 파라미터를 로그로 남기는 외부 라이브러리는 시스템 자원을 사용하므로, 개발 단계에서는 편하게
> 사용해도 된다. 하지만 운영시스템에 적용하려면 꼭 성능테스트를 하고 사용하는 것이 좋다.


</details>


# [2. 예제 도메인 모델](./2.example-domain-model)

<details> <summary> 1. 예제 도메인 모델과 동작확인 </summary>

**엔티티 클래스**
![image](https://user-images.githubusercontent.com/28394879/134102684-acc050af-2014-40ac-ba32-92502e61b8b8.png)

**ERD**
![image](https://user-images.githubusercontent.com/28394879/134102731-e9f9f990-0f73-4ffd-b31d-de8a37b97dd6.png)


**Member 엔티티**
- 롬복 설명
    - @Setter: 실무에서 가급적 Setter는 사용하지 않기
    - @NoArgsConstructor AccessLevel.PROTECTED: 기본 생성자 막고 싶은데, JPA 스팩상 PROTECTED로 열어 두어야 함
    - @ToString: 가급적 내부 필드만(연관관계 없는 필드만)
- `changeTeam()`으로 양방향 연관관계 한번에 처리(연관관계 편의 메소드)


**Team 엔티티**
- Member와 Team은 양방향 연관관계, `Member.team`이 연관관계의 주인, `Team.members`는 연관관계의 주인이 아님,
따라서 `Member.team`이 데이터베이스 외래키 값을 변경, 반대편은 읽기만 가능

**데이터 확인 테스트**
- 가급적 순수 JPA로 동작 확인 (뒤에서 변경)
- db 테이블 결과 확인
- 지연 로딩 동작 확인

</details>







# [3. 공통 인터페이스 기능](./3.common-interface-function)

<details> <summary> 1. 순수 JPA 기반 리포지토리 만들기 </summary>

</details>



<details> <summary> 2. 공통 인터페이스 설정 </summary>

</details>



<details> <summary> 3. 공통 인터페이스 적용 </summary>

</details>



<details> <summary> 4. 공통 인터페이스 분석 </summary>

</details>
