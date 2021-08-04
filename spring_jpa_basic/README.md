# 목표1 - 객체와 테이블 설계 매핑
- 객체와 테이블을 제대로 설계하고 매핑하는 방법
- 기본 키와 외래 키 매핑
- 1:N, N:1, 1:1, N:M 매핑
- 실무 노하우 + 성능까지 고려
- 어떠한 복잡한 시스템도 JPA로 설계 가능 

# 목표2 - JPA 내부 동작 방식 이해
- JPA의 내부 동작 방식을 이해하지 못하고 상요
- JPA 내부 동작 방식을 그림과 코드로 자세히 설명
- JPA가 어떤 SQL을 만들어 내는지 이해
- JPA가 언제 SQL을 실행하는지 이해

# JPA 적용 사례
- 우아한형제들, 쿠팡, 카카오, 네이버 등등
- 조 단위의 거래 금액이 발생하는 다양한 서비스에서 사용, 검증
- 최신 스프링 예제는 JPA 기존 적용
- 자바 개발자에게 JPA는 기본 기술
- 토비의 스프링 이일민님도 JPA는 기본 적용


# 1. JPA 소개
## A. SQL 중심적인 개발의 문제점
- SQL에 의존적인 개발을 피하기 어렵다.
- 패러다임의 불일치
- 객체와 관계형 데이터베이스의 차이
    1. 상속
        - 어떠한 객체들이 상속관계에 있고 RDB에서도 비슷하게 구성을 한다고 했을때, 
          자바컬렉션으로하면 list.add 한번이면 끝나는데, 관계형 데이터베이스에서 뭔가 데이터를 넣기 위해서는
          mapping작업을 하는데 있어서 많은 시간과 노력과 고민이 필요하다.
    2. 연관관계
        - 객체는 참조를 사용하고, 테이블은 외래키를 사용하게 되면, 두개의 테이블에 하나의 FK로 연관 되어 있다고 하면
        객체는 한방향으로만 찾아갈수있고 반대방향으로는 되돌아 올 수 없고, 테이블은 양쪽 방향으로 찾을 수 있다.(PK,FK를 활용해서)
        - 이 차이점을 극복하기위해서는 객체를 테이블에 맞추어 모델링 작업이 필요하다.
        - 모델링 작업을 하고나면, 조회를 할떄 문제가 생긴다. 두 테이블(객체)중 FK를 가지고 있는 테이블을 조회 하려면,
        FK에 해당하는 테이블을 따로 조회 해서 연결 시켜주는 작업이 따로 필요하게 되버리고, 생산성이 떨어진다.
    3. 데이터 타입
    4. 데이터 식별 방법
- 계층형 아키텍쳐, 진정한 의미의 계층 분할이 어렵다.
- DB sql을 통해서 객체를 꺼내는것이랑, 자바 컬렉션에서 객체를 꺼내는 것이랑 차이가 나버린다.
    - sql로 꺼내면 매번 꺼낼 때마다 객체를 새로 만들어서 넣어주기 떄문에 두개의 객체를 꺼내서 비교(==)해보면 다르다.
    - 자바 컬렉션에서 꺼내면(list.get(memberId)) member1 == member2 가 된다.
- 객체를 자바 컬렉션에 저장 하듯이 DB에 저장할 수는 없을까 ? ==> 이것의 고민을 해결한 것이 JPA이다.
## B. JPA 소개
### JPA
- Java Persistence API
- 자바 진영의 ORM 기술 표준
### ORM
- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간(객체와 관계형데이터베이스 사이)에서 매핑
- 대중적인 언어에는 대부분 ORM 기술이 존재
### JPA는 애플리케이션과 JDBC 사이에서 동작
### JPA 동작 - 저장
- Entity 분석
- INSERT SQL 생성
- JDBC API 사용
- 패러다임 불일치 해결
### JPA 동작 - 조회
- SELECT SQL 생성
- JDBC API 사용
- ResultSet 매핑
- 패러다임 불일치 해결
### JPA는 표준 명세
- JPA는 인터페이스의 모음
- JPA 2.1 표준 명세를 구현한 3가지 구현체
- 하이버네이트, EclipseLink, DataNucleus
### JPA 버전
- JPA 1.0(JSR 220) 2006년: 초기버전. 복합 키와 연관관계 기능이 부족
- JPA 2.0(JSR 317) 2009년: 대부분의 ORM 기능을 포함, JPA Criteria 추가
- JPA 2.1(JSR 338) 2013년: 스토어드 프로시저 접근, 컨버터(Converter), 엔티티 그래프 기능이 추가
### JPA를 왜 사용해야 하는가?
- SQL 중심적인 개발에서 객체 중심으로 개발
- 생산성
- 유지보수
- 패러다임의 불일치 해결
- 성능
- 데이터 접근 추상화와 벤더 독립성
- 표준

### 생산성 - JPA와 CRUD
- 저장: jpa.persist(member)
- 조회: Member member = jpa.find(memberId)
- 수정: member.setName("변경할 이름")
- 삭제: jpa.remove(member)
### 유지보수
- 기존: 필드 변경시 모든 SQL 수정 
- JPA: 필드만 추가하면 됨, SQL은 JPA가 처리
### 패러다임의 불일치 해결
1. JPA와 상속
2. JPA와 연관관계
3. JPA와 객체 그래프 탐색
4. JPA와 비교하기
    - 동일한 트랜잭션에서 조회한 엔티티는 같음을 보장(==)

### JPA의 성능 최적화 기능
1. 1차 캐시와 동일성(identity) 보장
    1. 같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상
    2. DB Ioslation Level이 Read Commit이어도 애플리케이션에서 Repeatable Read 보장 
2. 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
    - INSERT
        1. 트랜잭션을 커밋할 때까지 INSERT SQL을 모음
        2. JDBC BATCH SQL 기능을 사용해서 한번에 SQL 전송
    - UPDATE
        1. UPDATE, DELETE로 인한 로우(ROW)락 시간 최소화
        2. 트랜잭션 커밋 시 UPDATE, DELETE SQL 실행하고, 바로 커밋
3. 지연 로딩(Lazy Loading)
    - 지연 로딩: 객체가 실제 사용될 때 로딩
    - 즉시 로딩: JOIN SQL로 한번에 연관된 객체까지 미리 조회
    

# 2. JPA 시작하기
## A. Hello JPA - 프로젝트 생성
## B. Hello JPA - 애플리케이션 개발
- 주의
    - 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
    - 엔티티 매니저는 쓰레드간에 공유 X(사용하고 버려야 한다)
    - JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
- JPQL
    - JPA를 사용하면 엔티티 객체를 중심으로 개발
    - 문제는 검색 쿼리
    - 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
    - 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
    - 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색
    조건이 포함된 SQL이 필요 
    - JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
    - SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
    - JPQL은 엔티티 객체를 대상으로 쿼리
    - SQL은 데이터베이스 테이블을 대상으로 쿼리
    - 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
    - SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
    - JPQL을 한마디로 정의하면 객체 지향 SQL
    - JPQL은 뒤에서 아주 자세히 다룸
# 3. 영속성 관리 - 내부 동작 방식
## A. 영속성 컨텍스트 1
### JPA에서 가장 중요한 2가지
- 객체와 관계형 데이터베이스 매핑하기(Object Relational Mapping)
- 영속성 컨텍스트
### 영속성 컨텍스트
- JPA를 이해하는데 가장 중요한 용어
- "엔티티를 영구 저장하는 환경" 이라는 뜻
- EntityManager.persiste(entity);
### 엔티티 매니저? 영속성 컨텍스트?
- 영속성 컨텍스트는 논리적인 개념
- 눈에 보이지 않는다.
- 엔티티 매니저를 통해서 영속성 컨텍스트에 접근
### 엔티티의 생명주기
- 비영속(new/transient)
    - 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
    - Member member = new Member(); <br>
    member.setId("member1"); <br>
    member.setUsername("회원1"); 
- 영속(managed)
    - 영속성 컨텍스트에 관리되는 상태
    - Member member = new Member(); <br>
    member.setId("member1"); <br>
    member.setUsername("회원1");<br>
    EntityManager em = emf.createEntityManager();<br>
    em.getTransaction().begin(); <br>
    // 객체를 저장한 상태(영속) <br>
    em.persist(member); 
- 준영속(detached)
    - 영속성 컨텍스트에 저장되었다가 분리된 상태
    - em.detach(member); // 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
- 삭제(removed)
    - 삭제된 상태
    - em.remove(member); // 객체를 삭제한 상태(삭제)

### 영속성 컨텍스트의 이점
- 1차 캐시
- 동일성 보장
- 트랜잭션을 지원하는 쓰기 지연
- 변경 감지
- 지연 로딩

## B. 영속성 컨텍스트 2
### 영속성 컨텍스트의 이점 - 1차 캐시
- Member member = new Member(); <br>
member.setId("member1");<br>
member.setUsername("회원1");<br>
// 1차 캐시에 저장됨<br>
em.persist(member); <br>
// 1차 캐시에서 조회<br>
Member findMember = em.find(Member.class, "member1");
- 위에 코드처럼 조회를 했을때 직접 DB에서 가져오는 것이 아닌, 영속 컨텍스트에서 1차캐시에서 찾아서 반환한다.
- 하나의 트랜잭션에 대해서만 이루어지는게 1차캐시라고 하는데, 이 부분에선 하나의 트랜잭션에서만 이루어진다. 

### 영속성 컨텍스트의 이점 - 영속 엔티티의 동일성 보장 
- Member a = em.find(Member.class, "member1"); <br>
Member b = em.find(Member.class, "member2"); <br>
System.out.println(a==b); // 동일성 비교 true 
- 1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을
데이터베이스가 아닌 애플리케이션 차원에서 제공

### 영속성 컨텍스트의 이점 - 엔티티 등록, 트랜잭션을 지원하는 쓰기 지연
- EntityManager em = emf.createEntityManager();<br>
EntityTransaction transaction = em.getTransaction(); <br>
// 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다. <br>
transaction.begin(); // [트랜잭션] 시작 <br>
em.persist(memberA); <br> 
em.persist(memberB); <br>
// 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.<br>
// 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다. <br>
transaction.commit(); // [트랜잭션] 커밋 
- 버퍼링을 했다가 한번에 write해서 성능을 최적화가 가능하다.

### 영속성 컨텍스트의 이점 - 엔티티 수정, 변경 감지
```
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
transaction.begin(); // [트랜잭션] 시작
// 영속 엔티티 조회
Member memberA = em.find(Member.class, "memberA");
// 영속 엔티티 데이터 수정
memberA.setUsername("hi");
memberA.setAge(10);
//em.update(member) 이런 코드가 있어야 하지 않을까? ==> 아니다, 자동으로 업데이트 쿼리가 나감
transaction.commit(); // [트랜잭션] 커밋

```
- collection에서 다루는것 처럼 자동으로 변경이 감지되어서 update 된다.
- 값을 조회할 때 스냅샷이라고해서 그때당시의 값을 저장해두었다가, 커밋되는 시점에 비교 해서 바뀌어있으면
그떄 update query를 저장소에 저장해서 나중에 처리할 수 있도록 해준다, 삭제도 마찬가지임

## C. 플러시
- 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영하는 것
### 플러시 발생
- 변경 감지
- 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
- 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송 (등록, 수정, 삭제 쿼리)
### 영속성 컨텍스트를 플러시하는 방법
- em.flush() - 직접 호출
- 트랜잭션 커밋 - 플러시 자동 호출
- JPQL 쿼리 실행 - 플러시 자동 호출
### JPQL 쿼리 실행시 플러시가 자동으로 호출되는 이유
```
em.persist(memberA);
em.persist(memberB);
em.persist(memberC);

// 중간에 JPQL 실행
query = em.createQuery("select m from Member m", Member.class);
List<Member> members= query.getResultList();
```
- 위의 코드에 상황처럼 중간에 JPQL 실행 시점 이전에 자동으로 플러시가 호출된다.
- 왜냐하면, 저장한상태에서 select를 했는데 플러시를 안하면 데이터가 안보이니까.

### 플러시 모드 옵션
- em.setFlushMode(FlushModeType.COMMIT)
- FlushModeType.AUTO
    - 커밋이나 쿼리를 실행할 때 플러시(기본값)
- FlushModeType.COMMIT
    - 커밋할 때만 플러시

### 플러시는!
- 영속성 컨텍스트를 비우지 않음
- 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
- 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됨

## D. 준영속 상태
- 영속 -> 준영속
- 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
- 영속성 컨텍스트가 제공하는 기능을 사용 못함

### 준영속 상태로 만드는 방법
- em.detach(entity) -> 특정 엔티티만 준영속 상태로 전환
- em.clear() -> 영속성 컨텍스트를 완전히 초기화
- em.close() -> 영속성 컨텍스트를 종료

## E. 정리

# 4. 엔티티 매핑

## A. 객체와 테이블 매핑

## B. 데이터베이스 스키마 자동 생성

## C. 필드와 컬럼 매핑

## D. 기본 키 매핑

## E. 실전 예제 1 - 요구사항 분석과 기본 매핑

# 5. 연관관계 매핑 기초
# 6. 다양한 연관관계 매핑
# 7. 고급 매핑
# 8. 프록시와 연관관계 관리
# 9. 값 타입
# 10. 객체지향 쿼리 언어1 - 기본 문법
# 11. 객체지향 쿼리 언어2 - 중급 문법