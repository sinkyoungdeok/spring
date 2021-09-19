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