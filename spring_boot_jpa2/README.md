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

</details>



<details> <summary> 3. 주문 조회 V3: 엔티티를 DTO로 변환 - 페치 조인 최적화 </summary>

</details>



<details> <summary> 4. 주문 조회 V3.1: 엔티티를 DTO로 변환 - 페이징과 한계 돌파 </summary>

</details>



<details> <summary> 5. 주문 조회 V4: JPA에서 DTO 직접 조회 </summary>

</details>



<details> <summary> 6. 주문 조회 V5: JPA에서 DTO 직접 조회 - 컬렉션 조회 최적화 </summary>

</details>



<details> <summary> 7. 주문 조회 V6: JPA에서 DTO로 직접 조회, 플랫 데이터 최적화 </summary>

</details>


<details> <summary> 8. API 개발 고급 정리 </summary>

</details>
