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

</details>

<details> <summary> 4. H2 데이터베이스 설치 </summary>

</details>

<details> <summary> 5. 스프링 부트 설정 - JPA, DB </summary>

</details>
