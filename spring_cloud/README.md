# 1. Microservice와 Spring Cloud의 소개

<details> <summary> 1. 소프트웨어 아키텍처 </summary>

## 1. 소프트웨어 아키텍처


- IT 시스템의 역사
    - 1960~1980년대: Fragile, Cowboys
        - Mainframe, Hardware
    - 1990~2000년대: Robust, Distributed
        - Changes
    - 2010년대~: Resilient/Anti-Fragile, Cloud Native
        - Flow of value의 지속적인 개선
![image](https://user-images.githubusercontent.com/28394879/135281527-2d0a3325-59e1-47d6-8f32-650ee49114b0.png)

### Antifragile 특징
- Auto scaling
    - 자동 확장성을 갖는다.
![image](https://user-images.githubusercontent.com/28394879/135281911-0924f3c7-5c6d-4edb-899b-398c14256ed8.png)

- Microservices
    - 클라우드 네이티브 아키텍처의 핵심
    - 기존의 시스템들이 거대한 형태로 구축된 반면에 microservices는 모듈이나 기능등을 독립적으로 운영하고 배포 하는 것을 의미
![image](https://user-images.githubusercontent.com/28394879/135282170-13e73426-55d8-43d8-91b3-0fb8f01a8a71.png)

- Chaos engineering
    - 아래 4가지나 그 외에도 안정적인 서비스가 되도록 구축되어져 있음을 의미
    1. 시스템의 변동
    2. 예견된 불확실성
    3. 예견되지 않은 불확실성
    4. 카오스 불확실성
![image](https://user-images.githubusercontent.com/28394879/135282694-95c65a7d-77b4-4332-88ac-fca0e1334b4c.png)

- Continuous deployments
    - CI/CD와 같은 배포 파이프라인
![image](https://user-images.githubusercontent.com/28394879/135283055-619a2f1d-ff1f-4875-bcd5-4d5a110ccd68.png)

</details>

<details> <summary> 2. Cloud Native Architecture </summary>

## 2. Cloud Native Architecture

### Cloud Native Architecture의 특징
- 확장 가능한 아키텍처
    - 시스템의 수평적 확정에 유연
    - 확장된 서버로 시스템의 부하 분산, 가용성 보장
    - 시스템 또는, 서비스 애플리케이션 단위의 패키지 (컨테이너 기반 패키지)
    - 모니터링
- 탄력적 아키텍처
    - 서비스 생성 - 통합 - 배포, 비즈니스 환경 변화에 대응 시간 단축
    - 분활 된 서비스 구조
    - 무상태 통신 프로토콜
    - 서비스의 추가와 삭제 자동으로 감지
    - 변경된 서비스 요청에 따라 사용자 요청 처리 (동적 처리)
- 장애 격리 (Fault isolation)
    - 특정 서비스에 오류가 발생해도 다른 서비스에 영향 주지 않음



</details>

<details> <summary> 3. Cloud Native Application </summary>

## 3. Cloud Native Application

![image](https://user-images.githubusercontent.com/28394879/135284653-73406d81-b393-41fd-a0c7-9c01823e860b.png)

### Cloud Native Application - CI/CD
- 지속적신 통합, CI(Continuous Integration)
    - 통합 서버, 소스 관리 (SCM), 빌드 도구, 테스트 도구
    - ex) Jenkins, Team CI, Travis CI
- 지속적 배포
    - Continuous Delivery: CI까지 자동, 수동 배포
    - Continuous Deployment: 테스트부터 배포까지 완전한 자동화
    - Pipe line
![image](https://user-images.githubusercontent.com/28394879/135285216-933f5622-dd32-462e-9329-fa3795d0ab9a.png)

- 카나리 배포와 블루그린 배포
![image](https://user-images.githubusercontent.com/28394879/135285662-f24244a8-f80b-41bb-ada5-4500095ba769.png)

### Cloud Native Application - DevOps
- 개발 조직과 운영 조직의 통합을 의미
    - 이러한 통합으로 고객의 요구사항을 빠르게 반영하고 만족도 높은 결과물을 제시하는 것에 목적을 둔다.
- 기존의 엔터프라이즈 어플리케이션들은 고객의 요구사항에 맞게 도메인을 분석하고 시스템을 설계 그리고 어플리케이션 구현 테스트 배포 과정을 거쳐 3~6개월 혹은 수년에 거친다.
    - 개발 기간이 길어짐으로써 그만큼 변경사항이나 요구사항에 바로 대처 할 수 없다는 단점이 있다.
    - 이러한 변경사항이나 개선사항들이 시스템 막바지에 반영되기보다는 바로바로 반영되는 것이 더 좋다.
    - 그때그떄 고객의 요구사항을 반영하거나 개발된 내용을 테스트하는 것은 개발 기간을 더 느리게 할 수 도 있지만,
    - 개발회사가 완성도 높은 어플리케이션을 만들기 위해선 자주 업데이트하고 자주 테스트 해야 한다.
- 자주 테스트-피드백-업데이트하는 과정을 거쳐 전체 개발 일정이 완료될때 까지 지속적으로 끊임없이 진행해 나가는것을 Devops라고 한다.


### Cloud Native Application - Container 가상화
- 클라우드 네이티브의 핵심이다.
- Container가상화 기술은 기존의 하드웨어 가상화 또는 서버 가상화에 비해 적은 리소스를 사용하여 가상화 서비스를 구축할 수 있다.
![image](https://user-images.githubusercontent.com/28394879/135287740-c7f0dc27-150c-40ee-8fc9-b6a406c79369.png)

</details>

<details> <summary> 4. 12 Factors</summary>

## 4. 12 Factors

![image](https://user-images.githubusercontent.com/28394879/135288418-db3c6095-bd85-4644-8348-065b779bc94d.png)

- 최근에 3가지 추가되서 15가지이다.

1. One codebase, one application
**2. API first**
3. Dependency management
4. Design, build, release, and run
5. Configuration, credential, and code
6. Logs
7. Disposability
8. Backing services
9. Environment parity
10. Administrative processes
11. Port Binding
12. Stateless processes
13. Concurrency
**14. Telemetry**
**15. Authentication and authorization **


</details>

<details> <summary> 5. Monolithic vs. Microservice </summary>

## 5. Monolithic vs. Microservice

![image](https://user-images.githubusercontent.com/28394879/135293525-cbfa7a14-a1d1-424a-94bb-16694f6a35ac.png)

### Monolith Architecture
- 모든 업무 로직이 하나의 애플리케이션 형태로 패키지 되어 서비스
- 애플리케이션에서 사용하는 데이터가 한 곳에 모여 참조되어 서비스되는 형태
![image](https://user-images.githubusercontent.com/28394879/135294692-ccd5aafd-e7e9-4585-8911-0d9fdaae58f3.png)
- 일부 기능만 수정 하려고해도 전체 애플리케이션 빌드하고 배포 해야 되는 단점이 있다.

### Monolith vs Front & Back vs Microservice Architecture
![image](https://user-images.githubusercontent.com/28394879/135295933-5cd5838c-6586-4374-8af0-1eadba081884.png)

### Monolithic vs MSA
![image](https://user-images.githubusercontent.com/28394879/135296583-efa1eec8-19ee-48a4-95ce-91e5c7cccdc2.png)



</details>

<details> <summary> 6. Microservice Architecture란? </summary>

## 6. Microservice Architecture란?

### Microservice의 특징
1. Challenges
2. Small Well Chosen Deployable Units
3. Bounded Context
4. RESTful
5. Configuration Management
6. Cloud Enabled
7. Dynamic Scale Up And Scale Down
8. CI/CD
9. Visibility

**microservice를 도입하기 위해 고려해야 될 사항들**
1. Multiple Rates of Change
2. Independent Life Cycles
3. Independent Scalability
4. Isolated Failure
5. Simplify Interactions with External Dependencies
6. Polyglot Technology

**Microservice Team Structure**
- Two Pizza team
- Teams communicating through API contracts
- Develop, test and deploy each service independently
- Consumer Driven Contract


</details>

<details> <summary> 7. SOA vs MSA </summary>

## 7. SOA vs MSA

### SOA와 MSA와의 차이점
![image](https://user-images.githubusercontent.com/28394879/135387972-d3507360-5f4e-498b-b10a-e6d42f343cc6.png)
- 서비스 공유 지향점
    - SOA - 재사용을 통한 비용 절감
    - MSA - 서비스 간의 결합도를 낮추어 변화에 능동적으로 대응

- 기술 방식
![image](https://user-images.githubusercontent.com/28394879/135388131-44721fd5-f447-49cd-9aa7-ce33a48e5044.png)
    - SOA - 공통의 서비스를 ESB에 모아 사업 측면에서 공통 서비스 형식으로 서비스 제공
    - MSA - 각 독립된 서비스가 노출된 REST API를 사용


</details>

<details> <summary> 8. Microservice Architecture Structures </summary>

## 8. Microservice Architecture Structures

### MSA 표준 구성요소
![image](https://user-images.githubusercontent.com/28394879/135451363-ae609e4d-02d1-4332-8635-ef660f365d57.png)

### Service Mesh Capabilities
- MSA 인프라 -> 미들웨어
    - 프록시 역할, 인증, 권한 부여, 암호화, 서비스 검색, 요청 라우팅, 로드 밸런싱
    - 자가 치유 복구 서비스
- 서비스간의 통신과 관련된 기능을 자동화

### MSA 기반 기술
![image](https://user-images.githubusercontent.com/28394879/135452177-286e9ae3-0d80-4772-8574-ef19bfc32813.png)


</details>

<details> <summary> 9. Spring Cloud란? </summary>

## 9. Spring Cloud란?
- https://spring.io/projects/spring-cloud

### Main projects
- Spring Cloud Config
- Spring Cloud Netflix
- Spring Cloud Bus
- Spring Cloud Cloudfoundry
- Spring Cloud Open Service Broker
- Spring Cloud Cluster
- Spring Cloud Consul
- Spring Cloud Security
- Spring Cloud Sleuth
- Spring Cloud Data Flow
- Spring Cloud Stream
- Spring Cloud Stream App Starters
- Spring Cloud Task
- Spring Cloud Task App Starters
- Spring Cloud Zookeeper
- Spring Cloud Connectors
- Spring Cloud Starters
- Spring Cloud CLI
- Spring Cloud Contract
- Spring Cloud Gateway
- Spring Cloud OpenFeign
- Spring Cloud Pipelines
- Spring Cloud Function

**여기에서 다룰 projects**

- Spring Cloud Config
- Spring Cloud Netflix
- Spring Cloud Security
- Spring Cloud Sleuth
- Spring Cloud Starters
- Spring Cloud Gateway
- Spring Cloud OpenFeign


**스프링 클라우드 애플리케이션을 위한 서비스들**

- Centralized configuration management
    - Spring Cloud Config Server
- Location transparency
    - Naming Server (Eureka)
- Load Distribution (Load Balancing)
    - Ribbon (Client Side)
    - Spring Cloud Gateway
- Easier REST Clients
    - FeignClient
- Visibility and monitoring
    - Zipkin Distributed Tracing
    - Netflix API gateway
- Fault Tolerance
    - Hystrix

</details>



# 2. Service Discovery

<details> <summary> 1. Spring Cloud Netflix Eureka </summary>

</details>

<details> <summary> 2. Eureka Service Discovery - 프로젝트 생성 </summary>

</details>

<details> <summary> 3. User Service - 프로젝트 생성 </summary>

</details>

<details> <summary> 4. User Service - 등록 </summary>

</details>

<details> <summary> 5. User Service - Load Balancer </summary>

</details>