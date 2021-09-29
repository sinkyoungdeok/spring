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

</details>

<details> <summary> 4. 12 Factors</summary>

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