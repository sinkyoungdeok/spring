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

### 공통 인터페이스 기능
- 순수 JPA 기반 리포지토리 만들기
- 스프링 데이터 JPA 공통 인터페이스 소개
- 스프링 데이터 JPA 공통 인터페이스 활용

### 순수 JPA 기반 리포지토리 만들기
- 순수한 JPA 기반 리포지토리를 만들자
- 기본 CRUD
    - 저장
    - 변경 -> 변경감지 사용
    - 삭제
    - 전체 조회
    - 단건 조회
    - 카운트

> 참고: JPA에서 수정은 변경감지 기능을 사용하면 된다.
> 트랜젹선 안에서 엔티티를 조회한 다음에 데이터를 변경하면, 트랜잭션 종료 시점에 변경
> 감지 기능이 작동해서 변경된 엔티티를 감지하고 UPDATE SQL을 실행한다.


</details>



<details> <summary> 2. 공통 인터페이스 설정 </summary>

**JavaConfig 설정-스프링 부트 사용시 생략 가능**
```java
@Configuration
@EnableJpaRepositories(basePackages = "jpabook.jpashop.repository")
public class AppConfig {}
```
- 스프링 부트 사용시 `@SpringBootApplication` 위치를 지정(해당 패키지와 하위 패키지 인식)
- 만약 위치가 달라지면 `@EnableJpaRepositories`필요

**스프링 데이터 JPA가 구현 클래스 대신 생성**
![image](https://user-images.githubusercontent.com/28394879/134108972-496004f9-cbbf-4a85-b897-95470568d77f.png)
- `org.springframework.data.repository.Repository` 를 구현한 클래스는 스캔 대상
    - MemberRepository 인터페이스가 똥작한 이유
    - 실제 출력해보기(Proxy)
    - memberRepository.getClass() -> class.com.sun.proxy.$ProxyXXX
- `@Repository` 애노테이션 생략 가능
    - 컴포넌트 스캔을 스프링 데이터 JPA가 자동으로 처리
    - JPA 예외를 스프링 예외로 변환하는 과정도 자동으로 처리


</details>



<details> <summary> 3. 공통 인터페이스 적용 </summary>

- 순수 JPA로 구현한 `MemberJpaRepository` 대신에 스프링 데이터 JPA가 제공하는 공통 인터페이스 사용


</details>



<details> <summary> 4. 공통 인터페이스 분석 </summary>

- JpaRepository 인터페이스: 공통 CRUD 제공
- 제네릭은 <엔티티 타입, 식별자 타입> 설정

**공통 인터페이스 구성**
![image](https://user-images.githubusercontent.com/28394879/134111657-c8beaeeb-58c6-43e3-9665-f033ad03d47c.png)

**주의**
- `T findOne(ID)` -> `Optional<T> findById(ID)` 변경

**제네릭 타입**
- `T`: 엔티티
- `ID`: 엔티티의 식별자 타입
- `S`: 엔티티와 그 자식 타입

**주요 메서드**
- `save(S)`: 새로운 엔티티는 저장하고 이미 있는 엔티티는 병합한다.
- `delete(T)`: 엔티티 하나를 삭제한다. 내부에서 `EntityManager.remove()` 호출
- `findById(ID)`: 엔티티 하나를 조회한다. 내부에서 `EntityManager.find()` 호출
- `getOne(ID)`: 엔티티를 프록시로 조회한다. 내부에서 `EntityManager.getReference()`호출
- `findAll(_)`: 모든 엔티티를 조회한다. 정렬(`Sort`)이나 페이징(`Pageable`)조건을 파라미터로 제공할 수 있다.

> 참고: `JpaRepository`는 대부분의 공통 메서드르 제공한다.

</details>




# [4. 쿼리 메소드 기능](./4.query-method-function)

<details> <summary> 1. 메소드 이름으로 쿼리 생성 </summary>

- 메소드 이름으로 쿼리 생성
- NamedQuery
- @Query - 리파지토리 메소드에 쿼리 정의
- 파라미터 바인딩
- 반환 타입
- 페이징과 정렬
- 벌크성 수정 쿼리
- @EntityGraph

**쿼리 메소드 기능 3가지**
- 메소드 이름으로 쿼리 생성
- 메소드 이름으로 JPA NamedQuery 호출
- `@Query` 어노테이션을 사용해서 리파지토리 인터페이스에 쿼리 직접 정의

### 메소드 이름으로 쿼리 생성
- 스프링 데이터 JPA는 메소드 이름을 분석해서 JPQL을 생성하고 실행

**쿼리 메소드 필터 조건**
- 스프링 데이터 JPA 공식 문서 참고: (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)

**스프링 데이터 JPA가 제공하는 쿼리 메소드 기능**
- 조회: find...By, read...By, query...By, get...By
    - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
    - ex) findHelloBy 처럼 ...에 식별하기 위한 내용(설명)이 들어가도 된다.
- COUNT: count...By 반환타입 `long`
- EXISTS: exists...By 반환타입 `boolean`
- 삭제: delete...By, remove...By 반환타입 `long`
- DISTINCT: findDistinct, findMemberDistinctBy
- LIMIT: findFirst3, findFirst, findTop, findTop3
    - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.limit-query-result

> 참고: 이 기능은 엔티티의 필드명이 변경되면 인터페이스에 정의한 메서드 이름도 꼭 함께 변경해야 한다.
> 그렇지 않으면 애플리케이션을 시작하는 시점에 오류가 발생한다.
> 이렇게 애플리케이션 로딩 시점에 오류를 인지할 수 있는 것이 스프링 데이터 JPA의 매우 큰 장점이다.


</details>

<details> <summary> 2. JPA NamedQuery </summary>

### JPA NamedQuery
- JPA의 NamedQuery를 호출할 수 있음

**`@NamedQuery` 어노테이션으로 Named 쿼리 정의**
```java
@Entity
@NamedQuery(
 name="Member.findByUsername",
 query="select m from Member m where m.username = :username")
public class Member {
 ...
}
```

**JPA를 직접 사용해서 Named 쿼리 호출**
```java
public class MemberRepository {
 public List<Member> findByUsername(String username) {
 ...
 List<Member> resultList =
 em.createNamedQuery("Member.findByUsername", Member.class)
 .setParameter("username", username)
 .getResultList();
 }
}
```

**스프링 데이터 JPA로 NamedQuery 사용**
```java
@Query(name = "Member.findByUsername")
List<Member> findByUsername(@Param("username") String username);
```
- `@Query`를 생략하고 메서드 이름만으로 Named 쿼리를 호출할 수 있다.

**스프링 데이터 JPA로 Named 쿼리 호출**
```java
public interface MemberRepository
 extends JpaRepository<Member, Long> { //** 여기 선언한 Member 도메인 클래스
 List<Member> findByUsername(@Param("username") String username);
}
```
- 스프링 데이터 JPA는 선언한 "도메인 클래스 + .(점) + 메서드 이름"으로 Named쿼리를 찾아서 실행
- 만약 실행할 Named 쿼리가 없으면 메서드 이름으로 쿼리 생성 전략을 사용한다.
- 필요하면 전략을 변경할 수 있지만 권장하지 않는다.
    - 참고: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-lookup-strategies

> 참고: 스프링 데이터 JPA를 사용하면 실무에서 Named Query를 직접 등록해서 사용하는 일은 드물다.
> 대신 `@Query`를 사용해서 리파지토리 메소드에 쿼리를 직접 정의한다.



</details>

<details> <summary> 3. @Query, 리포지토리 메소드에 쿼리 정의하기 </summary>

### @Query, 리포지토리 메소드에 쿼리 정의하기

**메서드에 JPQL 쿼리 작성**
```java
public interface MemberRepository extends JpaRepository<Member, Long> {
@Query("select m from Member m where m.username= :username and m.age = :age")
List<Member> findUser(@Param("username") String username, @Param("age") int
age);
}
```
- `@org.springframework.data.jpa.repository.Query` 어노테이션을 사용
- 실행할 메서드에 정적 쿼리를 직접 작성하므로 이름 없는 Named 쿼리라 할 수 있음
- JPA Named 쿼리처럼 애플리케이션 실행 시점에 문법 오류를 발견할 수 있음(매우 큰 장점!)

> 참고: 실무에서는 메소드 이름으로 쿼리 생성 기능은 파라미터가 증가하면서 메소드 이름이 매우
> 지저분해진다. 따라서 `@Query`기능을 자주 사용하게 된다.

</details>

<details> <summary> 4. @Query, 값, DTO 조회하기 </summary>

### @Query, 값, DTO 조회하기
**단순히 값 하나를 조회**
```java
@Query("select m.username from Member m")
List<String> findUsernameList();
```
- JPA 값 타입 (`@Embedded`)도 이 방식으로 조회할 수 있다.

**DTO로 직접 조회**
```java
@Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
 "from Member m join m.team t")
List<MemberDto> findMemberDto();
```
- 주의! DTO로 직접 조회 하려면 JPA의 `new` 명령어를 사용해야 한다. 그리고 다음과 같이 생성자가 맞는 DTO가 필요하다.(JPA와 사용방식이 동일하다.)

```java
package study.datajpa.repository;
import lombok.Data;
@Data
public class MemberDto {
 private Long id;
 private String username;
 private String teamName;
 public MemberDto(Long id, String username, String teamName) {
 this.id = id;
 this.username = username;
 this.teamName = teamName;
 }
}
```


</details>

<details> <summary> 5. 파라미터 바인딩 </summary>

### 파라미터 바인딩
- 위치 기반
- 이름 기반

```java
select m from Member m where m.username = ?0 //위치 기반
select m from Member m where m.username = :name //이름 기반
```


```java

import org.springframework.data.repository.query.Param

public interface MemberRepository extends JpaRepository<Member, Long> {

 @Query("select m from Member m where m.username = :name")
 Member findMembers(@Param("name") String username);
}
```
> 참고: 코드 가독성과 유지보수를 위해 이름 기반 파라미터 바인딩을 사용하자
> (위치기반은 순서 실수가 바꾸면...)


**컬렉션 파라미터 바인딩**
- `Collection`타입으로 in절 지원
```java
@Query("select m from Member m where m.username in :names")
List<Member> findByNames(@Param("names") List<String> names);
```

</details>

<details> <summary> 6. 반환 타입 </summary>

### 반환 타입
- 스프링 데이터 JPA는 유연한 반환 타입 지원
```java
List<Member> findByUsername(String name); //컬렉션
Member findByUsername(String name); //단건
Optional<Member> findByUsername(String name); //단건 Optional
```
- 스프링 데이터 JPA 공식 문서: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-return-types

**조회 결과가 많거나 없으면?**
- 컬렉션(List)
    - 결과 없음: 빈 컬렉션 반환
- 단건 조회(Optional, Member)
    - 결과 없음: `null`반환
    - 결과가 2건 이상: `javax.persistence.NonUniqueResultException` 예외 발생

> 참고: 단건으로 지정한 메서드를 호출하면 스프링 데이터 JPA는 내부에서 JPAL의
> `Query.getSingleResult()`메서드를 호출한다. 이 메서드를 호출했을 때 조회 결과가 없으면
> `javax.persistence.NoResultException` 예외가 발생하는데 개발자 입장에서 다루기 상당히 불편하다.
> 스프링 데이터 JPA는 단건을 조회할 때 이 에외가 발생하면 예외를 무시하고 대신에 `null`을 반환한다.

</details>

<details> <summary> 7. 순수 JPA 페이징과 정렬 </summary>

### 순수 JPA 페이징과 정렬

- JPA에서 페이징을 어떻게 할 것인가?
- 다음 조건으로 페이징과 정렬을 사용하는 예제 코드를 보자.
    - 검색 조건: 나이가 10살
    - 정렬 조건: 이름으로 내림차순
    - 페이징 조건: 첫 번째 페이지, 페이지당 보여줄 데이터는 3건

**JPA 페이징 리포지토리 코드**
```java
public List<Member> findByPage(int age, int offset, int limit) {
 return em.createQuery("select m from Member m where m.age = :age order by
m.username desc")
 .setParameter("age", age)
 .setFirstResult(offset)
 .setMaxResults(limit)
 .getResultList();
}
public long totalCount(int age) {
 return em.createQuery("select count(m) from Member m where m.age = :age",
Long.class)
 .setParameter("age", age)
 .getSingleResult();
}
```

**JPA 페이징 테스트 코드**
```java
@Test
public void paging() throws Exception {
 //given
 memberJpaRepository.save(new Member("member1", 10));
 memberJpaRepository.save(new Member("member2", 10));
 memberJpaRepository.save(new Member("member3", 10));
 memberJpaRepository.save(new Member("member4", 10));
 memberJpaRepository.save(new Member("member5", 10));
 int age = 10;
 int offset = 0;
 int limit = 3;
 //when
 List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
 long totalCount = memberJpaRepository.totalCount(age);
 //페이지 계산 공식 적용...
 // totalPage = totalCount / size ...
 // 마지막 페이지 ...
 // 최초 페이지 ..
 //then
 assertThat(members.size()).isEqualTo(3);
 assertThat(totalCount).isEqualTo(5);
}
```

</details>

<details> <summary> 8. 스프링 데이터 JPA 페이징과 정렬 </summary>

### 스프링 데이터 JPA 페이징과 정렬
**페이징과 정렬 파라미터**
- `org.springframework.data.domain.Sort`: 정렬 기능
- `org.springframework.data.domain.Pageable`: 페이징 기능(내부에 `Sort` 포함)

**특별한 반환 타입**
- `org.springframework.data.domain.Page`: 추가 count쿼리 결과를 포함하는 페이징
- `org.springframework.data.domain.Slice`: 추가 count 쿼리 없이 다음 페이지만 확인 가능(내부적으로 limit + 1조회)
- `List`(자바 컬렉션): 추가 count 쿼리 없이 결과만 반환

**페이징과 정렬 사용 예제**
```java
Page<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용
Slice<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
List<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
List<Member> findByUsername(String name, Sort sort);
```

- 다음 조건으로 페이징과 정렬을 사용하는 예제 코드를 보자.
    - 검색 조건: 나이가 10살
    - 정렬 조건: 이름으로 내림차순
    - 페이징 조건: 첫 번째 페이지, 페이지당 보여줄 데이터는 3건

**Page 사용 예제 정의 코드**
```java
public interface MemberRepository extends Repository<Member, Long> {
 Page<Member> findByAge(int age, Pageable pageable);
}
```

**Page 사용 예제 실행 코드**
```java
//페이징 조건과 정렬 조건 설정
@Test
public void page() throws Exception {
 //given
 memberRepository.save(new Member("member1", 10));
 memberRepository.save(new Member("member2", 10));
 memberRepository.save(new Member("member3", 10));
 memberRepository.save(new Member("member4", 10));
 memberRepository.save(new Member("member5", 10));
 //when
 PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC,
"username"));
 Page<Member> page = memberRepository.findByAge(10, pageRequest);
 //then
 List<Member> content = page.getContent(); //조회된 데이터
 assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
 assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
 assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
 assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
 assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
 assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?
}
```

- 두 번째 파라미터로 받은 `Pageable` 은 인터페이스다. 따라서 실제 사용할 때는 해당 인터페이스를 구현한
`org.springframework.data.domain.PageRequest` 객체를 사용한다.
- `PageRequest` 생성자의 첫 번째 파라미터에는 현재 페이지를, 두 번째 파라미터에는 조회할 데이터 수를
입력한다. 여기에 추가로 정렬 정보도 파라미터로 사용할 수 있다. 참고로 페이지는 0부터 시작한다.

> 주의: Page는 1부터 시작이 아니라 0부터 시작이다.

**Page 인터페이스**
```java
public interface Page<T> extends Slice<T> {
 int getTotalPages(); //전체 페이지 수
 long getTotalElements(); //전체 데이터 수
 <U> Page<U> map(Function<? super T, ? extends U> converter); //변환기
}
```

**Slice 인터페이스**
```java
public interface Slice<T> extends Streamable<T> {
 int getNumber(); //현재 페이지
int getSize(); //페이지 크기
int getNumberOfElements(); //현재 페이지에 나올 데이터 수
List<T> getContent(); //조회된 데이터
boolean hasContent(); //조회된 데이터 존재 여부
Sort getSort(); //정렬 정보
boolean isFirst(); //현재 페이지가 첫 페이지 인지 여부
boolean isLast(); //현재 페이지가 마지막 페이지 인지 여부
boolean hasNext(); //다음 페이지 여부
boolean hasPrevious(); //이전 페이지 여부
Pageable getPageable(); //페이지 요청 정보
Pageable nextPageable(); //다음 페이지 객체
Pageable previousPageable();//이전 페이지 객체
<U> Slice<U> map(Function<? super T, ? extends U> converter); //변환기
}
```

**참고: count쿼리를 다음과 같이 분리할 수 있음**
```java
@Query(value = "select m from Member m”,
 countQuery = "select count(m.username) from Member m”)
Page<Member> findMemberAllCountBy(Pageable pageable);
```

**Top, First 사용 참고**
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.limit-query-result
- `List<Member> findTop3By();`

**페이지를 유지하면서 엔티티를 DTO로 변환하기**
```java
Page<Member> page = memberRepository.findByAge(10, pageRequest);
Page<MemberDto> dtoPage = page.map(m -> new MemberDto());
```

**실습**
- Page
- Slice (count X) 추가로 limit + 1을 조회한다. 그래서 다음 페이지 여부 확인(최근 모바일 리스트
생각해보면 됨)
- List (count X)
- 카운트 쿼리 분리(이건 복잡한 sql에서 사용, 데이터는 left join, 카운트는 left join 안해도 됨)
    - 실무에서 매우 중요!!!

> 참고: 전체 count쿼리를 매우 무겁다.

</details>

<details> <summary> 9. 벌크성 수정 쿼리 </summary>

### 벌크성 수정 쿼리

**JPA를 사용한 벌크성 수정 쿼리**
```java
public int bulkAgePlus(int age) {
 int resultCount = em.createQuery(
 "update Member m set m.age = m.age + 1" +
 "where m.age >= :age")
 .setParameter("age", age)
 .executeUpdate();
 return resultCount;
}
```

**JPA를 사용한 벌크성 수정 쿼리 테스트**
```java
@Test
public void bulkUpdate() throws Exception {
 //given
 memberJpaRepository.save(new Member("member1", 10));
 memberJpaRepository.save(new Member("member2", 19));
 memberJpaRepository.save(new Member("member3", 20));
 memberJpaRepository.save(new Member("member4", 21));
 memberJpaRepository.save(new Member("member5", 40));
 //when
 int resultCount = memberJpaRepository.bulkAgePlus(20);
 //then
 assertThat(resultCount).isEqualTo(3);
}
```

**스프링 데이터 JPA를 사용한 벌크성 수정 쿼리**
```java
@Modifying
@Query("update Member m set m.age = m.age + 1 where m.age >= :age")
int bulkAgePlus(@Param("age") int age);
```

**스프링 데이터 JPA를 사용한 벌크성 수정 쿼리 테스트**
```java
@Test
public void bulkUpdate() throws Exception {
 //given
 memberRepository.save(new Member("member1", 10));
 memberRepository.save(new Member("member2", 19));
 memberRepository.save(new Member("member3", 20));
 memberRepository.save(new Member("member4", 21));
 memberRepository.save(new Member("member5", 40));
 //when
 int resultCount = memberRepository.bulkAgePlus(20);
 //then
 assertThat(resultCount).isEqualTo(3);
}
```

- 벌크성 수정, 삭제 쿼리는 `@Modifying` 어노테이션을 사용
    - 사용하지 않으면 다음 예외 발생
    - `org.hibernate.hql.internal.QueryExecutionRequestException: Not supported for DML operations`
- 벌크성 쿼리를 실행하고 나서 영속성 컨텍스트 초기화: `@Modifying(clearAutomatically = true)` (이 옵션은 기본값은 `false`)
    - 이 옵션 없이 회원을 `findById`로 다시 조회하면 영속성 컨텍스트에 과거 값이 남아서 문제가 될 수 있다. 만약 다시 조회해야 하면
    꼭 영속성 컨텍스트를 초기화 하자.

> 참고: 벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 떄문에, 영속성 컨텍스트에 있는 엔티티의 상태와 DB에 엔티티 상태가 달라질 수 있다.
> 권장하는 방안
> 1. 영속성 컨텍스트에 엔티티가 없는 상태에서 벌크 연산을 먼저 실행한다.
> 2. 부득이하게 영속성 컨텍스트에 엔티티가 있으면 벌크 연산 직후 영속성 컨텍스트를 초기화 한다.

</details>

<details> <summary> 10. @EntityGraph </summary>

### @EntityGraph
- 연관된 엔티티들을 SQL 한번에 조회하는 방법
- member -> team은 지연로딩 관계이다. 따라서 다음과 같이 team의 데이터를 조회할 때 마다 쿼리가 실행된다. (N+1 문제 발생)
```java
@Test
public void findMemberLazy() throws Exception {
 //given
 //member1 -> teamA
 //member2 -> teamB
 Team teamA = new Team("teamA");
 Team teamB = new Team("teamB");
 teamRepository.save(teamA);
 teamRepository.save(teamB);
 memberRepository.save(new Member("member1", 10, teamA));
 memberRepository.save(new Member("member2", 20, teamB));
 em.flush();
 em.clear();
 //when
 List<Member> members = memberRepository.findAll();
 //then
 for (Member member : members) {
 member.getTeam().getName();
 }
}
```
- 참고: 다음과 같이 지연 로딩 여부를 확인할 수 있다.

```java
//Hibernate 기능으로 확인
Hibernate.isInitialized(member.getTeam())
//JPA 표준 방법으로 확인
PersistenceUnitUtil util =
em.getEntityManagerFactory().getPersistenceUnitUtil();
util.isLoaded(member.getTeam());
```

- 연관된 엔티티를 한번에 조회하려면 페치 조인이 필요하다.
**JPQL 페치 조인**
```java
@Query("select m from Member m left join fetch m.team")
List<Member> findMemberFetchJoin();
```

- 스프링 데이터 JPA는 JPA가 제공하는 엔티티 그래프 기능을 편리하게 사용하게 도와준다. 이 기능을 사용하면
JPQL 없이 페치 조인을 사용할 수 있다. (JPQL + 엔티티 그래프도 가능)

**EntityGraph**
```java
//공통 메서드 오버라이드
@Override
@EntityGraph(attributePaths = {"team"})
List<Member> findAll();
//JPQL + 엔티티 그래프
@EntityGraph(attributePaths = {"team"})
@Query("select m from Member m")
List<Member> findMemberEntityGraph();
//메서드 이름으로 쿼리에서 특히 편리하다.
@EntityGraph(attributePaths = {"team"})
List<Member> findByUsername(String username)
```

**EntityGraph정리**
- 사실상 페치 조인(FETCH JOIN)의 간편 버전
- LEFT OUTER JOIN 사용

**NamedEntityGraph 사용 방법**
```java
@NamedEntityGraph(name = "Member.all", attributeNodes =
@NamedAttributeNode("team"))
@Entity
public class Member {}
```

```java
@EntityGraph("Member.all")
@Query("select m from Member m")
List<Member> findMemberEntityGraph();
```

</details>

<details> <summary> 11. JPA Hint & Lock </summary>

## JPA Hint & Lock

### JPA Hint
- JPA 쿼리 힌트(SQL 힌트가 아니라 JPA 구현체에게 제공하는 힌트)
**쿼리 힌트 사용**
```java
@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value =
"true"))
Member findReadOnlyByUsername(String username);
```

**쿼리 힌트 사용 확인**
```java
@Test
public void queryHint() throws Exception {
 //given
 memberRepository.save(new Member("member1", 10));
 em.flush();
 em.clear();
 //when
 Member member = memberRepository.findReadOnlyByUsername("member1");
 member.setUsername("member2");
 em.flush(); //Update Query 실행X
}
```

**쿼리 힌트 Page 추가 예제**
```java
@QueryHints(value = { @QueryHint(name = "org.hibernate.readOnly",
 value = "true")},
 forCounting = true)
Page<Member> findByUsername(String name, Pagable pageable);
```
- `org.springframework.data.jpa.repository.QueryHints` 어노테이션을 사용
- `forCounting`: 반환 타입으로 `Page` 인터페이스를 적용하면 추가로 호출하는 페이징을 위한 count 쿼리도 쿼리 힌트 적용(기본값 `true`)

### Lock
```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
List<Member> findByUsername(String name);
```
- `org.springframework.data.jpa.repository.Lock` 어노테이션을 사용
- JPA가 제공하는 락은 JPA 책 16.1 트랜잭션과 락 절을 참고


</details>



# [5. 확장 기능](./5.extension-function)

<details> <summary> 1. 사용자 정의 리포지토리 구현 </summary>

### 사용자 정의 리포지토리 구현
- 스프링 데이터 JPA 리포지토리는 인터페이스만 정의하고 구현체는 스프링이 자동 생성
- 스프링 데이터 JPA가 제공하는 인터페이스를 직접 구현하면 구현해야 하는 기능이 너무 많음
- 다양한 이유로 인터페이스의 메서드를 직접 구현하고 싶다면?
    - JPA 직접 사용 (`EntityManager`)
    - 스프링 JDBC Template 사용
    - MyBatis 사용
    - 데이터 베이스 커넥션 직접 사용 등등...
    - Querydsl 사용

**사용자 정의 인터페이스**
```java
public interface MemberRepositoryCustom {
 List<Member> findMemberCustom();
}
```

**사용자 정의 인터페이스 구현 클래스**
```java
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
 private final EntityManager em;
 @Override
 public List<Member> findMemberCustom() {
 return em.createQuery("select m from Member m")
 .getResultList();
 }
}
```

**사용자 정의 인터페이스 상속**
```java
public interface MemberRepository
 extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}
```

**사용자 정의 메서드 호출 코드**
```java
List<Member> result = memberRepository.findMemberCustom();
```

**사용자 정의 구현 클래스**
- 규칙: 리포지토리 인터페이스 이름 + `Impl`
- 스프링 데이터 JPA가 인식해서 스프링 빈으로 등록

### Impl 대신 다른 이름으로 변경하고 싶으면? (앵간해서는 변경 하지는 말자)

**XML 설정**

```xml
<repositories base-package="study.datajpa.repository"
 repository-impl-postfix="Impl" />
```

**JavaConfig 설정**
```
@EnableJpaRepositories(basePackages = "study.datajpa.repository",
 repositoryImplementationPostfix = "Impl")
```

> 참고: 실무에서는 주로 QueryDSL이나 SpringJdbcTemplate을 함께 사용할 때 사용자 정의
> 리포지토리 기능 자주 사용

> 참고: 항상 사용자 정의 리포지토리가 필요한 것은 아니다. 그냥 임의의 리포지토리를 만들어도 된다.
> 예를들어 MemberQueryRepository를 인터페이스가 아닌 클래스로 만들고 스프링 빈으로 등록해서
> 그냥 직접 사용해도 된다. 물론 이 경우 스프링 데이터 JPA와는 아무런 관계 없이 별도로 동작한다.

### 사용자 정의 리포지토리 구현 최신 방식
- 스프링 데이터 2.x 부터는 사용자 정의 구현 클래스에 리포지토리 인터페이스 이름 + `Impl`을 적용하는 대신에
사용자 정의 인터페이스 명 + `Impl`방식도 지원한다.
- 예를 들어서 위 예제의 `MemberRepositoryImpl` 대신에 `MemberRepositoryCustomImpl` 같이 구현해도 된다.

**최신 사용자 정의 인터페이스 구현 클래스 예제**
```java
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
 private final EntityManager em;
 @Override
 public List<Member> findMemberCustom() {
 return em.createQuery("select m from Member m")
 .getResultList();
 }
}
```
- 기존 방식보다 이 방식이 사용자 정의 인터페이스 이름과 구현 클래스 이름이 비슷하므로 더 직관적이다.
- 추자로 여러 인터페이스를 분리해서 구현하는 것도 가능하기 떄문에 새롭게 변경된 이 방식을 사용하는것을 더 권장한다.

</details>

<details> <summary> 2. Auditing </summary>

### Auditing
- 엔티티를 생성, 변경할 때 변경한 사람과 시간을 추적하고 싶으면?
    - 등록일
    - 수정일
    - 등록자
    - 수정자

### 순수 JPA 사용
- 우선 등록일, 수정일 적용
```java
package study.datajpa.entity;
@MappedSuperclass
@Getter
public class JpaBaseEntity {
 @Column(updatable = false)
 private LocalDateTime createdDate;
 private LocalDateTime updatedDate;
 @PrePersist
 public void prePersist() {
 LocalDateTime now = LocalDateTime.now();
 createdDate = now;
 updatedDate = now;
 }
 @PreUpdate
 public void preUpdate() {
 updatedDate = LocalDateTime.now();
 }
}
public class Member extends JpaBaseEntity {}
```

**확인 코드**
```java
@Test
public void JpaEventBaseEntity() throws Exception {
 //given
 Member member = new Member("member1");
 memberRepository.save(member); //@PrePersist
 Thread.sleep(100);
 member.setUsername("member2");
 em.flush(); //@PreUpdate
 em.clear();
 //when
 Member findMember = memberRepository.findById(member.getId()).get();
 //then
 System.out.println("findMember.createdDate = " +
findMember.getCreatedDate());
 System.out.println("findMember.updatedDate = " +
findMember.getUpdatedDate());
}
```

**JPA 주요 이벤트 어노테이션**
- @PrePersist, @PostPersist
- PreUpdate, @PostUpdate

### 스프링 데이터 JPA 사용
**설정**
- `@EnableJpaAuditing` -> 스프링 부트 설정 클래스에 적용해야함(main파일)
- `@EntityListeners(AuditingEntityListener.class)` -> 엔티티에 적용

**사용 어노테이션**
- `@CreateDate`
- `@LastModifiedDate`
- `@CreatedBy`
- `@LastModifiedBy`

**스프링 데이터 Auditing 적용 - 등록일,수정일**
```java
package study.datajpa.entity;
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {
 @CreatedDate
 @Column(updatable = false)
 private LocalDateTime createdDate;
 @LastModifiedDate
 private LocalDateTime lastModifiedDate;
}
```

**스프링 데이터 Auditing 적용 - 등록자, 수정자**
```java
package jpabook.jpashop.domain;
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
 @CreatedDate
 @Column(updatable = false)
 private LocalDateTime createdDate;
 @LastModifiedDate
 private LocalDateTime lastModifiedDate;
 @CreatedBy
 @Column(updatable = false)
 private String createdBy;
 @LastModifiedBy
 private String lastModifiedBy;
}
```

**등록자, 수정자를 처리해주는 `AuditorAware`스프링 빈 등록**
```java
@Bean
public AuditorAware<String> auditorProvider() {
 return () -> Optional.of(UUID.randomUUID().toString());
}
```
- 실무에서는 세션 정보나, 스프링 시큐리티 로그인 정보에서 ID를 받음

> 참고: 실무에서 대부분의 엔티티는 등록시간, 수정시간이 필요하지만, 등록자, 수정자는 없을 수도 있다.
> 그래서 다음과 같이 Base 타입을 분리하고, 원하는 타입을 선택해서 상속한다.
```java
public class BaseTimeEntity {
 @CreatedDate
 @Column(updatable = false)
 private LocalDateTime createdDate;
 @LastModifiedDate
 private LocalDateTime lastModifiedDate;
}
public class BaseEntity extends BaseTimeEntity {
 @CreatedBy
 @Column(updatable = false)
 private String createdBy;
 @LastModifiedBy
 private String lastModifiedBy;
}
```

> 참고: 저장시점에 등록일, 등록자는 물론이고, 수정일, 수정자도 같은 데이터가 저장된다. 데이터가 중복
> 저장되는 것 같지만, 이렇게 해두면 변경 컬럼만 확인해도 마지막에 업데이트한 유저를 확인할 수 있으므로
> 유지보수 관점에서 편리하다. 이렇게 하지 않으면 변경 컬럼이 `null`일때 등록 컬럼을 또 찾아야 한다.
> 참고로 저장시점에 저장데이터만 입력하고 싶으면 `@EnableJpaAuditing(modifyOnCreate = false)`
> 옵션을 사용하면 된다.

**전체 적용**
- `EntityListeners(AuditingEntityListener.class)`를 생략하고 스프링 데이터 JPA가 제공하는
이벤트를 엔티티 전체에 적용하려면 orm.xml에 다음과 같이 등록하면 된다.

`META-INF/orm.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence/
orm http://xmlns.jcp.org/xml/ns/persistence/orm_2_2.xsd"
 version="2.2">
 <persistence-unit-metadata>
 <persistence-unit-defaults>
 <entity-listeners>
 <entity-listener
class="org.springframework.data.jpa.domain.support.AuditingEntityListener"/>
 </entity-listeners>
 </persistence-unit-defaults>
 </persistence-unit-metadata>

</entity-mappings>
```


</details>

<details> <summary> 3. Web 확장 - 도메인 클래스 컨버터 </summary>

### Web 확장 - 도메인 클래스 컨버터
- HTTP 파라미터로 넘어온 엔티티의 아이디로 엔티티 객체를 찾아서 바인딩

**도메인 클래스 컨버터 사용 전**
```java
@RestController
@RequiredArgsConstructor
public class MemberController {
 private final MemberRepository memberRepository;
 @GetMapping("/members/{id}")
 public String findMember(@PathVariable("id") Long id) {
 Member member = memberRepository.findById(id).get();
 return member.getUsername();
 }
}
```

**도메인 클래스 컨버터 사용 후**
```java
@RestController
@RequiredArgsConstructor
public class MemberController {
 private final MemberRepository memberRepository;
 @GetMapping("/members/{id}")
 public String findMember(@PathVariable("id") Member member) {
 return member.getUsername();
 }
}
```
- HTTP 요청은 회원 `id`를 받지만 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환
- 도메인 클래스 컨버터도 리파지토리를 사용해서 엔티티를 찾음

> 주의: 도메인 클래스 컨버터로 엔티티를 파라미터로 받으면, 이 엔티티는 단순 조회용으로만
> 사용해야 한다. (트랜잭션이 없는 범위에엇 엔티티를 조회했으므로, 엔티티를 변경해도 DB에 반영되지 않는다.)

</details>

<details> <summary> 4. Web 확장 - 페이징과 정렬 </summary>

### Web 확장 - 페이징과 정렬

- 스프링 데이터가 제공하는 페이징과 정렬 기능을 스프링 MVC에서 편리하게 사용할 수 있다.

**페이징과 정렬 예제**
```java
@GetMapping("/members")
public Page<Member> list(Pageable pageable) {
 Page<Member> page = memberRepository.findAll(pageable);
 return page;
}
```
- 파라미터로 `Pageable`을 받을 수 있다.
- `Pageable`은 인터페이스, 실제는 `org.springframework.data.domain.PageRequest` 객체 생성

**요청 파라미터**
- ex) `/members?page=0&size=3&sort=id,desc&sort=username,desc`
- page: 현재 페이지, **0부터 시작한다.**
- size: 한 페이지에 노출할 데이터 건수
- sort: 정렬 조건을 정의한다. 예) 정렬 속성, 정렬 속성...(ASC|DESC), 정렬 방향을 변경하고 싶으면 `sort`파라미터 추가 (`asc`생략 가능)

**기본값**
- 글로벌 설정: 스프링 부트
```java
spring.data.web.pageable.default-page-size=20 /# 기본 페이지 사이즈/
spring.data.web.pageable.max-page-size=2000 /# 최대 페이지 사이즈/
```

**개발 설정**
- `@PageableDefault` 어노테이션을 사용
```java
@RequestMapping(value = "/members_page", method = RequestMethod.GET)
public String list(@PageableDefault(size = 12, sort = “username”,
 direction = Sort.Direction.DESC) Pageable pageable) {
 ...
}
```

**접두사**
- 페이징 정보가 둘 이상이면 접두사로 구분
- `@Qualifier`에 접두사명 추가 "{접두사명}_xxx"
- 예제: `/members?member_page=0&order_page=1`
```java
public String list(
 @Qualifier("member") Pageable memberPageable,
 @Qualifier("order") Pageable orderPageable, ...
```

### Page 내용을 DTO로 변환하기
- 엔티티를 API로 노출하면 다양한 문제가 발생한다. 그래서 엔티티를 꼭 DTO로 변환해서 반환해야 한다.
- Page는 `map()`을 지원해서 내부 데이터를 다른 것으로 변경할 수 있다.

**Member DTO**
```java
@Data
public class MemberDto {
 private Long id;
 private String username;
 public MemberDto(Member m) {
 this.id = m.getId();
 this.username = m.getUsername();
 }
}
```

**`Page.map()`사용**
```java
@GetMapping("/members")
public Page<MemberDto> list(Pageable pageable) {
 Page<Member> page = memberRepository.findAll(pageable);
 Page<MemberDto> pageDto = page.map(MemberDto::new);
 return pageDto;
}
```

**`Page.map()`코드 최적화
```java
@GetMapping("/members")
public Page<MemberDto> list(Pageable pageable) {
 return memberRepository.findAll(pageable).map(MemberDto::new);
}
```

### Page를 1부터 시작하기
- 스프링 데이터는 Page를 0부터 시작한다.
- 만약 1부터 시작하려면?

-  1. Pageable, Page를 파리미터와 응답 값으로 사용히지 않고, 직접 클래스를 만들어서 처리한다. 그리고
  직접 PageRequest(Pageable 구현체)를 생성해서 리포지토리에 넘긴다. 물론 응답값도 Page 대신에
  직접 만들어서 제공해야 한다.
-  2. `spring.data.web.pageable.one-indexed-parameters` 를 `true` 로 설정한다. 그런데 이 방법은
   web에서 `page` 파라미터를 `-1` 처리 할 뿐이다. 따라서 응답값인 Page 에 모두 `0` 페이지 인덱스를
   사용하는 한계가 있다.

**`one-indexed-parameters` Page 1요청 (`http://localhost:8080/members?page1`)
```json
{
 "content": [
 ...
 ],
 "pageable": {
 "offset": 0,
 "pageSize": 10,
 "pageNumber": 0 //0 인덱스
 },
 "number": 0, //0 인덱스
 "empty": false
}
```

</details>



# [6. 스프링 데이터 JPA 분석](./6.spring-data-jpa-analysis)

<details> <summary> 1. 스프링 데이터 JPA 구현체 분석 </summary>

</details>


<details> <summary> 2. 새로운 엔티티를 구별하는 방법 </summary>

</details>