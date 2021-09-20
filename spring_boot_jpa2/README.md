# [1. API 개발 기본](./1.API-develop-basic)

<details> <summary> 1. 회원 등록 API </summary>

### V1: 엔티티를 RequestBody에 직접 매핑
- 문제점
    - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
    - 엔티티에 API 검증을 위한 로직이 들어간다. (@NotEmpty 등등)
    - 실무에서는 회원 엔티티를 위한 API가 다양하게 만들어지는데, 한 엔티티에 각각의 API를 위한
    모든 요청 요구사항을 담기는 어렵다.
    - 엔티티가 변경되면 API 스펙이 변한다.
- 결론
    - API 요청 스펙에 맞추어 별도의 DTO를 파라미터로 받는다.

### V2: 엔티티 대신에 DTO를 RequestBody에 매핑
- `CreateMemberRequest`를 `Member`엔티티 대신에 RequestBody와 매핑한다.
- 엔티티와 프레젠테이션 계층을 위한 로직을 분리할 수 있다.
- 엔티티와 API 스펙을 명확하게 분리할 수 있다.
- 엔티티가 변해도 API 스펙이 변하지 않는다.

> 참고: 실무에서는 엔티티를 API 스펙에 노출하면 안된다!

</details>


<details> <summary> 2. 회원 수정 API </summary>

### 회원 수정 API
- 회원 수정도 DTO를 요청 파라미터로 매핑
- 변경 감지를 사용해서 데이터를 수정

> 오류정정: 회원 수정 API`updateMemberV2`은 회원 정보를 부분 업데이트 한다. 여기서 PUT 방식을
> 사용했는데, PUT은 전체 업데이트를 할 때 사용하는 것이 맞다. 부분 업데이트를 하려면 PATCH를 사용하거나
> POST를 사용하는것이 REST 스타일에 맞다.

</details>


<details> <summary> 3. 회원 조회 API </summary>

### 회원 조회V1: 응답 값으로 엔티티를 직접 외부에 노출
- 문제점
    - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
    - 기본적으로 엔티티의 모든 값이 노출된다.
    - 응답 스펙을 맞추기 위해 로직이 추가된다. (@JsonIgnore, 별도의 뷰 로직 등등)
    - 실무에서는 같은 엔티티에 대해 API가 용도에 따라 다양하게 만들어지는데, 한 엔티티에 각각의
    - API를 위한 프레젠테이션 응답 로직을 담기는 어렵다.
    - 엔티티가 변경되면 API 스펙이 변한다.
    - 추가로 컬렉션을 직접 반환하면 항후 API 스펙을 변경하기 어렵다.(별도의 Result 클래스 생성으로
    - 해결)
- 결론
    - API 응답 스펙에 맞추어 별도의 DTO를 반환한다

> 참고: 엔티티를 외부에 노출하지 마라!
> 실무에서는 `member` 엔티티의 데이터가 필요한 API가 계속 증가하게 된다. 어떤 API는 `name`필드가
> 필요하지만, 어떤 API는 `name`필드가 필요없을 수 있다. 결론적으로 엔티티 대신에 API 스펙에 맞는
> 별도의 DTO를 노출해야 한다.

### 회원 조회V2: 응답 값으로 엔티티가 아닌 별도의 DTO 사용
- 엔티티를 DTO로 변환해서 반환한다.
- 엔티티가 변해도 API 스펙이 변경되지 않는다.
- 추가로 Result 클래스로 컬렉션을 감싸서 향후 필요한 필드를 추가할 수 있다.

</details>


# [2. API 개발 고급](./2.API-develop-advanced-prepare)

<details> <summary> 1. API 개발 고급 소개 </summary>

- API 개발 고급 - 조회용 샘플 데이터 입력
- API 개발 고급 - 지연 로딩과 조회 성능 최적화
- API 개발 고급 - 컬렉션 조회 최적화
- API 개발 고급 - 페이징과 한계 돌파
- API 개발 고급 - OSIV와 성능 최적화

</details>

<details> <summary> 2. 조회용 샘플 데이터 입력 </summary>

**API 개발 고급 설명을 위해 샘플 데이터를 입력하자**
- userA
    - JPA1 BOOK
    - JPA2 BOOK
- userB
    - SPRING1 BOOK
    - SPRING2 BOOK

> 참고: 주문 내역 화면에서는 회원당 주문 내역을 하나만 출력했으므로 하나만 노출된다.

</details>


# [3. API 개발 고급 - 지연 로딩과 조회 성능 최적](./3.API-develop-advanced-lazy-loading-select-optimization)

<details> <summary> 1. 간단한 주문 조회 V1: 엔티티를 직접 노출 </summary>

- 주문 + 배송정보 + 회원을 조회하는 API를 만들자
- 지연 로딩 떄문에 발생하는 성능 문제를 단계적으로 해결해보자.

> 참고: 지금부터 설명하는 내용은 정말 중요하다. 실무에서 JPA를 사용하려면 100% 이해해야 한다.
> 안그러면 엄청난 시간을 날리게 된다.

### 간간단 주문 조회V1: 엔티티를 직접 노출
- 엔티티를 직접 노출하는 것은 좋지 않다 (앞장에서 이미 설명)
- `order` -> `member`와 `order` -> `address`는 지연 로딩이다. 따라서 실제 엔티티 대신에 프록시 존재
- jackson 라이브러리는 기본적으로 이 프록시 객체를 json으로 어떻게 생성해야 하는지 모름 -> 예외 발생
- `Hibernate5Mdule`을 스프링 빈으로 등록하면 해결(스프링 부트 사용중)

**Hibernate5Mdule 등록**
- `JpashopApplication(main)`에 다음 코드를 추가
    ```java
    @Bean
    Hibernate5Module hibernate5Module() {
     return new Hibernate5Module();
    }
    ```
- 기본적으로 초기화 된 프록시 객체만 노출, 초기화 되지 않은 프록시 객체는 노출 안함

> 참고: `build.gradle`에 다음 라이브러리를 추가 해야 된다.
> `implementation 'com.fasterxml.jackson.datatype:jackson-datatype-hibernate5'`


- 다음과 같이 설정하면 강제로 지연 로딩 가능
```java
@Bean
Hibernate5Module hibernate5Module() {
 Hibernate5Module hibernate5Module = new Hibernate5Module();
 //강제 지연 로딩 설정
 hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING,
true);
 return hibernate5Module;
}
```
- 이 옵션을 키면`order->member`, `member->orders` 양방향 연관관계를 계속 로딩하게 된다. 따라서 `@JsonIgnore`옵션을 한곳에 주어야 한다.

> 주의: 엔티티를 직접 노출할 때는 양방향 연관관계가 걸린 곳은 꼭! 한곳을 `@JsonIgnore`처리 해야 한다.
> 안그러면 양쪽을 서로 호출하면서 무한 루프가 걸린다.

> 참고: 앞에서 계속 강조했듯이 정말 간단한 애플리케이션이 아니면 엔티티를 API 응답으로 외부로 노출하는 것은 좋지 않다.
> 따라서 `Hibernate5Module`를 사용하기 보다는 DTO로 변환해서 반환하는 것이 더 좋은 방법이다.

> 주의: 지연로딩(LAZY)를 피하기 위해서 즉시 로딩(EAGER)으로 설정하면 안된다!
> 즉시 로딩 때문에 연관관계가 필요 없는 경우에도 데이터를 항상 조회해서 성능 문제가 발생할 수 있다. 즉시 로딩으로
> 설정하면 성능 튜닝이 매우 어려워 진다.
> 항상 지연 로딩을 기본으로 하고, 성능 최적화가 필요한 경우에는 페치 조인(fetch join)을 사용해라(V3에서 설명)


</details>

<details> <summary> 2. 간단한 주문 조회 V2: 엔티티를 DTO로 변환 </summary>

**OrderSimpleApiController - 추가**
- 엔티티를 DTO로 변환하는 일반적인 방법이다.
- 쿼리가 총 1 + N + N 실행된다. (v1과 쿼리수 결과는 같다)
    - `order`조회 1번(order 조회 결과 수가 N이 된다.)
    - `order -> member`지연 로딩 조회 N번
    - `order -> delivery` 지연 로딩 조회 N번
    - 예) order의 결과가 4개면 최악의 경우 1 + 4 + 4번 실행된다.(최악의 경우)
        - 지연로딩은 영속성 컨텍스트에서 조회하므로, 이미 조회된 경우 쿼리를 생략한다.


</details>

<details> <summary> 3. 간단한 주문 조회 V3: 엔티티를 DTO로 변환 - 페치 조인 최적화 </summary>

**OrderRepository - 추가**
- 엔티티를 페치 조인(fetch join)을 사용해서 쿼리 1번에 조회
- 페치 조인으로 `order -> member`, `order -> delivery`는 이미 조회 된 상태 이므로 지연 로딩X

</details>

<details> <summary> 4. 간단한 주문 조회 V4: JPA에서 DTO로 바로 조회 </summary>

**OrderSimpleQueryDto 리포지토리에서 DTO 직접 조회**
- 일반적인 SQL을 사용할 때 처럼 원하는 값을 선택해서 조회
- `new` 명령어를 사용해서 JPQL의 결과를 DTO로 즉시 변환
- SELECT 절에서 원하는 데이터를 직접 선택하므로 DB -> 애플리케이션 네트웍 용량 최적화(생각보다 미비)
- 리포지토리 재사용성 떨어짐, API 스펙에 맞춘 코드가 리포지토리에 들어가는 단점

### 정리
- 엔티티를 DTO로 변환하거나, DTO로 바로 조회하는 두가지 방법은 각각 장단점이 있다. 둘중 상황에 따라서
더 나은 방법을 선택하면 된다. 엔티티로 조회하면 리포지토리 재사용성도 좋고, 개발도 단순해진다.
따라서 권장하는 방법은 다음과 같다.

**쿼리 방식 선택 권장 순서**
1. 우선 엔티티를 DTO로 변환하는 방법을 선택한다.
2. 필요하면 페치 조인으로 성능을 최적화 한다. 대부분의 성능 이슈가 해결된다.
3. 그래도 안되면 DTO로 직접 조회하는 방법을 사용한다.
4. 최후의 방법은 JPA가 제공하는 네이티브 SQL이나 스프링 JDBC Template을 사용해서 SQL을 직접 사용한다.


</details>





# [4. API 개발 고급 - 컬렉션 조회 최적화](./4.API-develop-advanced-collection-select-optimization)


<details> <summary> 1. 주문 조회 V1: 엔티티 직접 노출 </summary>

- 주문내역에서 추가로 주문한 상품 정보를 추가로 조회하자.
- Order 기준으로 컬렉션인 `OrderItem`와 `Item`이 필요하다.
- 앞의 예제에서는 toOne(OntToOne, ManyToOne) 관계만 있었다.
- 이번에는 컬렉션인 일대다 관계(OneToMany)를 조회하고, 최적화하는 방법을 알아보자.

### 주문 조회 V1: 엔티티 직접 노출
- `orderItem`, `item` 관계를 직접 초기화하면 `Hibernate5Module`설정에 의해 엔티티를 JSON으로 생성한다.
- 양방향 연관관계면 무한 루프에 걸리지 않게 한곳에 `@JsonIgnore`를 추가해야 한다.
- 엔티티를 직접 노출하므로 좋은 방법은 아니다.

</details>



<details> <summary> 2. 주문 조회 V2: 엔티티를 DTO로 변환 </summary>

### 주문 조회 V2: 엔티티를 DTO로 변환
- 지연 로딩으로 너무 많은 SQL 실행
- SQL 실행 수
    - `order` 1번
    - `member`, `address` N번(order 조회 수 만큼)
    - `orderItem` N번(order 조회 수 만큼)
    - `item` N번(orderItem 조회 수 만큼)

> 참고: 지연 로딩은 영속성 컨텍스트에 있으면 영속성 컨텍스트에 있는 엔티티를 사용하고 없으면 SQL을 실행한다.
> 따라서 같은 영속성 컨텍스트에서 이미 로딩한 회원 엔티티를 추가로 조회하면 SQL을 실행하지 않는다.

</details>



<details> <summary> 3. 주문 조회 V3: 엔티티를 DTO로 변환 - 페치 조인 최적화 </summary>

### 주문 조회 V3: 엔티티를 DTO로 변환 - 페치 조인 최적화
- 페치조인으로 SQL이 1번만 실행됨
- `distinct`를 사용한 이유는 1대다 조인이 있으므로 데이터베이스 row가 증가한다. 그 결과 같은 order엔티티의
조회 수도 증가하게 된다. JPA의 distinct는 SQL에 distinct를 추가하고, 더해서 같은 엔티티가 조회되면, 애플리케이션에서
중복을 걸러준다. 이 예에서 order가 컬렉션 페치 조인 때문에 중복 조회 되는 것을 막아준다.
- 단점
    - 페이징 불가능(OneToMany, ManytoMany 상황에서)

> 참고: 컬렉션 페치 조인을 사용하면 페이징이 불가능하다. 하이버네이트는 경로 로그를 남기면서
> 모든 데이터를 DB에서 읽어오고, 메모리에서 페이징 해버린다.(매우 위험하다). 자세한 내용은 자바 ORM 표준 JPA 프로그래밍의 페치 조인 부분을 참고하자.

> 참고: 컬렉션 페치 조인은 1개만 할 수 있다. 컬렉션 둘 이상에 페치 조인을 사용하면 안된다. 데이터가 부정합하게 조회될 수 있다.
> 자세한 내용은 자바 ORM 표준 JPA 프로그래밍을 참고하자.

</details>



<details> <summary> 4. 주문 조회 V3.1: 엔티티를 DTO로 변환 - 페이징과 한계 돌파 </summary>

- 컬렉션을 페치 조인하면 페이징이 불가능하다.
  - 컬렉션을 페치 조인하면 일대다 조인이 발생하므로 데이터가 예측할 수 없이 증가한다.
  - 일대다에서 일(1)을 기준으로 페이징을 하는 것이 목적이다. 그런데 데이터는 다(N)를 기준으로 row가 생성된다.
  - Order를 기준으로 페이징 하고 싶은데, 다(N)인 OrderItem을 조인하면 OrderItem이 기준이 되어버린다.
  - (더 자세한 내용은 자바 ORM 표준 JPA 프로그래밍 - 페치 조인 한계 참조)
- 이 경우 하이버네이트는 경고 로그를 남기고 모든 DB 데이터를 읽어서 메모리에서 페이징을 시도한다. 최악의 경우 장애로 이어질 수 있다. 

### 한계 돌파 
- 그러면 페이징 + 컬렉션 엔티티를 함꼐 조회하려면 어떻게 해야할까?
- 지금부터 코드도 단순하고, 성능 최적화도 보장하는 매우 강력한 방법을 소개하겠다. 대부분의 페이징 + 컬렉션 엔티티 조회 문제는 이 방법으로 해결 할 수 있다. (딱히 다른 해결 방법은 없다)
- 먼저 **ToOne**(OneToOne, ManyToOne) 관계를 모두 페치조인 한다. ToOne 관계는 row수를 증가시키지 않으므로 페이징 쿼리에 영향을 주지 않는다.
- 컬렉션은 지연 로딩으로 조회한다.
- 지연 로딩 성능 최적화를 위해 `hibernate.default_batch_fetch_size`, `@BatchSize`를 적용한다.
  - hibernate.default_batch_fetch_szie: 글로벌 설정
  - @BatchSize: 개발 최적화
  - 이 옵션을 사용하면 컬렉션이나, 프록시 객체를 한꺼번에 설정한 size 만큼 IN 쿼리로 조회한다. 
  - 필드나 테이블 개별로 설정하려면 `@BatchSize`를 적용하면 된다.(컬렉션은 컬렉션 필드에, 엔티티는 엔티티 클래스에 적용)

- 장점
  - 쿼리 호출 수가 `1+N` - > `1+1`로 최적화 된다.
  - 조인보다 DB 데이터 전송량이 최적화 된다. (Order와 OrderItem을 조인하면 Order가 OrderItem 만큼 중복해서 조회된다. 이 방법은 각각 조회하므로 전송해야할 중복 데이터가 없다.)
  - 페치 조인 방식과 비교해서 쿼리 호출 수가 약간 증가하지만, DB 데이터 전송량이 감소한다. 
  - 컬렉션 페치 조인은 페이징이 불가능 하지만 이 방법은 페이징이 가능하다.
- 결론
  - ToOne 관계는 페치 조인해도 페이징에 영향을 주지 않는다. 따라서 ToOne 관계는 페치조인으로 쿼리 수를 줄이고 해결하고, 나머지는 `hibernate.default_batch_fetch_size`로 최적화 하자. 

> 참고: `default_batch_fetch_size`의 크기는 적당한 사이즈를 골라야 하는데, 100 ~ 1000 사이를 선택하는 것을 권장한다. 
> 이 전략을 SQL IN절을 사용하는데, 데이터베이스에 따라 IN 절 파라미터를 1000으로 제한하기도 한다. 
> 1000으로 잡으면 한번에 1000개를 DB에서 애플리케이션에 불러오므로 DB에 순간 부하가 증가할 수 있다.
> 하지만, 애플리케이션은 100이든 1000이든 결국 전체 데이터를 로딩해야 하므로 메모리 사용량이 같다.
> 1000으로 설정하는 것이 성능상 가장 좋지만, 결국 DB든 애플리케이션이든 순간 부하를 어디까지 견딜 수 있는지로 결정하면 된다.

</details>



<details> <summary> 5. 주문 조회 V4: JPA에서 DTO 직접 조회 </summary>

### OrderItemQueryDto
- Query: 루트 1번, 컬렉션 N번 실행 
- ToOne(N:1, 1:1) 관계들을 먼저 조회하고, ToMany(1:N) 관계는 각각 별도로 처리한다.
  - 이런 방식을 선택한 이유는 다음과 같다.
  - ToOne 관계는 조인해도 데이터 row수가 증가하지 않는다.
  - toMany(1:N) 관계는 조인하면 row수가 증가한다.
- row 수가 증가하지 않는 ToOne 관계는 조인으로 최적화 하기 쉬우므로 한번에 조회하고, ToMany 관계는
최적화 하기 어려우므로 `findOrderItems()`같은 별도의 메서드로 조회한다.

</details>



<details> <summary> 6. 주문 조회 V5: JPA에서 DTO 직접 조회 - 컬렉션 조회 최적화 </summary>

### JPA에서 DTO 직접 조회 - 컬렉션 조회 최적화
- Query: 루트 1번, 컬렉션 1번 실행
- ToOne 관계들을 먼저 조회하고, 여기서 얻은 식별자 orderId로 ToMany 관계인 `OrderItem`을 한꺼번에 조회
- MAP을 사용해서 매칭 성능 향상(O(1))

</details>



<details> <summary> 7. 주문 조회 V6: JPA에서 DTO로 직접 조회, 플랫 데이터 최적화 </summary>

### JPA에서 DTO로 직접 조회, 플랫 데이터 최적화
- Query: 1번
- 단점
  - 쿼리는 한번이지만 조인으로 인해 DB에서 애플리케이션에 전달하는 데이터에 중복 데이터가 추가되므로 상황에 따라 V5 보다 더 느릴 수도 있다.
  - 으팰리케이션에서 추가 작업이 크다.
  - 페이징 불가능 
  
</details>


<details> <summary> 8. API 개발 고급 정리 </summary>

### API 개발 고급 정리
**정리**
- 엔티티 조회
  - 엔티티를 조회해서 그대로 반환: V1
  - 엔티티 조회 후 DTO로 변환: V2
  - 페치 조인으로 쿼리 수 최적화: V3
  - 컬렉션 페이징과 한계 돌파: V3.1
    - 컬렉션은 페치 조인시 페이징이 불가능
    - ToOne 관계는 페치 조인으로 쿼리 수 최적화
    - 컬렉션은 페치 조인 대신에 지연 로딩을 유지하고, `hibernate.default_batch_fetch_size`, `@BatchSize`로 최적화
- DTO 직접 조회
  - JPA에서 DTO를 직접 조회: V4
  - 컬렉션 조회 최적화 - 일대다 관계인 컬렉션은 IN 절을 활용해서 메모리에 미리 조회해서 최적화: V5
  - 플랫 데이터 최적화 - JOIN 결과를 그대로 조회 후 애플리케이션에서 원하는 모양으로 직접 변환: V6

**권장 순서**
1. 엔티티 조회 방식으로 우선 접근 
  1. 페치조인으로 쿼라 수를 최적화
  2. 컬렉션 최적화
    1. 페이징 필요 `hibernate.default_batch_fetch_size`, `@BatchSize`로 최적화
    2. 페이징 필요 X -> 페치 조인 사용
2. 엔티티 조회 방식으로 해결이 안되면 DTO 조회 방식 사용
3. DTO 조회 방식으로 해결이 안되면 NativeSQL or 스프링 jdbcTemplate

> 참고: 엔티티 조회 방식은 페치 조인이나, `hibernate.default_batch_fetch_size`, `@BatchSize` 같이
> 코드를 거의 수정하지 않고, 옵션만 약간 변경해서, 다양한 성능 최적화를 시도할 수 있다. 반면에 DTO를  
> 직접 조회하는 방식은 성능을 최적화 하거나 성능 최적화 방식을 변경할 때 많은 코드를 변경해야 한다. 
 
> 참고: 개발자는 성능 최적화와 코드 복잡도 사이에서 줄타기를 해야 한다. 항상 그런 것은 아니지만, 보통
> 성능 최적화는 단순한 코드를 복잡한 코드로 몰고간다.
> 엔티티 조회 방식은 JPA가 많은 부분을 최적화 해주기 때문에, 단순한 코드를 유지하면서, 성능을 최적화 할 수 있다.
> 반면에 DTO 조회 방식은 SQL을 직접 다루는 것과 유사하기 때문에, 둘 사이에 줄타기를 해야 한다.

**DTO 조회 방식의 선택지**
- DTO로 조회하는 방법도 각각 장단이 있다. V4, V5, V6에서 단순하게 쿼리가 1번 실행된다고 V6이 항상 좋은 방법인 것은 아니다.
- V4는 코드가 단순하다. 특정 주문 한건만 조회하면 이 방식을 사용해도 성능이 잘 나온다. 예를 들어서 
조회한 Order 데이터가 1건이면 OrderItem을 찾기 위한 쿼리도 1번만 실행하면 된다.
- V5는 코드가 복잡하다. 여러 주문을 한꺼번에 조회하는 경우에는 V4 대신에 이것을 최적화한 V5 방식을 사용해야 한다.
예를 들어서 조회한 Order 데이터가 1000건인데, V4 방식을 그대로 사용하면, 쿼리가 총 1 + 1000번 실행된다.
여기서 1은 Order 를 조회한 쿼리고, 1000은 조회한 Order의 row 수다. V5 방식으로 최적화 하면 쿼리가
총 1 + 1 번만 실행된다. 상황에 따라 다르겠지만 운영 환경에서 100배 이상의 성능 차이가 날 수 있다.
- V6는 완전히 다른 접근방식이다. 쿼리 한번으로 최적화 되어서 상당히 좋아보이지만, Order를 기준으로 페이징이 불가능하다.
실무에서는 이정도 데이터면 수백이나, 수천건 단위로 페이징 처리가 꼭 필요하므로, 이 경우 선택하기 어려운 방법이다.
그리고 데이터가 많으면 중복 전송이 증가해서 V5와 비교해서 성능차이도 미비하다. 

</details>



# [5. API 개발 고급 - 실무 필수 최적화](./5.API-develop-advanced-practical-essential-optimization)

<details> <summary> 1. OSIV와 성능 최적화 </summary>

## OSIV와  성능 최적화
- Open SEssion In View: 하이버네이트
- Open EntityManager In View: JPA <br>
(관례상 OSIV라 한다.)

### OSIV ON
![image](https://user-images.githubusercontent.com/28394879/134022144-fa3d5154-9143-46b5-b0e9-8b8a927ecb8e.png)

- `spring.jpa.open-in-view`: true 기본값

- 이 기본값을 뿌리면서 애플리케이션 시작 시점에 warn 로그를 남기는 것은 이유가 있다.
- OSIV 전략은 트랜잭션 시작처럼 최초 데이터베이스 커넥션 시작 시점부터 API 응답이 끝날 때 까지 영속성 컨텍스트와 데이터베이스 커넥션을 유지한다. 그래서 지금까지 View Template이나 API 컨트롤러에서 지연 로딩이 가능 했던 것이다.
- 지연 로딩은 영속성 컨텍스트가 살아 있어야 가능하고, 영속성 컨텍스트는 기본적으로 데이터베이스 커넥션을 유지한다. 이것 자체가 큰 장점이다.
- 그런데 이 전략은 너무 오랜시간동안 데이터베이스 커넥션 리소스를 사용하기 떄문에, 실시간 트래픽이 중요한 애플리케이션에서는 커넥션이 모자랄 수 있다. 이것은 결국 장애로 이어진다.
- 예를 들어서 컨트롤러에서 외부 API를 호출하면 외부 API 대기 시간 만큼 커넥션 리소르르 반환하지 못하고, 유지 해야 한다. 

### OSIV OFF
![image](https://user-images.githubusercontent.com/28394879/134023402-46e5c4fd-76df-4310-92fe-f1ac3613a389.png)

- `spring.jpa.open-in-view: false` OSIV 종료

- OSIV를 끄면 트랜잭션을 종료할 때 영속성 컨텍스트를 닫고, 데이터베이스 커넥션도 반환한다. 따라서 커넥션 리소스를 낭비하지 않는다.
- OSIVE를 끄면 모든 지연로딩을 트랜잭션 안에서 처리해야 한다. 따라서 지금까지 작성한 많은 지연 로딩 코드를 트랜잭션 안으로 넣어야 하는 단점이 있다. 그리고 view template에서 지연로딩이 동작하지 않는다. 
- 결론적으로 트랜잭션이 끝나기 전에 지연 로딩을 강제로 호출해 두어야 한다. 

### 커멘드와 쿼리 분리
- 실무에서 OSIV를 끈 상태로 복잡성을 관리하는 좋은 방법이 있다. 따로 Command와 Query를 분리하는 것이다. 
- 참고: https://en.wikipedia.org/wiki/Command%E2%80%93query_separation
- 보통 비즈니스 로직은 특정 엔티티 몇개를 등록하거나 수정하는 것이므로 성능이 크게 문제가 되지 않는다.
- 그런데 복잡한 화면을 출력하기 위한 쿼리는 화면에 맞추어 성능을 최적화 하는 것이 중요하다.
- 하지만 그 복잡성에 비햇 핵심 비즈니스에 큰 영향을 주는 것은 아니다.
- 그래서 크고 복잡한 애플리케이션을 개발한다면, 이 둘의 관심사를 명확하게 분리하는 선택은 유지보수 관점에서 충분히 의미 있다.
- 단순하게 설명해서 다음처럼 분리하는 것이다.

- OrderService
  - OrderService: 핵심 비즈니스 로직
  - OrderQueryService: 화면이나 API에 맞춘 서비스 (주로 읽기 전용 트랜잭션 사용)
  ```java
     @Service
     @RequiredArgsConstructor
     @Tranactional(readOnlay = true)
     public class OrderQueryClass {
       private final OrderRepository orderRepository;

       public List<OrderDto> ordersV3() { // Controller에서 이 ordersV3를 그대로 쓸 수 있도록 하면 OSIV를 끄고도 잘 동작할 수 있게 된다. (Transactional인 Service에서 lazyLoading했기 떄문)
         List<Order> orders = orderRepository.findAllWithItem();

         List<OrderDto> result = orders.stream()
          .map(o -> new OrderDto(o))
          .collect(toList());
          return result;
       }
     }
  ``` 
- 보통 서비스 계층에서 트랜잭션을 유지한다. 두 서비스 모두 트랜잭션을 유지하면서 지연 로딩을 사용할 수 있다.

> 참고: 고객 서비스의 실시간 API는 OSIV를 끄고, ADMIN 처럼 커넥션을 많이 사용하지 않는 곳에서는 OSIV를 켜는것이 좋다.

> 참고: OSIV에 관해 더 깊이 알고 싶으면 자바 ORM 표준 JPA 프로그래밍 13장 웹 애플리케이션과 영속성 관리를 참고하자. 

</details>