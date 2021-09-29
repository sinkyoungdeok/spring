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

</details>

<details> <summary> 6. Microservice Architecture란? </summary>

</details>

<details> <summary> 7. SOA vs MSA </summary>

</details>

<details> <summary> 8. Microservice Architecture Structures </summary>

</details>

<details> <summary> 9. Spring Cloud란? </summary>

</details>