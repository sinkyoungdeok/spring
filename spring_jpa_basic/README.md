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
### 엔티티 매핑 소개
- 객체와 테이블 매핑: @Entity, @Table
- 필드와 컬럼 매핑: @Column
- 기본 키 매핑: @Id
- 연관관계 매핑: @ManaToOne, @JoinColumn

## A. 객체와 테이블 매핑
### @Entity
- @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
- JPA를 사용해서 테이브로가 매핑할 클래스는 @Entity 필수
- 주의
    - 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자)
    - final 클래스, enum, interface, inner 클래스 사용X
    - 저장할 필드에 final 사용 X
### @Entity 속성 정리
- 속성: name
    - JPA에서 사용할 엔티티 이름을 지정한다.
    - 기본값: 클래스 이름을 그대로 사용(예: Member)
    - 같은 클래스 이름이 없음녀 가급적 기본값을 사용한다.
- @Table
    - @Table은 엔티티와 매핑할 테이블 지정
    - name: 매핑할 테이블 이름
    - catalog: 데이터베이스 catalog 매핑
    - schema: 데이터베이스 schema 매핑
    - uniqueConstraints: DDL 생성 시에 유니크 제약 조건 생성
    

## B. 데이터베이스 스키마 자동 생성
- DDL을 애플리케이션 실행 시점에 자동 생성
- 테이블 중심 -> 객체 중심
- 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한 DDL 생성
- 이렇게 생성된 DDL은 개발 장비에서만 사용
- 생성된 DDL은 운영서버에서는 사용하지 않거나, 적절히 다듬은 후 사용
### 데이터베이스 스키마 자동 생성 - 속성
- hibernate.hbm2ddl.auto
    - create: 기존 테이블 삭제 후 다시 생성 (DROP + CREATE)
    - create-drop: create와 같으나 종료시점에 테이블 DROP
    - update: 변경분만 반영(운영DB에는 사용하면 안됨)
    - validate: 엔티티와 테이블이 정상 매핑되었는지만 확인
    - none: 사용하지 않음
### 데이터베이스 스키마 자동 생성 - 주의
- 운영 장비에는 절대 create, create-drop, update 사용하면 안된다.
- 개발 초기 단계는 create 또는 update
- 테스트 서버는 update 또는 validate
- 스테이징과 운영 서버는 validate 또는 none

### DDL 생성 기능
- 제약조건 추가: 회원 이름은 필수, 10자 초과X
    - @Column(nullable = false, length = 10)
- 유니트 제약 조건 추가 
    - Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames={"NAME","AGE"} )})
- DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.
## C. 필드와 컬럼 매핑
### 요구사항 추가
1. 회원은 일반 회원과 관리자로 구분해야 한다.
2. 회원 가입일과 수정일이 있어야 한다.
3. 회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.
### 매핑 어노테이션 정리
- @Column: 컬럼 매핑
- @Temporal: 날짜 타입 매핑
- @Enumerated: enum 타입 매핑
- @Lob: BLOB, CLOB 매핑
- @Transient: 특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
### @Column
- name: 필드와 매핑할 테이블의 컬럼 이름
- insertable, updatable: jpa가 DB에 등록, 변경 가능 여부
- nullable(DDL): null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시에 not null 제약조건이 붙는다.
- unique(DDL): @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다.
- columnDefinition(DDL): 데이터베이스 컬럼 정보를 직접 줄 수 있다.
ex) varchar(100) default ‘EMPTY'
- length(DDL): 문자 길이 제약조건, String 타입에만 사용한다.
- precision,scale(DDL): BigDecimal 타입에서 사용한다(BigInteger도 사용할 수 있다).
precision은 소수점을 포함한 전체 자 릿수를, scale은 소수의 자릿수
다. 참고로 double, float 타입에는 적용되지 않는다. 아주 큰 숫자나
정 밀한 소수를 다루어야 할 때만 사용한다.
### @Enumerated
- 자바 enum 타입을 매핑할 때 사용
- 주의! ORDINAL 사용 X (enum에 대한 순서를 저장하는것인데, 순서이기떄문에 순서를 enum클래스에서 변경하게 되면, DB에 기존에 저장되어있는 순서랑 동기화가 안되기 떄문에 안쓰는게 좋다)

### @Temporal
- 날짜 타입(java.util.Date, java.util.Calendar)을 매핑할 때 사용
- 참고: LocalDate, LocalDateTime을 사용할 때는 생략 가능(최신 하이버네이트 지원)
- value
    - TemporalType.DATE: 날짜, 데이터베이스 date 타입과 매핑
      (예: 2013–10–11)
    - TemporalType.TIME: 시간, 데이터베이스 time 타입과 매핑
      (예: 11:11:11)
    - TemporalType.TIMESTAMP: 날짜와 시간, 데이터베이 스
      timestamp 타입과 매핑(예: 2013–10–11 11:11:11)

### @Lob
- 데이터베이스 BLOB, CLOB 타입과 매핑
- @Lob에는 지정할 수 있는 속성이 없다.
- 매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
    - CLOB: String, char[], java.sql.CLOB
    - BLOB: byte[], java.sql.BLOB

### @Transient
- 필드 매핑X
- 데이터베이스에 저장X, 조회X
- 주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용
- @Transient<br>
private Integer temp;


## D. 기본 키 매핑
### 기본 키 매핑 어노테이션
- @Id
- @GeneratedValue

### 기본 키 매핑 방법
- 직접 할당: @Id만 사용
- 자동 생성(@GeneratedValue)
    - IDENTITY: 데이터베이스에 위임, MYSQL
    - SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE
        - @SequenceGenerator 필요
    - TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용
        - @TableGenerator 필요
    - AUTO: 방언에따라 자동 지정, 기본값 

### IDENTITY 전략 - 특징
- 기본 키 생성을 데이터베이스에 위임
- 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용 ( ex: MySQL의 AUTO_INCRESEMENT)
- JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
- AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행 한 이후에 ID 값을 알 수 있음
- IDENTITY 전략은 em.persist() 시점에 즉시 INSERT_SQL 실행하고 DB에서 식별자를 조회

### SEQUENCE 전략 - 특징
- 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트(ex: 오라클 시퀀스)
- 오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용

### SEQUENCE - @SequenceGenerator
- 주의: allocationSize 기본값 = 50
- name: 식별자 생성기 이름
- sequenceName: 데이터베이스에 등록되어 있는 시퀀스 이름
- initialValue: DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1 시작하는수를 지정한다.
- allocationSize: 시퀀스 한번 호출에 증가하는 수(성능 최적화에 사용됨),
데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값을 반드시 1로 설정해야 한다.
- catalog, schema: 데이터베이스 catalog, schema 이름 

### TABLE 전략
- 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
- 장점: 모든 데이터베이스에 적용 가능
- 단점: 성능

### @TableGenerator - 속성
- name: 식별자 생성기 이름
- table: 키 생성 테이블명
- pkColumnName: 시퀀스 컬럼명
- valueColumnNa: 시퀀스 값 컬럼명
- pkColumnValue: 키로 사용할 값 이름
- initialValue: 초기 값, 마지막으로 생성된 값이 기준이다.
- allocationSize: 시퀀스 한번 호출에 증가하는 수(성능 최적화에 사용됨)
- catalog, schema: 데이터베이스 catalog, schema 이름
- uniqueConstraint(DDL): 유니크 제약 조건을 지정할 수 있다.

### 권장하는 식별자 전략
- 기본 키 제약 조건: null 아님, 유일, 변하면 안된다.
- 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자.
- 예를들어 주민등록번호도 기본 키로 적절하지 않다.
- 권장: Long형 + 대체키 + 키 생성전략 사용

## E. 실전 예제 1 - 요구사항 분석과 기본 매핑
### 요구사항 분석
- 회원은 상품을 주문할 수 있다.
- 주문 시 여러 종류의 상품을 선택할 수 있다.
### 기능 목록
- 회원 기능
    - 회원 등록
    - 회원 조회
- 상품 기능
    - 상품 등록
    - 상품 수정
    - 상품 조회
- 주문 기능
    - 상품 주문
    - 주문내역 조회
    - 주문 취소
    
### 도메인 모델 분석
- 회원과 주문의 관계: 회원은 여러 번 주문 할 수 있다. (일대다)
- 주문과 상품의 관계: 주문할 때 여러 상품을 선택할 수 있다. 반대로 같은 상품도 여러 번 주문될 수 있다.
주문상품 이라는 모델을 만들어서 다대다 관계를 일다대, 다대일 관계로 풀어냄 

### 테이블 설계
![image](https://user-images.githubusercontent.com/28394879/128124814-3257059b-c4a1-4bcc-b696-1416f1e8dded.png)

### 엔티티 설계와 매핑
![image](https://user-images.githubusercontent.com/28394879/128124974-6a962ef6-558d-445f-9e1d-24adbf75af13.png)

### 데이터 중심 설계의 문제점
- 일단 위의 테이블 설계가 데이터 중심 설계이다.
- 현재 방식은 객체 설계를 테이블 설계에 맞춘 방식
- 테이블의 외래키를 객체에 그대로 가져옴
- 객체 그래프 탐색이 불가능
- 참조가 없으므로 UML도 잘못됨


# 5. 연관관계 매핑 기초
## A. 단방향 연관관계
### 목표
- 객체와 테이블 연관관계의 차이를 이해
- 객체의 참조와 테이블의 외래 키를 매핑
- 용어 이해
    - 방향(Direction): 단방향, 양방향
    - 다중성(Multiplicity): 다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:M) 이해
    - 연관관계의 주인(Owner): 객체 양방향 연관관게는 관리 주인이 필요
    
### 연관관계가 필요한 이유
- 객체지향 설계의 목표는 자율적인 객체들의 협력 공동체를 만드는 것이다. - 조영호(객체지향의 사실과 오해)

### 예제 시나리오
- 회원과 팀이 있다.
- 회원은 하나의 팀에만 소속될 수 있다.
- 회원과 팀은 다대일 관계다.

### 객체를 테이블에 맞추어 모델링(연관관계가 없는 객체)
![image](https://user-images.githubusercontent.com/28394879/128131051-2a59c3ec-a202-4b49-9f8f-d7f701d8d399.png)

### 객체를 테이블에 맞추어 데이터 중심으로 모델링 하면, 협력 관계를 만들 수 없다.
- 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다.
- 객체는 참조를 사용해서 연관된 객체를 찾는다.
- 테이블과 객체 사이에는 이런 큰 간격이 있다.

### 단방향 연관관계
- 객체 지향 모델링(객체 연관관계 사용)
    - ![image](https://user-images.githubusercontent.com/28394879/128131719-7a4d167c-d886-4bce-8f90-b39b29dbcf84.png)
    

## B. 양방향 연관관계와 연관관계의 주인 1-기본
### 다대일 단방향
![image](https://user-images.githubusercontent.com/28394879/128489873-4ba5514c-0a7e-4129-8bfa-b70b8fb9efaf.png)
- 단방향에서 양방향 매핑으로 바꾼다고 해도 DB테이블에선 차이점이 없다. 왜냐하면 FK로 JOIN을 하면 양방향이 되기 떄문, 그리고 애초에 DB는 방향자체가 없이 양방향으로 되어 있다.
- 객체는 Member에서 Team, Team에서 Member로 양방향이 가능하려면 매핑을 따로 해주어야 한다. 
### 양방향 매핑 
![image](https://user-images.githubusercontent.com/28394879/128490062-8b90fb81-6467-41a8-b63c-9605d2a1daf6.png)
```
Team findTeam = em.find(Team.class, team.getId());
int memberSize = findTeam.getMembers().size();// 역방향 조회

```
- 그래도 객체는 가급적이면 단방향이 좋다.
- 오히려 객체는 양방향이면 신경쓸게 많은데, 그부분은 나중에 다시 정리 

### 연관관계의 주인과 mappedBy
- mappedBy = JPA의 멘탈붕괴 난이도
- mappedBy는 처음에는 이해하기 어렵다.
- 객체와 테이블간에 연관관계를 맺는 차이를 이해해야 한다.

### 객체와 테이블의 관계를 맺는 차이
- 객체 연관관계 = 2개
    - 회원 -> 팀 연관관계 1개(단방향)
    - 팀 -> 회원 연관관계 1개(단방향)
- 테이블 연관관계 = 1개
    - 회원 <-> 팀의 연관관계 1개(양방향)

### 객체의 양방향 관계
- 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개다.
- 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.
- A -> B
- B -> A

### 테이블의 양방향 연관관계
- 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
- MEMBER.TEAM_ID 외래 키 하나로 양방향 연관관계 가짐(양쪽으로 조인할 수 있다.)
```
SELECT *
FROM MEMBER M
JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID 
```

```
SELECT *
FROM TEAM T
JOIN MEMBER M ON T.TEAM_ID = M.TEAM_ID
```

### 둘 중 하나로 외래키를 관리해야 한다.
![image](https://user-images.githubusercontent.com/28394879/128494479-793cbe7a-cd0b-45af-9876-4eeeac34ae62.png)

### 연관관계의 주인(Owner)
- 양방향 매핑 규칙
    - 객체의 두 관계중 하나를 연관관계의 주인으로 지정
    - 연관관계의 주인만이 외래 키를 관리(등록, 수정)
    - 주인이 아닌쪽은 읽기만 가능
    - 주인은 mappedBy 속성 사용X
    - 주인이 아니면 mappedBy 속성으로 주인 지정
    
### 누구를 주인으로 ?
- 외래 키가 있는 곳을 주인으로 정해라
- 여기서는 Member.team이 연관관계의 주인
![image](https://user-images.githubusercontent.com/28394879/128494834-60cb5da4-eca6-4107-90a5-2e9281861b63.png)

## C. 양방향 연관관계와 연관관계의 주인 2-주의점, 정리 
### 양방향 매핑시 가장 많이 하는 실수
(연관관계의 주인에 값을 입력하지 않음)
```
Team team = new Team();
 team.setName("TeamA");
 em.persist(team);
 Member member = new Member();
 member.setName("member1");
 //역방향(주인이 아닌 방향)만 연관관계 설정
 team.getMembers().add(member);
 em.persist(member);
```
- 이렇게 할 경우 Member의 TEAM_ID가 null로 들어간다.

### 양방향 매핑시 연관관계의 주인에 값을 입력해야 한다.
(순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.)
```
Team team = new Team();
 team.setName("TeamA");
 em.persist(team);
 Member member = new Member();
 member.setName("member1");
 team.getMembers().add(member);
 //연관관계의 주인에 값 설정
 member.setTeam(team); //**
 em.persist(member);
```
- 이렇게 해야 Member에 TEAM_ID가 들어간다.
- 1차 캐시에서 데이터를 가져오면 (flush,clear사용 안할떄) Team, Member가 연결 안되어 있는 상태로 조회가 되버린다.
그래서, team.getMembers().add(member); 로 연결해서 양방향 연결을 해주어야 한다.

### 양방향 연관관계 주의 - 실습
- 순수 객체 상태를 고려하여 항상 양쪽에 값을 설정하자.
- 연관관계 편의 메소드를 생성하자 (Member의 changeTeam)
- 양방향 매핑시에 무한 루프를 조심하자
    - 예: toString(), lombok, JSON 생성 라이브러리 
    - 가급적이면 toString을 쓰지말자
    - JSON 라이브러리   
        - Controller에서 Entity를 반환하지 말자
        - Entity를 반환하면 Entity를 변경하는 순간 API Spec이 변경 될 수 있다.
        - Entity를 반환하면 양방향 매핑 시 무한루프가 생길 수 있다.
        - DTO로 변환해서 반환하는것을 추천한다. 이렇게 하면 JSON 생성 라이브러리에 의한 에러는 피할 수 있다.
    
### 양방향 매핑 정리
- 단방향 매핑만으로도 이미 연관관계 매핑은 완료
- 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가 된 것 뿐
- JPQL에서 역방향으로 탐색할 일이 많음
- 단방향 매핑을 잘 하고 양방향은 필요할 때 추가 해도 됨 (테이블에 영향을 주지 않음)

### 연관관계의 주인을 정하는 기준
- 비즈니스 로직을 기준으로 연관관계의 주인을 선택하면 안됨
- 연관관계의 주인은 외래 키의 위치를 기준으로 정해야함

## D. 실전 예제 2 - 연관관계 매핑 시작
### 테이블 구조
![image](https://user-images.githubusercontent.com/28394879/128958589-b48ba463-bd05-4641-8937-967775c63f7b.png)

### 객체 구조
![image](https://user-images.githubusercontent.com/28394879/128958635-91c75619-6016-4543-b22c-07a2f48ff4cc.png)

- 단방향으로 다 설계 할 수 있지만, 나중에 조회에 편의성을 가져가기 위해서 Order, Member에 양방향을 걸어 두었다.
- 핵심은 가능하면 다 단방향으로 설꼐하는게 좋다.
- 조회를 편하게하고, JPQL을 편하게 작성하려면 일부는 양방향으로 가져가는게 좋긴 하다.

# 6. 다양한 연관관계 매핑
## A. 다대일 [N:1]
### 연관관계 매핑시 고려사항 3가지
- 다중성
- 단방향, 양방향
- 연관관계의 주인

### 다중성
- 다대일: @ManyToOne
- 일대다: @OneToMany
- 일대일: @OneToOne
- 다대다: @ManyToMany
    - 실무에서 쓰면 안됨
    
### 단방향, 양방향
- 테이블
    - 외래 키 하나로 양쪽 조인 가능
    - 사실 방향이라는 개념이 없음
- 객체
    - 참조용 필드가 있는 쪽으로만 참조 가능
    - 한쪽만 참조하면 단방향
    - 양쪽이 서로 참조하면 양방향
        - 양방향이 사람들이 이해하기 쉽게 하기 위해서 용어를 만든 것이다.
        - 실제로는 단방향이 2개로 이루어진 것이다.

### 연관관계의 주인
- 테이블은 외래 키 하나로 두 테이블이 연관관계를 맺음
- 객체 양방향 관계는 A->B, B->A 처럼 참조가 2군데
- 객체 양방향 관계는 참조가 2군데 있음. 둘중 테이블의 외래 키를 관리할 곳을 지정해야함
- 연관관계의 주인: 외래 키를 관리하는 참조
- 주인의 반대편: 외래키에 영향을 주지 않음, 단순 조회만 기능 제공

### 다대일
### 다대일 단방향
![image](https://user-images.githubusercontent.com/28394879/129174139-fb9b04fa-8ca3-4a15-b241-327e4419d395.png)
- 여기 기분에서 Member에다가 (FK가 있는 곳) 참조를 걸고, 연관관계 매핑을 하면 됨

### 다대일 단방향 정리
- 가장 많이 사용하는 연관관계
- 다대일의 반대는 일대다

### 다대일 양방향
![image](https://user-images.githubusercontent.com/28394879/129175038-88a2620f-e8f4-403b-8c7e-2c30a6e42472.png)

## B. 일대다 [1:N]
### 일대다 단방향
![image](https://user-images.githubusercontent.com/28394879/129175668-931d0ab7-4483-4ff1-8954-2883de401237.png)

### 일대다 단방향 정리
- 일대다 단방향은 일대다(1:N)에서 일(1)이 연관관계의 주인
- 테이블 일대다 관계는 항상 다(N) 쪽에 외래 키가 있음
- 객체와 테이블의 차이 떄문에 반대편 테이브르이 외래 키를 관리하는 특이한 구조
- @JoinColumn을 꼭 사용해야 함. 그렇지 않으면 조인 테이블 방식을 사용함(중간에 테이블을 하나 추가함)
- 일대다 단방향 매핑의 단점
    - 엔티티가 관리하는 외래 키가 다른 테이블에 있음
    - 연관관계 관리를 위해 추가로 UPDATE SQL 실행
- 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자
- 실무에서는 앵간해선 쓰면 안됨
- 일대다 쓰지말고 다대일 써라.

### 일대다 양방향 정리
- 이런 매핑은 공식적으로 존재X
- @JoinColumn(insertable=false, updatable=false)
- 읽기 전용 필드를 사용해서 양방향 처럼 사용하는 방법
- 다대일 양방향을 사용하자


## C. 일대일 [1:1]
### 일대일 관계
- 일대일 관계는 그 반대도 일대일
- 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
    - 주 테이블에 외래 키
    - 대상 테이블에 외래 키
- 외래 키에 데이터베이스 유니크(UNI) 제약조건 추가

### 일대일: 주 테이블에 외래 키 단방향
![image](https://user-images.githubusercontent.com/28394879/129365995-5b26ed4e-f2dd-4cc0-8e15-67ccd9ec6185.png)

### 일대일: 주 테이블에 외래 키 단방향 정리
- 다대일(@ManyToOne) 단방향 매핑과 유사

### 일대일: 주 테이블에 외래 키 양방향
![image](https://user-images.githubusercontent.com/28394879/129366659-42ed29b7-35e4-4155-b72a-996c12e0f2b2.png)

### 일대일: 주 테이블에 외래 키 양방향 정리
- 다대일 양방향 매핑 처럼 외래 키가 있는 곳이 연관관계의 주인
- 반대편은 mappedBy 적용

### 일대일: 대상 테이블에 외래 키 단방향
![image](https://user-images.githubusercontent.com/28394879/129366827-154daa67-b61f-4326-a569-1a1ac759bc68.png)
- 이 경우에 있어서는 지원자체가 안되므로 이렇게는 하지마라.

### 일대일: 대상 테이블에 외래 키 단방향 정리
- 단방향 관계는 JPA 지원 X
- 양방향 관계는 지원

### 일대일: 대상 테이블에 외래 키 양방향
![image](https://user-images.githubusercontent.com/28394879/129367006-761ed7a2-51d6-4e51-920f-8a1b6fe3ca43.png)
- 사실 일대일 주 테이블에 외래 키 양방향과 매핑 방법은 같음

### 일대일 정리
- 주 테이블에 외래 키
    - 주 객체가 대상 객체의 참조를 가지는 것 처럼 <br>
    주 테이블에 외래 키를 두고 대상 테이블을 찾음  
    - 객체지향 개발자 선호
    - JPA 매핑 편리
    - 장점: 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
    - 단점: 값이 없으면 외래 키에 null 허용
    - 개인적으로는 실무에서 주로 이게 더 좋다.
- 대상 테이블에 외래 키
    - 대상 테이블에 외래 키가 존재
    - 전통적인 데이터베이스 개발자 선호
    - 장점: 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
    - 단점: 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨(프록시는 뒤에서 설명)
    
## D. 다대다 [N:M]
### 다대다
- 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없음
- 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야 함
![image](https://user-images.githubusercontent.com/28394879/129369154-cc154c26-f032-444e-a6ff-e01c806c2770.png)

### 다대다
- 객체는 컬렉션을 사용해서 객체 2개로 다대다 관계 가능
  ![image](https://user-images.githubusercontent.com/28394879/129369453-1bbd8112-f449-44e3-8cf9-68ca50945c4e.png)
  
### 다대다
- @ManyToMany 사용
- @JoinTable로 연결 테이블 지정
- 다대다 매핑: 단방향, 양방향 가능

### 다대다 매핑의 한계
- 편리해 보이지만 실무에서 사용X
- 연결 테이블이 단순히 연결만 하고 끝나지 않음
- 주문시간, 수량 같은 데이터가 들어올 수 있음
  ![image](https://user-images.githubusercontent.com/28394879/129370524-3f99ebf7-cb33-44eb-8dea-af8e58b0bfc1.png)
  
### 다대다 한계 극복
- 연결 테이블용 엔티티 추가(연결 테이블을 엔티티로 승격)
- @ManyToMany -> @OneToMany, @ManyToOne
  ![image](https://user-images.githubusercontent.com/28394879/129372065-e807d067-4d46-4d12-9da0-7664990d7dd9.png)
  

## E. 실전 예제 3 - 다양한 연관관계 매핑
### 배송, 카테고리 추가 - 엔티티
- 주문과 배송은 1:1(@OneToMany)
- 상품과 카테고리는 N:M(@ManyToMany)
  ![image](https://user-images.githubusercontent.com/28394879/129429619-a9ba537f-cd38-4872-8998-deea1c207423.png)

### 배송, 카테고리 추가 - ERD
![image](https://user-images.githubusercontent.com/28394879/129429666-fb216f8b-2c70-49a5-ac90-32825fac8799.png)
- 일대일 관계는 외래 키를 양쪽 어디나 둘 수 있음
    - ORDERS에 두면: 성능(바로 확인 가능, 나중에 프록시 등등) + 객체 입장에서 편리함
    - DELIVERY에 두면: 1 -> N으로 확장이 편리함(DB컬럼 변경 없이 N으로 변경 가능)
- 다대다 관계 -> 테이블은 중간 테이블을 만들고 다대일 관계로 풀어야 한다.
### 배송, 카테고리 추가 - 엔티티 상세
![image](https://user-images.githubusercontent.com/28394879/129429732-f752cef1-1fee-4713-9f3d-c5be9b1a216c.png)

### N:M 관계는 1:N, N:1 로
- 테이블의 N:M 관계는 중간 테이블을 이용해서 1:N, N:1
- 실전에서는 중간 테이블이 단순하지 않다.
- @ManyToMany는 제약: 필드 추가X, 엔티티 테이블 불일치
- 실전에서는 @ManyToMany 사용X 

### @JoinColumn
- 외래 키를 매핑할 때 사용
  ![image](https://user-images.githubusercontent.com/28394879/129430444-6ccc85f3-fa8d-4937-ae4e-d761b42a4522.png)

### @ManyToOne - 주요 속성
- 다대일 관계 매핑
![image](https://user-images.githubusercontent.com/28394879/129430472-c032c16b-9fcb-4839-bee7-cdeddd47f41b.png)
  
### @OneToMany - 주요 속성
- 다대일 관계 매핑
  ![image](https://user-images.githubusercontent.com/28394879/129430503-2e428617-e751-4874-9980-74be03887350.png)

# 7. 고급 매핑
## A. 상속관계 매핑

### 상속관계 매핑
- 관계형 데이터베이스는 상속 관계 X
- 슈퍼타입 서브타입 관계라는 모델링 기법이 객체 상속과 유사
- 상속관계 매핑: 객체의 상속과 구조와 DB의 슈퍼타입 서브타입 관계를 매핑
  ![image](https://user-images.githubusercontent.com/28394879/129435611-a5c3fbc4-4cc1-4a3e-8086-f669adcc91d1.png)
  
### 상속관계 매핑
- 슈퍼타입 서브타입 논리 모델을 실제 물리 모델로 구현하는 방법
    - 각각 테이블로 변환 -> 조인 전략
    - 통합 테이블로 변환 -> 단일 테이블 전략
    - 서뷰타입 테이블로 변환 -> 구현 클래스마다 테이블 전략

### 주요 어노테이션
- @Inheritance(strategy=InheritanceType.XXX)
    - JOINED: 조인 전략
    - SINGLE_TABLE: 단일 테이블 전략
    - TABLE_PER_CLASs: 구현 클래스마다 테이블 전략
- @DiscriminatorColumn(name="DTYPE")
- @DiscriminatorValue("XXX")

### 조인 전략
![image](https://user-images.githubusercontent.com/28394879/129435708-d6345887-ae0e-42fd-a16e-8b5e80e5b157.png)
- 장점
    - 테이블 정규화
    - 외래 키 참조 무결성 제약조건 활용가능
    - 저장공간 효율화
- 단점
    - 조회시 조인을 많이 사용, 성능 저하
    - 조회 쿼리가 복잡함
    - 데이터 저장시 INSERT SQL 2번 호출

### 단일 테이블 전략
![image](https://user-images.githubusercontent.com/28394879/129435791-073c4ab2-a98c-444a-bd81-87933bfaaa20.png)
- 장점
    - 조인이 필요 없으므로 일반적으로 조회 성능이 빠름
    - 조회 쿼리가 단순함
- 단점
    - 자식 엔티티가 매핑한 컬럼은 모두 null 허용
    - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다. 상황에 
    따라서 조회 성능이 오히려 느려질 수 있다.

### 구현 클래스마다 테이블 전략
![image](https://user-images.githubusercontent.com/28394879/129435837-8f900cd5-6ebd-42a6-aaf6-64b8407b28e2.png)
- 이 전략은 데이터베이스 설계자와 ORM 전문가 둘다 추천 X
- 장점
    - 서브 타입을 명확하게 구분해서 처리할 때 효과적
    - not null 제약 조건 사용 가능
- 단점
    - 여러 자식 테이블을 함께 조회할 때 성능이 느림(UNION SQL 필요)
    - 자식 테이블을 통합해서 쿼리하기 어려움


## B. Mapped Superclass - 매핑 정보 상속

### @MappedSuperclass
- 공통 매핑 정보가 필요할 때 사용(id, name)
  ![image](https://user-images.githubusercontent.com/28394879/129463939-e7c043a6-ce2b-4f00-9524-978009b5ace1.png)
  
### @MappedSuperclass
- 상속관계 매핑 X
- 엔티티 X, 테이블과 매핑 X
- 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
- 조회, 검색 불가(em.find(BaseEntity) 불가)
- 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장 

### @MappedSuperclass
- 테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할
- 주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공토으로 적용하는 정보를 모을 떄 사용
- 참고: @Entity 클래스는 엔티티나 @MappedSuperclass로 지정한 클래스만 상속 가능


## C. 실전 예제 4 - 상속관계 매핑
### 요구 사항 추가
- 상품의 종류는 음식, 도서, 영화가 있고 이후 더 확장될 수 있다.
- 모든 데이터는 등록일과 수정일이 필수다.

### 도메인 모델
![image](https://user-images.githubusercontent.com/28394879/129464113-25907d7d-4158-4db5-88ce-38a284e8d406.png)

### 도메인 모델 상세
![image](https://user-images.githubusercontent.com/28394879/129464131-609ce663-eead-456d-abab-ae5bb19754e5.png)

### 테이블 설계
![image](https://user-images.githubusercontent.com/28394879/129464137-37d52163-a807-43db-bdde-42df06d6153d.png)


# 8. 프록시와 연관관계 관리
## A. 프록시
### Member를 조회 할 때 Team도 함께 조회해야 할까 ?
![image](https://user-images.githubusercontent.com/28394879/129464577-3b7d0c1a-a52c-4f18-820a-8bf6c64e8ad3.png)
![image](https://user-images.githubusercontent.com/28394879/129464584-3c11c652-1863-4a9d-ba05-687cef94d266.png)

### 프록시 기초
- em.find() vs em.getReference()
    - em.find(): 데이터베이스를 통해서 실제 엔티티 객체 조회
    - em.getReference(): 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회

### 프록시 특징
- 실제 클래스를 상속 받아서 만들어짐
- 실제 클래스와 겉 모양이 같다.
- 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고
사용하면 됨(이론상)
  ![image](https://user-images.githubusercontent.com/28394879/129467412-1b2775bd-df47-4504-b06f-ceaee7e465b9.png)

### 프록시 특징
- 프록시 객체는 실제 객체의 참조(target)를 보관
- 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출
  ![image](https://user-images.githubusercontent.com/28394879/129467529-362aaaac-0972-4348-b3e8-87f4780a0fab.png)
  
### 프록시 객체의 초기화
```
Member member = em.getReference(Member.class, "id1");
member.getName();
```
![image](https://user-images.githubusercontent.com/28394879/129467545-caf2ea65-d46d-466e-88ef-615cd21759d8.png)

### 프록시의 특징
- 프록시 객체는 처음 사용할 떄 한번만 초기화
- 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님, 초기화 되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
- 프록시 객체는 원본 엔티티를 상속 받음, 따라서 타입 체크시 주의해야함 (==비교 실패, 대신 instance of 사용)
- 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
- 초기화는 영속성 컨텍스트의 도움을 받아야 가능하다. 따라서 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태의 프록시를 초기화 하면 문제가 발생한다.
  (하이버네이트는 org.hibernate.LazyInitializationException 예외를 터트림)

### 프록시 확인
- 프록시 인스턴스의 초기화 여부 확인
PersistenceUnitUtil.isLoaded(Object entity)
- 프록시 클래스 확인 방법
entity.getClass().getName() 출력(..javasist.. or HibernateProxy...)
- 프록시 강제 초기화
org.hibernate.Hibernate.initialize(entity)
- 참고: JPA 표준은 강제 초기화 없음
강제 호출: member.getName()

## B. 즉시 로딩과 지연 로딩
### Member를 조회할 때 Team도 함께 조회해야 할까?
- 단순히 member 정보만 사용하는 비즈니스 로직 
- println(member.getName());
  ![image](https://user-images.githubusercontent.com/28394879/129473894-42160de0-a5da-4adc-b149-d0dca787a647.png)

### 지연 로딩 LAZY을 사용해서 프록시로 조회
![image](https://user-images.githubusercontent.com/28394879/129473912-afc19f8f-cee2-42a2-89c9-f020e575ed7e.png)

### 지연 로딩
![image](https://user-images.githubusercontent.com/28394879/129474009-753529f3-41ce-44ec-9e7a-28f95b507020.png)

### 지연 로딩 LAZY을 사용해서 프록시로 조회
![image](https://user-images.githubusercontent.com/28394879/129474037-2b187a23-0809-450d-b606-99f49ea93005.png)
```
- Team team = member.getTeam(); 
team.getName(); // 실제 team을 사용하는 시점에 초기화(DB 조회)
```

### Member와 Team을 자주 함께 사용한다면?
- EAGER 전략 사용

### 즉시 로딩 EAGER를 사용해서 함께 조회
![image](https://user-images.githubusercontent.com/28394879/129474098-1f9e8d9c-ea74-49ce-9161-66f208a987e0.png)

### 즉시 로딩
![image](https://user-images.githubusercontent.com/28394879/129474113-de81486f-f40d-408f-b29b-884ed8fc75ec.png)

### 즉시 로딩(EAGER), Member 조회시 항상 Team도 조회
![image](https://user-images.githubusercontent.com/28394879/129474127-b516a017-8ffd-46f3-bb43-2e86aa24034b.png)

### 프록시와 즉시로딩 주의
- 가급적 지연 로딩만 사용(특히 실무에서)
- 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
- 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
- @ManyToOne, @OneToOne은 기본이 즉시 로딩 -> LAZY로 설정
- @OneToMany, @ManyToMany는 기본이 지연 로

### 지연 로딩 활용
- Member와 Team은 자주 함께 사용 -> 즉시 로딩
- Member와 Order는 가끔 사용 -> 지연 로딩
- Order와 Product는 자주 함꼐 사용 -> 즉시 로딩
  ![image](https://user-images.githubusercontent.com/28394879/129474927-0f94fdb0-4945-4848-9205-535c08e23a41.png)
  
### 지연 로딩 활용
![image](https://user-images.githubusercontent.com/28394879/129474958-54e0b37a-4068-4ccb-935f-91dc3c021d1f.png)
![image](https://user-images.githubusercontent.com/28394879/129474982-56f0f175-1603-4019-ad1a-cd88de0f42bd.png)

### 지연 로딩 활용 - 실무
- 모든 연관관계에 지연 로딩을 사용해라!
- 실무에서 즉시 로딩을 사용하지 마라!
- JPQL fetch 조인이나, 엔티티 그래프 기능을 사용해라! ( 뒤에서 정리 )
- 즉시 로딩은 상상하지 못한 쿼리가 나간다.

## C. 영속성 전이(CASCADE)과 고아 객체
### 영속성 전이: CASCADE
- 특정 엔티티를 영속 상태로 만들 떄 연관된 엔티티도 함께 영속상태로 만들고 싶을 떄
- ex) 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장

### 영속성 전이: 저장
```
@OneToMany(mappedBy="parent", cascade=CascadeType.PERSIST)
```
![image](https://user-images.githubusercontent.com/28394879/129481475-927f6dfb-bd29-42ab-9c63-8c4a57dee6d0.png)

### 영속성 전이: CASCADE - 주의!
- 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없음
- 엔티티를 영속화 할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공할 뿐

### CASCADE의 종류
- ALL: 모두 적용
- PERSIST: 영속
- REMOVE: 삭제
- MERGE: 병합
- REFRESH: REHRESH
- DETACH: DETACH

### 고아 객체
- 고아 객체 제거: 부모 엔티티와 연관관계까 끈헝진 자식 엔티티를 자동으로 삭제
- orphanRemoval = true
- Parent parent1 = em.find(Parent.class, id);
parent1.getChildren().remove(0); // 자식 엔티티를 컬렉션에서 제거
- DELETE FROM CHILD WHERE ID=?

### 고아 객체 - 주의
- 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능
- 참조 하는 곳이 하나일 때 사용해야 함!
- 특정 엔티티가 개인 소유 할 때 사용
- @OneToOne, @OneToMany만 가능
- 참고: 개념적으로 부모를 제거하면 자식은 고아가 된다. 따라서 고아 객체 제거 기능을 활성화 하면, 부모를 제거할 때 자식도 함께 제거 된다. 이것은 CascadeType.REMOVE처럼 동작한다.

### 영속성 전이 + 고아 객체, 생명 주기
- CascadeType.ALL + orphanRemovel = true
- 스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거
- 두 옵션을 모두 활성화 하면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있음
- 도멩니 주도 설계(DDD)의 Aggregate Root 개념을 구현할 때 유용


## D. 실전 예제 5 - 연관관계 관리
### 글로벌 페치 전략 설정
- 모든 연관관계를 지연 로딩으로
- @ManyToOne, @OneToOne은 기본이 즉시 로딩이므로 지연로딩으로 변경

### 영속성 전이 설정
- Order -> Delivery를 영속성 전이 ALL 설정
- Order -> OrderItem을 영속성 전이 ALL 설정

# 9. 값 타입
## A. 기본값 타입
### JPA의 데이터 타입 분류
- 엔티티 타입
    - @Entity로 정의하는 객체
    - 데이터가 변해도 식별자로 지속해서 추적 가능
    - ex) 회원 엔티티의 키나 나이 값을 변경해도 식별자로 인식 가능
- 값 타입
    - int, Integer, String 처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
    - 식별자가 없고 값만 있으므로 변경시 추적 불가
    - ex) 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체

### 값 타입 분류
- 기본값 타입
    - 자바 기본 타입(int, double)
    - 래퍼 클래스(Integer, Long)
    - String
- 임베디드 타입(embedded type, 복합 값 타입)
- 컬렉션 값 타입(collection value type)

### 기본값 타입
- ex) String name, int age
- 생명주기를 엔티티의 의존
    - ex) 회원을 삭제하면 이름, 나이 필드도 함께 삭제
- 값 타입은 공유하면 X
    - ex) 회원 이름 변경시 다른 회원의 이름도 함께 변경되면 안됨

### 참고: 자바의 기본 타입은 절대 공유X
- int, double 같은 기본 타입(primitive type)은 절대 공유X
- 기본 타입은 항상 값을 복사함
- Integer같은 래퍼 클래스나 String 같은 특수한 클래스는 공유 가능한 객체이지만 변경X

## B. 임베디드 타입
### 임베디드 타입
- 새로운 값 타입을 직접 정의할 수 있음
- JPA는 임베디드 타입(embedded type)이라 함
- 주로 기본 값 타입을 모아서 만들어서 복합 값 타입이라고도 함
- int, String과 같은 값 타입

### 임베디드 타입
- 회원 엔티티는 이름, 근무 시작일, 근무 종료일, 주소 도시, 주소 번지, 주소 우편번호를 가진다.
  ![image](https://user-images.githubusercontent.com/28394879/129580669-51d2c299-1b64-483f-8ce7-9d9b88184ef9.png)

### 임베디드 타입
- 회원 엔티티는 이름, 근무 기간, 집 주소를 가진다.
  ![image](https://user-images.githubusercontent.com/28394879/129580831-b95207a0-9bc5-4d5a-aabd-ae73b6f1d176.png)
  ![image](https://user-images.githubusercontent.com/28394879/129580975-83034e8d-3d2a-49e4-be3c-fc483ff4dffa.png)
  
### 임베디드 타입 사용법
- @Embeddable: 값 타입을 정의하는 곳에 표시
- @Embedded: 값 타입을 사용하는 곳에 표시
- 기본 생성자 필수

### 임베디드 타입의 장점
- 재사용
- 높은 응집도
- Period.isWork()처럼 해당 값 타입만 사용하는 의미 있는 메소드를 만들 수 있음
- 임베디드 타입을 포함한 모든 값 타입은, 값 타입을 소유한 엔티티에 생명주기를 의존함

### 임베디드 타입과 테이블 매핑
![image](https://user-images.githubusercontent.com/28394879/129581354-a3e10b28-27ac-4d87-8dbe-bcfd24fa8b1e.png)

### 임베디드 타입과 테이블 매핑
- 임베디드 타입은 엔티티의 값일 뿐이다.
- 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
- 객체와 테이블을 아주 세밀하게(find-grained) 매핑하는 것이 가능
- 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많음

### 임베디드 타입과 연관관계
![image](https://user-images.githubusercontent.com/28394879/129582946-42dda557-9814-4ebd-84fe-b8bb7e75580b.png)

### @AttributeOverride: 속성 재정의
- 한 엔티티에서 같은 값 타입을 사용하면?
- 컬럼 명이 중복됨
- @AttributeOverrides, @AttributeOverride를 사용해서 컬럼 명 속성을 재정의

### 임베디드 타입과 null
- 임베디드 타입의 값이 null이면 매핑한 컬럼 값은 모두 null



## C. 값 타입과 불변 객체
- 값 타입은 복잡한 객체 세상을 조금이라도 단순화하려고 만든 개념이다. 따라서 값 타입은 단순하고 안전하게 다룰 수 있어야 한다.

### 값 타입 공유 참조
- 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험함
- 부작용(side effect) 발생
  ![image](https://user-images.githubusercontent.com/28394879/132115428-b80b3446-e0f8-40f5-bbd0-01c81e25dd69.png)

### 값 타입 복사
- 값 타입의 실제 인스턴스인 값을 공유하는 것은 위험
- 대신 값(인스턴스)를 복사해서 사용
  ![image](https://user-images.githubusercontent.com/28394879/132115477-f56ec6f5-f37b-4f64-ad35-a03604d124d6.png)
  
### 객체 타입의 한계
- 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.
- 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체 타입이다.
- 자바 기본 타입에 값을 대입하면 값을 복사한다.
- 객체 타입은 참조 값을 직접 대입하는 것을 막을 방벙이 없다.
- 객체의 공유 참조는 피할 수 없다.

### 객체 타입의 한계
- 기본 타입 (primitive type)
```
int a = 10;
int b = a; // 기본 타입은 값을 복사
b = 4; // b의 값만 변경
```

- 객체 타입
```
Address a = new Address("Old");
Address b = a; // 객ㅊ레 타입은 참조를 전달
b.setCity("new"); // a,b 값 둘다 변경됨
```

### 불변 객체
- 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
- 값 타입은 불변 객체(immutable object)로 설계해야함
- 불변 객체: 생성 시점 이후 절대 값을 변경할 수 없는 객체
- 생성자로만 값을 설정하고 수정자(setter)를 만들지 않으면 됨
- 참고: Integer, String은 자바가 제공하는 대표적인 불변 객체

### 불변이라는 작은 제약으로 부작용이라는 큰 재앙을 막을 수 있다.


## D. 값 타입의 비교 

### 값 타입의 비교
- 값 타입: 인스턴스가 달라고 그 안에 값이 같으면 같은 것으로 봐야 함 
``` 값 타입이므로 a == b
int a = 10;
int b = 10;
```

``` primitive 타입 이므로 a!= b
Address a = new Address("서울시");
Address b = new Address("서울시");
```

- 동일성(identity) 비교: 인스턴스의 참조 값을 비교, == 사용
- 동등성(equivalence) 비교: 인스턴스의 값을 비교, equals() 사용
- 값 타입은 a.equals(b)를 사용해서 동등성 비교를 해야 함
- 값 타입의 equals() 메소드를 적절하게 재정의(주로 모든 필드 사용)




## E. 값 타입 컬렉션

### 값 타입 컬렉션
![image](https://user-images.githubusercontent.com/28394879/132220134-6eec296c-b6d7-4663-afac-fb625115e5db.png)
- 값 타입을 하나 이상 저장할 때 사용
- @ElementCollection, @CollectionTable 사용
- 데이터베이스는 컬렉션을 같은 테이브렝 저장할 수 없다.
- 컬렉션을 저장하기 위한 별도의 테이블이 필요함

### 값 타입 컬렉션 사용
- 값 타입 저장 예제
- 값 타입 조회 예제
    - 값 타입 컬렉션도 지연 로딩 전략 사용
- 값 타입 수정 예제 (실무에서는 값 타입 수정은 일어나면 성능에 이슈가 많으므로 쓰면 안됨) 
- 참고: 값 타입 컬렉션은 용속성 전이(Cascade) + 고아 객체 제거 기능을 필수로 가진다고 볼 수 있다. 

### 값 타입 컬렉션의 제약사항
- 값 타입은 엔티티와 다르게 식별자 개념이 없다.
- 값은 변경하면 추적이 어렵다.
- 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을
모두 다시 저장한다.
- 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야 함: null 입력X, 중복 저장X

### 값 타입 컬렉션 대안 
- 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
- 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
- 영속성 전이(Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션 처럼 사용
- EX) AddressEntity

### 정리
- 엔티티 타입의 정리
    - 식별자 O
    - 생명 주기 관리
    - 공유
- 값 타입의 특징
    - 식별자X
    - 생명 주기를 엔티티에 의존
    - 공유하지 않는 것이 안전(복사해서 사용)
    - 불변 객체로 만드는 것이 안전

- 값 타입은 정말 값 타입이라 판단될 때만 사용
- 엔티티와 값 타입을 혼동해서 엔티티를 값 타입으로 만들면 안됨
- 식별자가 필요하고, 지속해서 값을 추적, 변경해야 한다면 그것은 값 타입이 아닌 엔티티


## F. 실전 예제 6 - 값 타입 매핑
![image](https://user-images.githubusercontent.com/28394879/132521148-305bbcd4-41c3-4760-972b-b8d4749156c8.png)



# 10. 객체지향 쿼리 언어1 - 기본 문법
## A. 소개

### JPA는 다양한 쿼리 방법을 지원
- JPQL
- JPA Criteria
- QueryDSL
- 네이티브 SQL
- JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

### JPQL 소개
- 가장 단순한 조회 방법
    - EntityManager.find()
    - 객체 그래프 탐색(a.getB().getC())

- 나이가 18살 이상인 회원을 모두 검색하고 싶다면?

### JPQL
- JPA를 사용하면 엔티티 객체를 중심으로 개발
- 문제는 검색 쿼리
- 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
- 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
- 애플리케이션이 필요한 데이터만 DB에서 불려오려면 결국 검색 조건이 포함된 SQL이 필요

### JPQL
- JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
- SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
- JPQL은 엔티티 객체를 대상으로 쿼리
- SQL은 데이터베이스 테이블을 대상으로 쿼리 

```
//검색
String jpql = "select m From Member m where m.name like '%hello%'";
List<Member> result = em.createQuery(jpql, Member.class)
    .getResultList();
    
```
- 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
- SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
- JPQL을 한마디로 정의하면 객체 지향 SQL

### JPQL과 실행된 SQL
```
//검색
String jpql = "select m From Member m where m.name like '%hello%'";
List<Member> result = em.createQuery(jpql, Member.class)
    .getResultList();
```
```
실행된 SQL
select 
    m.id as id,
    m.age as age,
    m.USERNAME as USERNAME,
    m.TEAM_ID as TEAM_ID
from
    Member m
where
    m.age>18
```

### Criteria 소개
```
//Criteria 사용 준비
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Member> query = cb.createQuery(Member.class);
//루트 클래스 (조회를 시작할 클래스)
Root<Member> m = query.from(Member.class);
//쿼리 생성 
CriteriaQuery<Member> cq =
query.select(m).where(cb.equal(m.get("username"), "kim"));
List<Member> resultList = em.createQuery(cq).getResultList();
```
- 장점1: 쿼리를 잘못쓰면 컴파일 오류가 뜨게 된다.
- 장점2: 동적쿼리도 그냥 JPQL만쓰는 것 보다 훨씬 깔끔하게 나온다.
- 하지만 sql스럽지 않고 코드를 알아보기 힘들다는 단점이 있다. 
- 그러므로, 유지보수가 쉽지 않아서 ,실무에선 안쓰는게 좋다.

### Criteria 소개
- 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
- JPQL 빌더 역할
- JPA 공식 기능
- 단점: 너무 복잡하고 실용성이 없다.
- Criteria 대신에 QueryDSL 사용 권장 

### QueryDSL 소개
```
// JPQL
// select m from Member m where m.age > 18
JPAFactoryQuery query = new JPAQueryFactory(em);
QMember m = QMember.member;

List<Member> list = 
    query.selectFrom(m)
        .where(m.age.get(18))
        .orderBy(m.name.desc())
        .fetch();
```
- 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
- JPQL 빌더 역할
- 컴파일 시점에 문법 오류를 찾을 수 있음
- 동적쿼리 작성 편리함
- 단순하고 쉬움
- 실무 사용 권장

### 네이티브 SQL 소개
- JPA가 제공하는 SQL을 직접 사용하는 기능
- JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능
- 예) 오라클 CONNECT BY, 특정 DB만 사용하는 SQL 힌트

```
String sql =
 “SELECT ID, AGE, TEAM_ID, NAME FROM MEMBER WHERE NAME = ‘kim’";
List<Member> resultList =
 em.createNativeQuery(sql, Member.class).getResultList();
```

### JDBC 직접 사용, SpringJdbcTemplate 등
- JPA를 사용하면서, JDBC 커넥션을 직접 사용하거나, 스프링 JdbcTemplate, 마이바티스등을 함께 사용 가능
- 단 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요
- 예) JPA를 우회해서 SQL을 실행하기 직전에 영속성 컨텍스트 수동 플러시 


## B. 기본 문법과 쿼리 API

### JPQL 소개
- JPQL은 객체지향 쿼리 언어다. 따라서 테이블을 대상으로 쿼리 하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
- JPQL은 SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.
- JPQL은 결국 SQL로 변환된다.
  ![image](https://user-images.githubusercontent.com/28394879/132695251-7c46deef-7b74-4573-824b-fef493ffae44.png)

### JPQL 문법
- select m from Member as m where m.age > 18
- 엔티티와 속성은 대소문자 구분O (Member, age)
- JPQL 키워드는 대소문자 구부X (SELECT, FROM, where)
- 엔티티 이름 사용, 테이블 이름이 아님(Member)
- 별칭은 필수(m) (as는 생략가능)
```
select_문 :: =
 select_절
 from_절
 [where_절]
 [groupby_절]
 [having_절]
 [orderby_절]
update_문 :: = update_절 [where_절]
delete_문 :: = delete_절 [where_절]
```

### 집합과 정렬
```
select
 COUNT(m), //회원수
 SUM(m.age), //나이 합
 AVG(m.age), //평균 나이
 MAX(m.age), //최대 나이
 MIN(m.age) //최소 나이
from Member m
```
- GROUP BY, HAVING,
- ORDER BY

### TypeQuery, Query
- TypeQuery: 반환 타입이 명확할 때 사용
- Query: 반환 타입이 명확하지 않을 때 사용
```
TypeQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);

Query query = em.createQuery("SELECT m.username, m.age from Member m");
```

### 결과 조회 API
- query.getResultList(): 결과가 하나 이상일 때, 리스트 반환
    - 결과가 없으면 빈 리스트 반환 
- query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
    - 결과가 없으면: javax.persistence.NoResultException
    - 둘 이상이면: javax.persistence.NonUniqueResultException
    

### 파라미터 바인딩 - 이름 기준, 위치 기준
![image](https://user-images.githubusercontent.com/28394879/132703684-91e72ea3-202f-4c6c-8c73-d15379fc1e8f.png)
- 위치 기준 보다는 이름 기준을 쓰는게 좋다.




## C. 프로젝션(SELECT)
### 프로젝션 
- SELECT 절에 조회할 대상을 지정하는 것
- 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본 데이터 타입)
- SELECT m FROM Member m -> 엔티티 프로젝션
- SELECT m.team FROM Member m -> 엔티티 프로젝션
- SELECT m.address FROM Member m -> 임배디드 타입 프로젝션
- SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션
- DISTINCT로 중복 제거 

### 프로젝션 - 여러 값 조회
- SELECT m.username, m.age FROM Member m
- 1. Query 타입으로 조회
- 2. Object[] 타입으로 조회
- 3. new 명령어로 조회
    - 단순 값을 DTO로 바로 조회 <br>
    SELECT new jpabook.jpql.UserDTO(m.username, m.age) FROM Member m
    - 패키지 명을 포함한 전체 클래스 명 입력
    - 순서와 타입이 일치하는 생성자 필요





## D. 페이징

### 페이징 API
- JPA는 페이징을 다음 두 API로 추상화 
- setFirstResult(int startPosition): 조회 시작 위치(0부터 시작)
- setMaxResults(int maxResult): 조회할 데이터 수 

### 페이징 API 예시
```
// 페이징 쿼리
String jpql = "select m from Member m order by m.name desc";
List<Member> resultList = em.createQuery(jpql, Member.class)
    .setFirstResult(10)
    .setMaxResults(20)
    .getResultList();
```

### 페이징 API - MySQL 방언 
```
SELECT
 M.ID AS ID,
 M.AGE AS AGE,
 M.TEAM_ID AS TEAM_ID,
 M.NAME AS NAME
FROM
 MEMBER M
ORDER BY
 M.NAME DESC LIMIT ?, ?
```

### 페이징 API - Oracle 방언
```
SELECT * FROM
 ( SELECT ROW_.*, ROWNUM ROWNUM_
 FROM
 ( SELECT
 M.ID AS ID,
 M.AGE AS AGE,
 M.TEAM_ID AS TEAM_ID,
 M.NAME AS NAME
 FROM MEMBER M
 ORDER BY M.NAME
 ) ROW_
 WHERE ROWNUM <= ?
 )
WHERE ROWNUM_ > ?
```


## E. 조인
### 조인 
- 내부 조인: SELECT m FROM Member m [INNER] JOIN m.team t
- 외부 조인: SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
- 세타 조인: select count(m) from Member m, Team t where m.username = t.name

### 조인 - ON 절
- ON절을 활용한 조인(JPA 2.1 부터 지원)
    1. 조인 대상 필터링
    2. 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)
    
### 1. 조인 대상 필터링
- 예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
```
JPQL:
SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
SQL:
SELECT m.*, t.* FROM
Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='A'
```

### 2. 연관관계 없는 엔티티 외부 조인
- 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
```
JPQL:
SELECT m, t FROM
Member m LEFT JOIN Team t on m.username = t.name
SQL:
SELECT m.*, t.* FROM
Member m LEFT JOIN Team t ON m.username = t.name
```


## F. 서브 쿼리
### 서브 쿼리
- 나이가 평균보다 많은 회원<br>
`select m from Member m
  where m.age > (select avg(m2.age) from Member m2)`
- 한 건이라도 주문한 고객<br>
`select m from Member m
  where (select count(o) from Order o where m = o.member) > 0`
  
### 서브 쿼리 지원 함수
- [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참
    - {ALL | ANY | SOME} (subquery)
    - ALL 모두 만족하면 참
    - ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참
- [NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참

### 서브 쿼리 - 예제
- 팀A 소속인 회원<br>
`select m from Member m
  where exists (select t from m.team t where t.name = ‘팀A')`

- 전체 상품 각각의 재고보다 주문량이 많은 주문들
`select o from Order o
  where o.orderAmount > ALL (select p.stockAmount from Product p)`
  
- 어떤 팀이든 팀에 소속된 회원
`select m from Member m
  where m.team = ANY (select t from Team t)`
  
### JPA 서브 쿼리 한계
- JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
- SELECT 절도 가능(하이버네이트에서 지원)
- FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
    - 조이능로 풀 수 있으면 풀어서 해결
    
## G. JPQL 타입 표현과 기타식
### JPQL 타입 표현
- 문자: 'HELLO', 'She''s'
- 숫자: 10L(Long), 10D(Double), 10F(Float)
- Boolean: TRUE, FALSE
- ENUM: jpabook.MemberType.Admin (패키지명 포함)
- 엔티티 타입: TYPE(m) = Member (상속 관계에서 사용)

### JPQL 기타
- SQL과 문법이 같은 식
- EXISTS, IN
- AND, OR, NOT
- =, >, >=, <, <=, <>
- BETWEEN, LIEK, IS NULL



## H. 조건식(CASE 등등)
### 조건식 - CASE 식
- 기본 CASE 식
```
select
 case when m.age <= 10 then '학생요금'
 when m.age >= 60 then '경로요금'
 else '일반요금'
 end
from Member m
```

- 단순 CASE 식
``` 
select
 case t.name
 when '팀A' then '인센티브110%'
 when '팀B' then '인센티브120%'
 else '인센티브105%'
 end
from Team t
```

- COALESCE: 하나씩 조회해서 null이 아니면 반환
- NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환

- 사용자 이름이 없으면 이름 없는 회원을 반환<br>
`select coalesce(m.username,'이름 없는 회원') from Member m`

- 사용자 이름이 '관리자'면 null을 반환하고 나머지는 본인의 이름을 반환
`select NULLIF(m.username, '관리자') from Member m`



## I. JPQL 함수
### JPQL 기본 함수
- CONCAT
- SUBSTRING
- TRIM
- LOWER, UPPER
- LENGTH
- LOCATE
- ABS, SQRT, MOD
- SIZE, INDEX(JPA 용도)

### 사용자 정의 함수 호출
- 하이버네이트는 사용전 방언에 추가해야 한다.
    - 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다.<br>
    `select function('group_concat', i.name) from Item i`
  


# 11. 객체지향 쿼리 언어2 - 중급 문법
## A. 경로 표현식
## B. 페치 조인1 - 기본
## C. 페치 조인2 - 한계
## D. 다형성 쿼리
## E. 엔티티 직접 사용
## F. Named 쿼리
## G. 벌크 연산 