## 스프링 공부 하면서 정리
1. [Spring Boot JWT Tutorial](./spring-boot-jwt-tutorial)
2. [코틀린 마이크로서비스 개발](./kotlin-microservice-development)
3. [RxJava 리액티브 프로그래밍](./rxJava-reactive-programming)
4. [스프링 인 액션](./spring-in-action)

# 스프링 용어 및 개념 정리 
## REST란
- 자원을 이름으로 구분하여 해당 자원의 상태(정보)를 주고 받는 모든 것을 의미 한다.
- HTTP URI를 통해 자원을 명시하고, HTTP Method(POST,GET,PUT,DELETE)를
통해 해당 자원에 대한 CRUD Operation을 적용하는 것을 의미
### REST의 장단점
- 장점
    - HTTP 프로토콜의 인프라를 그대로 사용하므로 REST API 사용을 위한 별도의 인프라를 구축할
    필요가 없다.
    - HTTP 프로토콜의 표준을 최대한 활용하여 여러 추가적인 장점을 함께 가져갈 수 있게 해준다.
    - HTTP 표준 프로토콜에 따르는 모든 플랫폼에서 사용이 가능하다.
    - Hypermedia API의 기본을 충실히 지키면서 범용성을 보장한다.
    - REST API 메시지가 의도하는 바를 명확하게 나타내므로 의도하는 바를 쉽게 파악할 수 있다.
    - 여러가지 서비스 디자인에서 생길 수 있는 문제를 최소화한다.
    - 서버와 클라이언트의 역할을 명확하게 분리한다.
- 단점
    - 표준이 존재하지 않는다.
    - 사용할 수 있는 메소드가 4가지 밖에 없다.
        - HTTP Method 형태가 제한적이다.
    - 브라우저를 통해 테스트할 일이 많은 서비스라면 쉽게 고칠 수 잇는 URL보다 Header 값이 
    더 어렵게 느껴진다.
    - 구형 브라우저가 아직 제대로 지원해주지 못하는 부분이 존재한다.
        - PUT, DELETE를 사용하지 못하는 점
        - pustState를 지원하지 않는 점
- 필요성
    - 애플리케이션 분리 및 통합
    - 다양한 클라이언트의 등장
    - 최근의 서버 프로그램은 다양한 브라우저와 안드로이드폰, 아이폰과 같은 모바일 디바이스에서도 통신을 할 수 있어야 한다.
    - 멀티 플랫폼에 대한 지원을 위해 서비스 자원에 대한 아키텍처를 세우고 이용하는 방법을 모색한 결과, REST에 관심을 가지게 되었다.
## REST API 란
- API
    - 데이터와 기능의 집합을 제공하여 컴퓨터 프로그램간 상호작용을 촉진하며, 서로 정보를 교환가능 하도록 하는 것
- REST API
    - REST기반으로 서비스 API를 구현한 것
- RESTful 
    - REST API를 제공하는 웹 서비스


## OOP
- 데이터를 객체로 취급하여 프로그램에 반영하고, 순차적으로 프로그램이 동작하는 것과는 다르게 객체의 상호작용을 통해 프로그램이 동작하는 것
- 객체를 연결시켜 프로그래밍하면 상속, 캡슐화, 다형성을 이용해 코드 재사용성을 증가시키고, 유지보수를 감소시켜주는 장점이 있다.

## 자바의 메모리 영역
- 메서드 영역
  - static 변수, 전역 변수, 코드에서 사용되는 클래스 정보들이 올라감. 코드에서 사용되는 클래스들을 로더가 읽고 클래스 별로 분류해서 저장.
- 스택
  - 지역 변수, 메서드 등이 할당되는 LIFO 방식의 메모리
- 힙
  - new 연산자를 통해 동적할당된 객체들이 저장되고, 메모리는 가비지 컬렉션에 의해 관리됨.

## 인터페이스
- 모든 메서드가 추상 메서드로 이루어진 클래스
- 선언한 변수는 자동으로 final static 키워드가 붙음.
- 객체를 어떻게 구성해야 하는지 정리한 설계도

## 메모리 상수풀 영역
- 힙 영역에서 생성되고 자바 프로세스 종료까지 계속 유지되는 메모리 영역
- 기본적으로는 JVM에서 관리
- 프로그래머가 작성한 상수에 대해 최우선으로 찾아보고 없으면 상수풀에 추가한 후 그 주소값을 리턴 ( 이로써 메모리 절약 효과 )

## jdbc
- 자바에서 데이터베이스를 접속할 수 있도록 해주는 자바 API
- 데이터베이스 자료를 쿼리하거나 업데이트 하는 방법들을 제공

## 직렬화
- 자바에서 입출력에 사용되는 것은 스트림이라는 데이터 통로를 통해 이동하는데, 객체는 바이트형이 아니기 때문에 
스트림을 통해서 저장되거나 네트워크로 전송하는 것이 불가능하다. 따라서 객체를 스트림으로 입출력하기 위해서 바이트 배열로
변환하는 것을 직렬화 라고 함.
- 반대로 스트림으로 받은 직렬화된 객체를 다시 원래로 돌리는 건 역직렬화 라고 함.

## serialVersionUID를 선언하는 이유
- JVM은 직렬화나 역직렬화를 하는 시점의 클래스에 대해 version 번호를 부여.
- 그런데 이 시점에서 클래스의 정의가 바뀌게 되면, version 번호도 새롭게 할당해주어야함
- 직렬화와 역직렬화의 version 번호가 서로 다르면 안되기 때문에 serialVersionUID를 선언해서 문제를 해결
- 즉, 클래스 버전이 맞는지 확인하기 위한 용도로 사용

## 자바의 클래스 맴버 변수 초기화 순서
- static 변수 선언부는 클래스가 로드 될 때 변수가 제일 먼저 초기화 됨.
- 필드 변수 선언부는 객체가 생성될 때 힙 메모리에 올라가고 생성자 block보다 앞서 초기화됨.
- 생성자 block은 객체가 생성 될 때 마찬가지로 heap 메모리에 올라가는데, 이때 필드 변수가 초기화 될 때까지 
JVM에서 내부적으로 로킹해준다.
  
## Servlet vs JSP
- Servlet은 자바 언어로 웹 개발을 하기 위해 만들어진 것으로, 컨테이너가 이해할 수 있도록 순수 자바 코드로만 이루어져 있음.
- JSP는 html 기반에 자바 코드를 블록화하여 삽입한 것으로 서블릿을 좀 더 쉽게 접근할 수 있도록 만들어 짐

## 제네릭
- 클래스를 선언할 때 타입을 결정하지 않고 객체 생성 시 유동적으로 재사용하기 위한 것
- 제네릭을 활용하면 따로 형 변환할 필요가 없고, 타입 에러가 발생하지 않기 떄문에 유용하게 사용할 수 있다.

## 컬렉션 클래스에서 제네릭을 사용하는 이유
- 컬랙션 클래스에서 제네릭을 사용하면, 컴파일러는 특정한 타입만 포함될 수 있도록 컬렉션을 제한
- 컬렉션 클래스에 저장되는 인스턴스 타입을 제한하여 런타임에 발생할 수 있는 잠재적인 모든 예외를 컴파일 타임에 
잡아낼 수 있도록 도와주기 때문에 사용
  
## pojo
- 오래된 방식의 자바 오브젝트의 줄임말
- 특정한 자바 모델이나 기능, 프레임워크 등을 따르지 않는 자바 오브젝트

## 박싱과 언박싱
- 박싱: 원시형을 Wrapper Class로 변환
- 언박싱: Wrapper Class를 원시형으로 변환

## 데드락
- 둘 이상의 쓰레드가 lock을 획득하기 위해 기다리는데, 이 lock을 잡고 있는 스레드도 똑같이 다른 lock을 기다리면서 서로
블락 상태에 놓이는 것
- 다수의 스레드가 같은 lock을 동시에 다른 명령에 의해 획득하려 할 때 발생할 수 있음
- 해결방법
  - 우선 순위를 선정해 자원을 선점하도록 함
  - 공유 불가능한 상호 배제 조건을 제거

## 상속 vs 컴포지션(합성)
- 상속
  - 관계
  - 클래스를 확장해 부모 클래스에서 속성 및 동작을 물려 받음(상속)
- 컴포지션
  - 객체들 간의 관계를 가짐
  - 클래스가 구성원 데이터로 다른 클래스의 객체를 포함할 수 있는 능력
  
## JVM 역할
- 자바를 실행하기 위한 가상 기계
- 자바의 바이트 코드를 운영체제에 맞게 해석
- 자바 컴파일러가 .java 파일을 컴파일 하면, .class라는 자바 바이트 코드로 변환 시켜줌
이때, 바이트 코드가 기계어가 아니기 때문에 운영체제에서 바로 실행이 되지 않는데, 이떄 운영체제가 이해할 수 있도록 해석해주느 것이 JVM 이다.

## interface vs abstract
- interface
  - 일종의 추상 클래스이며, 추상 메서드와 상수만 멤버로 갖는다.
  - 상속의 관계가 없는 클래스간 서로 공통되는 로직을 구현하여 쓸 수 있다.
- abstract
  - 추상메서드를 하나 이상 가진 클래스
  - 자신의 생성자로 객체 생성이 불가능
  - 하위 클래스를 참조해 상위 클래스의 객체를 생성하여 하위 클래스를 제어하기 위해 사용

## 다형성
- 하나의 메소드나 클래스가 다양한 방법으로 동작 
- 하나의 객체가 여러 가지 타입을 가질 수 있는 것을 의미

## 스프링
- 자바 플랫폼을 위한 오픈소스 애플리케이션 프레임워크
- 자바 SE로 된 자바 객체 POJO를 자바 EE에 의존적이지 않게 연결해주는 역할
- 크기와 부하 측면에서 경량 시킨 것과, IOC 기술로 애플리케이션의 느슨한 결합을 도모시킴

## MVC패턴
- 코드의 재사용에 유용하며, 사용자 인터페이스와 응용 프로그램 개발에 소요되는 시간을 줄여주는 효과적인 설계 방식
- 모델은 핵심적인 비즈니스 로직을 담당하여 데이터베이스를 관리하는 부분
- 뷰는 사용자에게 보여주는 화면
- 컨트롤러는 모델과 뷰 사이에서 정보 교환을 할 수 있도록 연결시켜주는 역할

## 스프링의 AOP
- 관점 지향 프로그래밍의 약자
  - 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화 하겠다는 것
- 코드들을 부분적으로 나누어서 모듈화
- 소스 코드 상에서 다른 부분에 계속 반복해서 쓰는 코드들을 발견할 수 있는데 이것을 Aspect로 모듈화하고 핵심적인 비즈니스
로직에서 분리하여 재사용하겠다는것이 AOP의 취지
- 기존의 OOP에서 기능별로 클래스를 분리 했음에도 불구하고, 여전히 로그나 트랜잭션, 자원해제, 성능테스트 메서드처럼 공통적으로
반복되는 중복코드가 발생하는 단점이 생긴다. 이를 해결할 수 있도록 개발 코드에서는 비즈니스 로직에 집중하고, 실행 시 비즈니스
로직의 앞과 뒤에서 원하는 지점에 해당 공통 관심사를 수행할 수 있게 하면서 중복 코드를 줄일 수 있는 방식이 AOP 방식


## 스프링 DI
- 의존성 주입
- 객체들 간의 의존성을 줄이기 위해 사용되는 스프링의 IOC 컨테이너의 구체적인 구현 방식
- 개발코드 부분에서 객체를 생성하는 것이 아니라, 데이터 주입만 담당하는 별도의 공간에서 객체를 생성하고, 
데이터간의 의존성을 주입해 개발코드에서 가져다 쓰면서 의존성을 줄이는 방식
  
## 스프링 빈
- Spring IoC컨테이너가 관리하는 자바 객체

## IoC Container
- IoC: 제어의 역전
    - 인스턴스의 생성부터 소멸까지 개발자가 아닌 컨테이너가 대신 관리해주는 것
    - 인스턴스 생성의 제어를 서블릿과 같은 bean을 관리해주는 컨테이너가 관리
- IoC Container: 모든 작업을 사용하는 쪽에서 제어하게 되면서 IoC컨테이너에서 제어하게 되는데, 기본적으로 컨테이너는 객체를 생성하고 객체간의 
의존성을 이어주는 역할을 한다.
  
## MVC1 vs MVC2
- 모델1
  - JSP페이지 안에서 로직 처리를 위해 자바 코드가 함께 사용
  - 요청이 오면, 직접 자바빈이나 클래스를 이용해 작업을 처리하고, 이를 클라이언트에 출력
  - 구조가 단순한 장점이 있지만, JSP내에서 Html 코드와 자바 코드가 같이 사용되면서 복잡해지고 유지보수가 어렵다는 단점이 있다.
- 모델2
  - 모든 처리를 JSP에서만 담당하는 것이 아니라 서블릿을 만들어 역할 분담을 하는 패턴
  - 요청 결과를 출력해주는 뷰만 JSP가 담당하고, 흐름을 제어해주고 비즈니스 로직에 해당하는 컨트롤러의 역할을 서블릿이 담당
  - 역할을 분담하면서 유지보수가 용이해지는 장점이 있지만 습득하기 힘들고 구조가 복잡해지는 단점이 있다

## 스프링 vs 스프링 부트
- 스프링에서 사용하는 프로젝트를 간편하게 셋업할 수 있는 서브 프로젝트
- 독립 컨테이너에서 동작할 수 있기 때문에 임베디드 톰켓이 자동으로 실행

## 스프링 MVC 구조 흐름
1. 디스패처 서블릿이 클라이언트로 부터 요청을 받음
2. 이를 요청할 핸들러 이름을 알기 위해 핸들러맵핑에게 물어봄
3. 핸들러 맵핑은 요청 url을 보고 핸들러 이름을 디스패처 서블릿에게 알려줌
4. 이때 핸들러를 실행하기 전/후에 처리할 것들을 인터셉터로 만들어줌
5. 디스패처 서블릿은 해당 핸들러에게 제어권을 넘겨주고, 이 핸들러는 응답에 필요한 서비스를 호출하고
렌더링해야 하는 뷰 이름을 판단하여 디스패처 서블릿에게 전송
6. 디스패처 서블릿은 받은 뷰 이름을 뷰 리졸버에게 전달해 응답에 필요한 뷰를 만들라고 명령
7. 뷰가 디스패처 서블릿에게 받은 모델과 컨트롤러를 활용해 원하는 응답을 생성해서 다시 보내줌
8. 디스패처 서블릿이 뷰로부터 받은 것을 클라이언트에게 응답

## DAO 와 DTO
- DAO
  - 데이터베이스의 데이터에 접근하기 위한 객체
  - 데이터베이스에 접근을 하기 위한 로직과 비즈니스 로직을 분리하기 위해서 사용
  - 데이터베이스를 사용해 데이터베이스를 조회하거나 조작하는 기능을 전담하도록 만든 객체
- DTO
  - 계층간 데이터 교환을 위한 자바 beans
  - 계층은 컨트롤러, 뷰, business layer, persistent layer
  - VO도 같은 기능을 하지만 readonly 속성을 가진 차이가 있다.
  
## JPA N+1 문제
- 원인
  - 1:N 관계를 갖는 엔티티의 하위 엔티티를 조회할 때마다 많은 양의 쿼리가 발생
  - Spring Data JPA에서 제공하는 Repository의 findAll, findById 등과 같은 메소드를 사용하면 바로 DB에
  SQL쿼리를 날리는 것이 아니다. JPQL 이라는 객체지향 쿼리 언어를 생성, 실행시킨 후 JPA는 이것을 분석해서 SQL을 생성, 실생하는 동작에서 N+1 쿼리 문제가 발생
  - JPQL 입장에서는 Lazy 로딩, Eager 로딩과 같은 글로벌 패치 전략을 신경 쓰지 않고, JPQL만 사용해서 SQL을 생성
- 발생시점
  - Eager 전략으로 데이터를 가져오는 경우
  - Lazy 전략으로 데이터를 가져온 이후에 가져온 데이터에서 하위 엔티티를 다시 조회한느 경우
- 해결방법
  - 패치 조인
    - 미리 쿼리로 테이블을 조인해서 가져오기 때문에 Lazy, Eager 두개의 전략에 해당되는 해결방법이다.
    - 단점
      - JPA가 제공하는 Pageable 기능 사용 불가
      - 1:N 관계가 2개인 엔티티를 패치 조인 사용 불가 ( 임시 해결법은 List -> Set으로 자료구조를 변경하는 것 )
      - 데이터가 많아지면, 메모리 초과 예외가 발생할 수 있음
  - Batch Size 지정 + 즉시로딩 
    - 설정한 Size만큼 데이터를 미리 로딩 ( where in을 사용하여 )
    - JPA의 페이징 API 기능처럼 개수가 고정된 데이터를 가져올 때 함께 사용할 때 유용하게 사용 가능
    - 단점
      - 글로벌 패치 전략을 Eager로 변경해야함
  - @EntityGraph 사용

## Service에 @Transactional
- 미적용 
  - 기본적으로 JDBC의 트랜잭션은 하나의 Connection Instance를 생성하고 통신하며 종료하는 흐름과 같이 동작
  - 즉 코드에 존재하는 DAO 로직들은 각각의 트랜잭션 안에서 연산을 진행, 이떄의 트랜잭션을 로컬 트랜잭션이라고 함
- 적용
  - 여러 질의를 포함하는 트랜잭션을 구성하기 위해서 하나의 커넥션을 생성하고 Auto-commit을 false처리한 뒤 이 커넥션을 재사용
  - Spring에서는 이를 구현하는 방법을 Transaction Synchronization 이라고 함

## Transaction Synchronization
1. DAO의 호출을 위한 connection을 트랜잭션 경계 상단에서 생성
2. 해당 connection 객체를 TransactionSynchronizationManager 내부의 참조 변수인 connectionHolder 객체에 저장
3. connection의 Auco-commit 설정 값을 false로 설정
4. DAO의 메서드가 호출되면 우선 Manager 내부의 Holder 객체에 connection이 있는지 확인
5. 저장되어 있는 Connection을 가져오고 Statement 객체를 생성하여 쿼리를 전송. 그리고 연산 종료 시 해당 connection을 종료시키지 않고 열어둠
6. 위와 같은 연산을 진행하여 Runtime Exception이 발생하면 connection 객체의 RollBack을 실행하고 그렇지 않은 경우 commit을 실행

## 트랜잭션
- 데이터베이스의 상태를 변화시키기 위해서 수행하는 작업 단위

## 트랜잭션 격리 수준 
- 동시에 여러 트랜잭션이 처리 될 때 특정 트랜잭션이 다른 트랜잭션에서 변경하거나 조회하는 데이터를 볼 수 있도록 허용할지 말지를 결정
  - READ UNCOMMITTED
  - READ COMMITED
  - REPEATABLE READ
  - SERIALIZABLE

### READ UNCOMMITTED
- 각 트랜잭션에서의 변경 내용이 COMMIT 이나 ROLLBACK 여부에 상관 없이 다른 트랜잭션에서 값을 읽을 수 있다.
- 정합성에 문제가 많은 격리 수준이기 떄문에 사용하지 않는 것을 권장
- 문제점
  - DIRTY READ 현상 발생
    - 트랜잭션 작업이 완료되지 않았는데도 다른 트랜잭션에서 볼 수 있게 되는 현상

### READ COMMITED
- RDB에서 대부분 기본적으로 사용되고 있는 격리 수준
- DIRTY READ현상 발생 X
- 실제 테이블 값을 가져오는 것이 아니라 Undo 영역에 백업된 레코드에서 값을 가져옴
- 문제점
  - 하나의 트랜잭션내에서 똑같은 SELECT 쿼리를 실행했을 때는 항상 같은 결과를 가져와야 하는 REPEATABLE READ의 정합성에 어긋남
  - 이러한 문제는 주로 입금, 출금 처리가 진행되는 금전적인 처리에서 주로 발생
    - 데이터의 정합성은 깨지고, 버그는 찾기 어려워짐

### REPEATABLE READ
- MYSQL에서는 트랜잭션마다 트랜잭션 ID를 부여하여 트랜잭션 ID보다 작은 트랜잭션 번호에서 변경한 것만 읽게 됨
- Undo 공간에 백업해두고 실제 레코드 값을 변경
  - 백업된 데이터는 불필요하다고 판단하는 시점에 주기적으로 삭제
  - Undo에 백업된 레코드가 많아지면 MYSQL 서버의 처리 성능이 떨어질 수 있음
- 문제점
  - PHANTOM READ
    - 다른 트랜잭션에서 수행한 변경 작업에 의해 레코드가 보였다가 안보였다가 하는 현상
    - 이를 방지하기 위해서는 쓰기 잠금을 걸어야 함

### SERIALIZABLE
- 가장 단순한 격리 수준이지만 가장 엄격한 격리 수준
- 성능 측면에서는 동시 처리성능이 가장 낮다
- PHANTOM READ가 발생하지 않지만, 데이터베이스에서는 거의 사용되지 않는다. 

## 자바 thread-safe 자료구조
- Vector
- Hashtable
- concurrent API의 Atomic Type



