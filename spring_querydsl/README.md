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

### 스프링 부트 설정 - JPA, DB

- `application.yml`
```yml
spring:
 datasource:
 url: jdbc:h2:tcp://localhost/~/querydsl
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
- spring.jpa.hibernate.ddl-auto:create
    - 이 옵션은 애플리케이션 실행 시점에 테이블을 drop 하고, 다시 생성한다.

> 참고: 모든 로그 출력은 가급적 로거를 통해 남겨야 한다.
> `show_sql`: 옵션은 `System.out`에 하이버네이트 실행 SQL을 남긴다.
> `org.hibernate.SQL`: 옵션은 logger를 통해 하이버네이트 실행 SQL을 남긴다.

### 쿼리 파라미터 로그 남기기
- 로그에 다음을 추가하기 `org.hibernate.type`: SQL 실행 파라미터를 로그로 남긴다.
- 외부 라이브러리 사용
    - https://github.com/gavlyukovskiy/spring-boot-data-source-decorator
- 스프링 부트를 사용하면 이 라이브러리만 추가하면 된다.
```
implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.8'
```

> 참고: 쿼리 파라미터를 로그로 남기는 외부 라이브러리는 시스템 자원을 사용하므로, 개발 단계에서는
> 편하게 사용해도 된다. 하지만 운영시스템에 적용하려면 꼭 성능테스트를 하고 사용하는 것이 좋다

</details>


# [2. 예제 도메인 모델](./2.example-domain-model)

<details> <summary> 1. 예제 도메인 모델과 동작 확인 </summary>

### 예제 도메인 모델
- 스프링 데이터 JPA와 동일한 예제 도메인 모델

### 예쩨 도메인 모델과 동작확인

**엔티티 클래스**
![image](https://user-images.githubusercontent.com/28394879/134102684-acc050af-2014-40ac-ba32-92502e61b8b8.png)

**ERD**
![image](https://user-images.githubusercontent.com/28394879/134102731-e9f9f990-0f73-4ffd-b31d-de8a37b97dd6.png)

**Member 엔티티**
```java
package study.querydsl.entity;
import lombok.*;
import javax.persistence.*;
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {
 @Id
 @GeneratedValue
 @Column(name = "member_id")
 private Long id;
 private String username;
 private int age;
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "team_id")
 private Team team;
 public Member(String username) {
 this(username, 0);
 }
 public Member(String username, int age) {
 this(username, age, null);
 }
 public Member(String username, int age, Team team) {
 this.username = username;
 this.age = age;
 if (team != null) {
 changeTeam(team);
 }
 }
 public void changeTeam(Team team) {
 this.team = team;
 team.getMembers().add(this);
 }
}
```
- 롬복 설명
    - @Setter: 실무에서 가급적 Setter는 사용하지 않기
    - @NoArgsConstructor AccessLevel.PROTECTED: 기본 생성자 막고 싶은데, JPA 스팩상 PROTECTED로 열어 두어야 함
    - @ToString: 가급적 내부 필드만(연관관계 없는 필드만)
- `changeTeam()`으로 양방향 연관관계 한번에 처리(연관관계 편의 메소드)

**Team 엔티티**
```java
package study.querydsl.entity;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {
 @Id @GeneratedValue
 @Column(name = "team_id")
 private Long id;
 private String name;
 @OneToMany(mappedBy = "team")
 List<Member> members = new ArrayList<>();
 public Team(String name) {
 this.name = name;
 }
}
```
- Member와 Team은 양방향 연관관계, `Member.team`이 연관관계의 주인, `Team.members`는 연관관계의 주인이 아님,
따라서 `Member.team`이 데이터베이스 외래키 값을 변경, 반대편은 읽기만 가능

**데이터 확인 테스트**
```java
package study.querydsl.entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@SpringBootTest
@Transactional
@Commit
public class MemberTest {
 @PersistenceContext
 EntityManager em;
 @Test

 public void testEntity() {
 Team teamA = new Team("teamA");
 Team teamB = new Team("teamB");
 em.persist(teamA);
 em.persist(teamB);
 Member member1 = new Member("member1", 10, teamA);
 Member member2 = new Member("member2", 20, teamA);
 Member member3 = new Member("member3", 30, teamB);
 Member member4 = new Member("member4", 40, teamB);
 em.persist(member1);
 em.persist(member2);
 em.persist(member3);
 em.persist(member4);
 //초기화
 em.flush();
 em.clear();
 //확인
  List<Member> members = em.createQuery("select m from Member m",
 Member.class)
  .getResultList();
  for (Member member : members) {
  System.out.println("member=" + member);
  System.out.println("-> member.team=" + member.getTeam());
  }
  }
 }
```
- 가급적 순수 JPA로 동작 확인 (뒤에서 변경)
- db 테이블 결과 확인
- 지연 로딩 동작 확인


</details>


# [3. 기본 문법](./3.basic-grammar)

<details> <summary> 1. 시작 - JPQL vs Querydsl </summary>

### 시작 - JPQL vs Querydsl
**테스트 기본 코드**
```java
package study.querydsl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
@SpringBootTest
@Transactional
public class QuerydslBasicTest {
 @PersistenceContext
 EntityManager em;
 @BeforeEach
 public void before() {
 Team teamA = new Team("teamA");
 Team teamB = new Team("teamB");
 em.persist(teamA);
 em.persist(teamB);
 Member member1 = new Member("member1", 10, teamA);
 Member member2 = new Member("member2", 20, teamA);
 Member member3 = new Member("member3", 30, teamB);
 Member member4 = new Member("member4", 40, teamB);
 em.persist(member1);
 em.persist(member2);
 em.persist(member3);
 em.persist(member4);
 }
}
```
- 지금부터는 이 예제로 실행

**Querydsl vs JPQL**
```java
@Test
public void startJPQL() {
 //member1을 찾아라.
 String qlString =
 "select m from Member m " +
 "where m.username = :username";
 Member findMember = em.createQuery(qlString, Member.class)
 .setParameter("username", "member1")
 .getSingleResult();
 assertThat(findMember.getUsername()).isEqualTo("member1");
}
@Test
public void startQuerydsl() {
 //member1을 찾아라.
 JPAQueryFactory queryFactory = new JPAQueryFactory(em);
 QMember m = new QMember("m");
 Member findMember = queryFactory
 .select(m)
 .from(m)
 .where(m.username.eq("member1"))//파라미터 바인딩 처리
 .fetchOne();
 assertThat(findMember.getUsername()).isEqualTo("member1");
}
```
- `EntityManager`로 `JPAQueryFactory`생성
- Querydsl은 JPQL 빌더
- JPQL: 문자(실행 시점 오류), Querydsl: 코드(컴파일 시점 오류)
- JPQL: 파라미터 바인딩 직접, Querydsl: 파라미터 바인딩 자동 처리

**JPAQueryFactory를 필드로**
```java
package study.querydsl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
@SpringBootTest
@Transactional
public class QuerydslBasicTest {
 @PersistenceContext
 EntityManager em;
 JPAQueryFactory queryFactory;
 @BeforeEach
 public void before() {
 queryFactory = new JPAQueryFactory(em);
 //…
 }
 @Test
 public void startQuerydsl2() {
 //member1을 찾아라.
 QMember m = new QMember("m");
 Member findMember = queryFactory
 .select(m)
  .from(m)
  .where(m.username.eq("member1"))
  .fetchOne();
  assertThat(findMember.getUsername()).isEqualTo("member1");
  }
 }
```
- JPAQueryFactory를 필드로 제공하면 동시성 문제는 어떻게 될까? 동시성 문제는 JPAQueryFactory를
생성할 때 제공하는 EntityManager(em)에 달려있다. 스프링 프레임워크는 여러 쓰레드에서 동시에 같은
EntityManager에 접근해도, 트랜잭션 마다 별도의 영속성 컨텍스트를 제공하기 때문에, 동시성 문제는
걱정하지 않아도 된다.

</details>

<details> <summary> 2. 기본 Q-Type 활용 </summary>

### 기본 Q-Type 활용
**Q클래스 인스턴스를 사용하는 2가지 방법**
```java
QMember qMember = new QMember("m"); //별칭 직접 지정
QMember qMember = QMember.member; //기본 인스턴스 사용
```

**기본 인스턴스를 static import와 함께 사용**
```java
import static study.querydsl.entity.QMember.*;
@Test
public void startQuerydsl3() {
 //member1을 찾아라.
 Member findMember = queryFactory
 .select(member)
 .from(member)
 .where(member.username.eq("member1"))
 .fetchOne();
 assertThat(findMember.getUsername()).isEqualTo("member1");
}
```
- 다음 설정을 추가하면 실행되는 JPQL을 볼 수 있다.
```xml
spring.jpa.properties.hibernate.use_sql_comments: true
```
> 참고: 같은 테이블을 조인해야 하는 경우가 아니면 기본 인스턴스를 사용하자



</details>

<details> <summary> 3. 검색 조건 쿼리 </summary>

### 검색 조건 쿼리
**기본 검색 쿼리**
```java
@Test
public void search() {
 Member findMember = queryFactory
 .selectFrom(member)
 .where(member.username.eq("member1")
 .and(member.age.eq(10)))
 .fetchOne();
 assertThat(findMember.getUsername()).isEqualTo("member1");
}
```
- 검색 조건은 `.and()`, `.or()`를 메서드 체인으로 연결할 수 있다.

> 참고: `select`, `from`을 `selectFrom`으로 합칠 수 있음

**JPQL이 제공하는 모든 검색 조건 제공**
```java
member.username.eq("member1") // username = 'member1'
member.username.ne("member1") //username != 'member1'
member.username.eq("member1").not() // username != 'member1'
member.username.isNotNull() //이름이 is not null
member.age.in(10, 20) // age in (10,20)
member.age.notIn(10, 20) // age not in (10, 20)
member.age.between(10,30) //between 10, 30
member.age.goe(30) // age >= 30
member.age.gt(30) // age > 30
member.age.loe(30) // age <= 30
member.age.lt(30) // age < 30
member.username.like("member%") //like 검색
member.username.contains("member") // like ‘%member%’ 검색
member.username.startsWith("member") //like ‘member%’ 검색
//...
```

**AND 조건을 파라미터로 처리**
```java
@Test
public void searchAndParam() {
 List<Member> result1 = queryFactory
 .selectFrom(member)
 .where(member.username.eq("member1"),
 member.age.eq(10))
 .fetch();
 assertThat(result1.size()).isEqualTo(1);
}
```
- `where()`에 파라미터로 검색조건을 추가하면 `AND` 조건이 추가됨
- 이 경우 `null`값은 무시 -> 메서드 추출을 활용해서 동적 쿼리를 깔끔하게 만들 수 있음 -> 뒤에서 설명

</details>

<details> <summary> 4. 결과 조회 </summary>

### 결과 조회
- `fetch()`: 리스트 조회, 데이터 없으면 빈 리스트 반환
- `fetchOne()`: 단 건 조회
    - 결과가 없으면: `null`
    - 결과가 둘 이상이면: `com.querydsl.core.NonUniqueResultException`
- `fetchFirst()`: `limit(1).fetchOne()`
- `fetchResults()`: 페이징 정보 포함, total count 쿼리 추가 실행
- `fetchCount()`: count 쿼리로 변경해서 count 수 조회
```java
//List
List<Member> fetch = queryFactory
 .selectFrom(member)
 .fetch();
//단 건
Member findMember1 = queryFactory
 .selectFrom(member)
 .fetchOne();
//처음 한 건 조회
Member findMember2 = queryFactory
 .selectFrom(member)
 .fetchFirst();
//페이징에서 사용
QueryResults<Member> results = queryFactory
 .selectFrom(member)
 .fetchResults();
//count 쿼리로 변경
long count = queryFactory
 .selectFrom(member)
 .fetchCount();
```



</details>

<details> <summary> 5. 정렬 </summary>

### 정렬
```java
/**
 * 회원 정렬 순서
 * 1. 회원 나이 내림차순(desc)
 * 2. 회원 이름 올림차순(asc)
 * 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
 */
@Test
public void sort() {
 em.persist(new Member(null, 100));
 em.persist(new Member("member5", 100));
 em.persist(new Member("member6", 100));
 List<Member> result = queryFactory
 .selectFrom(member)
 .where(member.age.eq(100))
 .orderBy(member.age.desc(), member.username.asc().nullsLast())
 .fetch();
 Member member5 = result.get(0);
 Member member6 = result.get(1);
 Member memberNull = result.get(2);
 assertThat(member5.getUsername()).isEqualTo("member5");
 assertThat(member6.getUsername()).isEqualTo("member6");
 assertThat(memberNull.getUsername()).isNull();
}
```
- `desc()`, `asc()`: 일반 정렬
- `nullsLast()`, `nullsFirst()`: null 데이터 순서 부여


</details>

<details> <summary> 6. 페이징 </summary>

### 페이징

**조회 건수 제한**
```java
@Test
public void paging1() {
 List<Member> result = queryFactory
 .selectFrom(member)
 .orderBy(member.username.desc())
 .offset(1) //0부터 시작(zero index)
 .limit(2) //최대 2건 조회
 .fetch();
 assertThat(result.size()).isEqualTo(2);
}
```

**전체 조회 수가 필요하면?**
```java
@Test
public void paging2() {
 QueryResults<Member> queryResults = queryFactory
 .selectFrom(member)
 .orderBy(member.username.desc())
 .offset(1)
 .limit(2)
 .fetchResults();
 assertThat(queryResults.getTotal()).isEqualTo(4);
 assertThat(queryResults.getLimit()).isEqualTo(2);
 assertThat(queryResults.getOffset()).isEqualTo(1);
 assertThat(queryResults.getResults().size()).isEqualTo(2);
}
```

> 주의: count 쿼리가 실행되니 성능상 주의!

> 참고: 실무에서 페이징 쿼리를 작성할 때, 데이터를 조회하는 쿼리는 여러 테이블을 조인해야 하지만,
> count 쿼리는 조인이 필요 없는 경우도 있다. 그런데 이렇게 자동화된 count쿼리는 원본 쿼리와 같이 모두
> 조인을 해버리기 때문에 성능이 안나올 수 있다. count쿼리에 조인이 필요없는 성능 최적화가 필요하다면,
> count 전용 쿼리를 별도로 작성해야 한다.

</details>

<details> <summary> 7. 집합 </summary>

### 집합

**집합 함수**
```java
/**
 * JPQL
 * select
 * COUNT(m), //회원수
 * SUM(m.age), //나이 합
 * AVG(m.age), //평균 나이
 * MAX(m.age), //최대 나이
 * MIN(m.age) //최소 나이
 * from Member m
 */
@Test
public void aggregation() throws Exception {
 List<Tuple> result = queryFactory
 .select(member.count(),
 member.age.sum(),
 member.age.avg(),
 member.age.max(),
 member.age.min())
 .from(member)
 .fetch();
 Tuple tuple = result.get(0);
 assertThat(tuple.get(member.count())).isEqualTo(4);
 assertThat(tuple.get(member.age.sum())).isEqualTo(100);
 assertThat(tuple.get(member.age.avg())).isEqualTo(25);
 assertThat(tuple.get(member.age.max())).isEqualTo(40);
 assertThat(tuple.get(member.age.min())).isEqualTo(10);
}
```
- JPQL이 제공하는 모든 집합 함수를 제공한다.
- tuple은 프로젝션과 결과반환에서 설명한다.

**GroupBy 사용**
```java
/**
 * 팀의 이름과 각 팀의 평균 연령을 구해라.
 */
@Test
public void group() throws Exception {
 List<Tuple> result = queryFactory
 .select(team.name, member.age.avg())
 .from(member)
 .join(member.team, team)
 .groupBy(team.name)
 .fetch();
 Tuple teamA = result.get(0);
 Tuple teamB = result.get(1);
 assertThat(teamA.get(team.name)).isEqualTo("teamA");
 assertThat(teamA.get(member.age.avg())).isEqualTo(15);
 assertThat(teamB.get(team.name)).isEqualTo("teamB");
 assertThat(teamB.get(member.age.avg())).isEqualTo(35);
}
```

- `groupBy`, 그룹화된 결과를 제한하려면 `having`

**groupBy(), having() 예시
```java
.groupBy(item.price)
.having(item.price.gt(1000))
```



</details>

<details> <summary> 8. 조인 - 기본 조인 </summary>

### 조인 - 기본 조인
**기본 조인**
- 조인의 기본 문법은 첫 번째 파라미터에 조인 대상을 지정하고, 두 번째 파라미터에 별칭(alias)으로 사용할 Q 타입을 지정하면 된다.
```java
join(조인 대상, 별칭으로 사용할 Q타입)
```

```java
/**
 * 팀 A에 소속된 모든 회원
 */
@Test
public void join() throws Exception {
 QMember member = QMember.member;
 QTeam team = QTeam.team;
 List<Member> result = queryFactory
 .selectFrom(member)
 .join(member.team, team)
 .where(team.name.eq("teamA"))
 .fetch();
 assertThat(result)
 .extracting("username")
 .containsExactly("member1", "member2");
}
```

- `join()`, `innerJoin()`: 내부 조인(inner join)
- `leftJoin()`: left 외부 조인(left outer join)
- `rightJoin()`: right 외부 조인(right outer join)
- JPQL의 `on`과 성능 최적화를 위한 `fetch`조인 제공 -> 다음 on 절에서 설명

**세타 조인**
- 연관관계가 없는 필드로 조인
```java
/**
 * 세타 조인(연관관계가 없는 필드로 조인)
 * 회원의 이름이 팀 이름과 같은 회원 조회
 */
@Test
public void theta_join() throws Exception {
 em.persist(new Member("teamA"));
 em.persist(new Member("teamB"));
 List<Member> result = queryFactory
 .select(member)
 .from(member, team)
 .where(member.username.eq(team.name))
 .fetch();
 assertThat(result)
 .extracting("username")
 .containsExactly("teamA", "teamB");
}
```
- from 절에 여러 엔티티를 선택해서 세타 조인
- 외부 조인 불가능 -> 다음에 설명할 조인 on을 사용하면 외부 조인 가능

</details>

<details> <summary> 9. 조인 - on절 </summary>

### 조인 - on절
- ON절을 활용한 조인(JPA 2.1부터 지원)
    1. 조인 대상 필터링
    2. 연관관계 없는 엔티티 외부 조인

**1. 조인 대상 필터링**

- 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
```java
/**
 * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
 * JPQL: SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'teamA'
 * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and
t.name='teamA'
 */
@Test
public void join_on_filtering() throws Exception {
 List<Tuple> result = queryFactory
 .select(member, team)
 .from(member)
 .leftJoin(member.team, team).on(team.name.eq("teamA"))
 .fetch();
 for (Tuple tuple : result) {
 System.out.println("tuple = " + tuple);
 }
}
```

**결과**
```java
t=[Member(id=3, username=member1, age=10), Team(id=1, name=teamA)]
t=[Member(id=4, username=member2, age=20), Team(id=1, name=teamA)]
t=[Member(id=5, username=member3, age=30), null]
t=[Member(id=6, username=member4, age=40), null]
```

> 참고: on 절을 활용해 조인 대상을 필터링 할 때, 외부 조인이 아니라 내부조인(inner join)을 사용하면,
> where 절에서 필터링 하는 것과 기능이 동일하다. 따라서 on 절을 활용한 조인 대상 필터링을 사용 할 때,
> 내부조인 이면 익숙한 where 절로 해결하고, 정말 외부조인이 필요한 경우에만 이 기능을 사용하자.

**2. 연관관계 없는 엔티티 외부 조인**

- 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
```java
/**
 * 2. 연관관계 없는 엔티티 외부 조인
 * 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
 * JPQL: SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name
 * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.username = t.name
 */
@Test
public void join_on_no_relation() throws Exception {
 em.persist(new Member("teamA"));
 em.persist(new Member("teamB"));
 List<Tuple> result = queryFactory
 .select(member, team)
 .from(member)
 .leftJoin(team).on(member.username.eq(team.name))
 .fetch();
 for (Tuple tuple : result) {
 System.out.println("t=" + tuple);
 }
}
```

- 하이버네이트 5.1부터 `on`을 사용해서 서로 관계가 없는 필드로 외부 조인하는 기능이 추가되었다. 물론 내부조인도 가능하다.
- 주의! 문법을 잘 봐야 한다. **leftJoin()** 부분에 일반 조인과 다르게 엔티티 하나만 들어간다.
    - 일반조인: `leftJoin(member.team, team)`
    - on조인: `from(member).leftJoin(team).on(xxx)`

**결과**
```java
t=[Member(id=3, username=member1, age=10), null]
t=[Member(id=4, username=member2, age=20), null]
t=[Member(id=5, username=member3, age=30), null]
t=[Member(id=6, username=member4, age=40), null]
t=[Member(id=7, username=teamA, age=0), Team(id=1, name=teamA)]
t=[Member(id=8, username=teamB, age=0), Team(id=2, name=teamB)]
```

</details>

<details> <summary> 10. 조인 - 페치 조인 </summary>

### 조인 - 페치 조인
- 페치 조인은 SQL에서 제공하는 기능은 아니다.
- SQL조인을 활용해서 연관된 엔티티를 SQL 한번에 조회하는 기능이다.
- 주로 성능 최적화에 사용하는 방법이다.

**페치 조인 미적용**
- 지연로딩으로 Member, Team SQL 쿼리 각각 실행
```java
@PersistenceUnit
EntityManagerFactory emf;
@Test
public void fetchJoinNo() throws Exception {
 em.flush();
 em.clear();
 Member findMember = queryFactory
 .selectFrom(member)
 .where(member.username.eq("member1"))
 .fetchOne();
 boolean loaded =
emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
 assertThat(loaded).as("페치 조인 미적용").isFalse();
}
```

**페치 조인 적용**
- 즉시로딩으로 Member, Team SQL 쿼리 조인으로 한번에 조회
```java
@Test
public void fetchJoinUse() throws Exception {
 em.flush();
 em.clear();
 Member findMember = queryFactory
 .selectFrom(member)
 .join(member.team, team).fetchJoin()
 .where(member.username.eq("member1"))
 .fetchOne();
 boolean loaded =
emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
 assertThat(loaded).as("페치 조인 적용").isTrue();
}
```

**사용방법**
- `join(), leftJoin()` 등 조인 기능 뒤에 `fetchJoin()`이라고 추가하면 된다.

> 참고: 페치 조인에 대한 자세한 내용은 JPA 기본편이나, 활용 2편을 참고하자.


</details>

<details> <summary> 11. 서브 쿼리 </summary>

</details>

<details> <summary> 12. Case 문 </summary>

</details>

<details> <summary> 13. 상수, 문자 더하기 </summary>

</details>