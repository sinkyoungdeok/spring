# [1. 프로젝트 환경설정](./1.project-setting)

<details> <summary> 1. 프로젝트 생성 </summary>

</details>

<details> <summary> 2. Querydsl 설정과 검증 </summary>

### Querydsl 설정과 검증
- `build.gradle`에 주석을 참고해서 querydsl 설정 추가
```gradle
plugins {
 id 'org.springframework.boot' version ‘2.2.2.RELEASE'
 id 'io.spring.dependency-management' version '1.0.8.RELEASE'
 //querydsl 추가
 id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
 id 'java'
}
group = 'study'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'
configurations {
 compileOnly {
 extendsFrom annotationProcessor
 }
}
repositories {
 mavenCentral()
}
dependencies {
 implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
 implementation 'org.springframework.boot:spring-boot-starter-web'
 //querydsl 추가
 implementation 'com.querydsl:querydsl-jpa'
 compileOnly 'org.projectlombok:lombok'
 runtimeOnly 'com.h2database:h2'
 annotationProcessor 'org.projectlombok:lombok'
 testImplementation('org.springframework.boot:spring-boot-starter-test') {
 exclude group: ‘org.junit.vintage’, module: ‘junit-vintage-engine'
 }
}
test {
 useJUnitPlatform()
}
//querydsl 추가 시작
def querydslDir = "$buildDir/generated/querydsl"
querydsl {
 jpa = true
 querydslSourcesDir = querydslDir
 }
 sourceSets {
  main.java.srcDir querydslDir
 }
 configurations {
  querydsl.extendsFrom compileClasspath
 }
 compileQuerydsl {
  options.annotationProcessorPath = configurations.querydsl
 }
 //querydsl 추가 끝
```

### Querydsl 환경설정 검증
**검증용 엔티티 생성**
```java
package study.querydsl.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
@Getter @Setter
public class Hello {
 @Id @GeneratedValue
 private Long id;
}
```

#### 검증용 Q 타입 생성
**Gradle Intellij 사용법**
- Gradle -> Tasks -> build -> clean
- Gradle -> Tasks -> other -> compileQuerydsl

**Gradle 콘솔 사용법**
- ./gradlew clean compileQuerydsl

#### Q타입 생성 확인
- build -> generated -> querydsl
    - study.querydsl.entity.QHello.java 파일이 생성되어 있어야 함

> 참고: Q타입은 컴파일 시점에 자동 생성되므로 버전관리(GIT)에 포함하지 않는 것이 좋다. 앞서 설정에서
> 생성 위치를 gradle build 폴더 아래 생성되도록 했기 때문에 이 부분도 자연스럽게 해결된다.
> (대부분 gradle build 폴더를 git에 포함하지 않는다.)

**테스트 케이스로 실행 검증**
```java
package study.querydsl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Hello;
import study.querydsl.entity.QHello;
import javax.persistence.EntityManager;
import java.util.List;
@SpringBootTest
@Transactional
class QuerydslApplicationTests {
@Autowired
EntityManager em;
@Test
void contextLoads() {
Hello hello = new Hello();
em.persist(hello);
JPAQueryFactory query = new JPAQueryFactory(em);
QHello qHello = QHello.hello; //Querydsl Q타입 동작 확인
Hello result = query
.selectFrom(qHello)
.fetchOne();
Assertions.assertThat(result).isEqualTo(hello);
//lombok 동작 확인 (hello.getId())
Assertions.assertThat(result.getId()).isEqualTo(hello.getId());
}
}
```
- Querydsl Q타입이 정상 동작하는가?
- lombok이 정상 동작 하는가?

> 참고: 스프링 부트에 아무런 설정도 하지 않으면 h2 DB를 메모리 모드로 JVM안에서 실행한다.

</details>

<details> <summary> 3. 라이브러리 살펴보기 </summary>

### 라이브러리 살펴보기
**gradle 의존관계 보기**
- `./gradlew dependencies --configuration compileClasspath`

**Querydsl 라이브러리 살펴보기**
- querydsl-apt: Querydsl 관련 코드 생성 기능 제공
- querydsl-jpa: querydsl 라이브러리

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

**테스트 라이브러리**
- spring-boot-starter-test
    - junit: 테스트 프레임워크, 스프링 부트 2.2부터 junit5( jupiter ) 사용
        - 과거 버전은 vintage
    - mockito: 목 라이브러리
    - assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
        - https://joel-costigliola.github.io/assertj/index.html
    - spring-test: 스프링 통합 테스트 지원

- 핵심 라이브러리
    - 스프링 MVC
    - JPA, 하이버네이트
    - 스프링 데이터 JPA
    - Queryds

- 기타 라이브러리
    - H2 데이터베이스 클라이언트
    - 커넥션 풀: 부트 기본은 HikariCP
    - 로깅 SLF4J & LogBack
    - 테스트

</details>

<details> <summary> 4. H2 데이터베이스 설치 </summary>

### H2 데이터베이스 설치
- 개발이나 테스트 용도로 가볍고 편리한 DB, 웹 화면 제공

- https://www.h2database.com/html/main.html
- 다운로드 및 설치
- h2 데이터베이스 버전은 스프링 부트 버전에 맞춘다.
- 권한 주기: `chmod 755 h2.sh`
- 데이터 베이스 파일 생성 방법
    - `jdbc:h2:~/querydsl` (최소 한번)
    - `~/querydsl.mv.db`파일 생성 확인
    - 이후 부터는 `jdbc:h2:tcp://localhost/~/querydsl` 이렇게 접속

> 참고: H2 데이터베이스의 MVCC 옵션은 H2 1.4.198 버전부터 제거 되었다. 이후 부터는 옵션 없이
> 사용하면 된다.

> 주의: 가급적 안정화 버전을 사용해라. 1.4.200 버전은 몇가지 오류가 있다.
> 현재 안정화 버전은 1.4.199(2019-03-13) 입니다.
> 다운로드 링크: https://www.h2database.com/html/download.html


</details>

<details> <summary> 5. 스프링 부트 설정 - JPA, DB </summary>

</details>
