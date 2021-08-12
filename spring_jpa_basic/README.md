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
## C. 일대일 [1:1]
## D. 다대다 [N:M]
## E. 실전 예제 3 - 다양한 연관관계 매핑

# 7. 고급 매핑
# 8. 프록시와 연관관계 관리
# 9. 값 타입
# 10. 객체지향 쿼리 언어1 - 기본 문법
# 11. 객체지향 쿼리 언어2 - 중급 문법