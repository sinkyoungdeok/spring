
# 출처
- url: https://fastcampus.co.kr/dev_online_javaend


## 1. 스프링 배치란 

<details><summary> 1. 배치와 스프링 배치 이해 </summary>

## 1. 배치와 스프링 배치 이해

### 1. 배치란? 
- 큰 단위의 작업을 일괄처리
- 대부분 처리량이 많고 비 실시간성 처리에 사용
  - 대용량 데이터 계산, 정산, 통계, 데이터베이스, 변환 등
- 컴퓨터 자원을 최대로 활용
  - 컴퓨터 자원 사용이 낮은 시간대에 배치를 처리하거나
  - 배치만 처리하기 위해 사용자가 사용하지 않는 또 다른 컴퓨터 자원을 사용
- 사용자 상호작용으로 실행되기 보단, 스케줄러와 같은 시스템에 의해 실행되는 대상
  - 예를 들면 매일 오전 10시에 배치 실행, 매주 월요일 12시 마다 실행
  - crontab, jenkins ...

### 2. 스프링 배치란? 
- 배치 처리를 하기 위한 Spring Framework 기반 기술
  - Spring에서 지원하는 기술 적용 가능
  - DI, AOP, 서비스 추상화
- 스프링 배치의 실행 단위인 Job과 Step
- 비교적 간단한 작업(Tasklet) 단위 처리와, 대량 묶음(Chunk) 단위 처리 

</details>

<details><summary> 2. 환경 설정 및 준비 </summary>

## 2. 환경 설정 및 준비

- 개발 환경
  - Intellij IDEA
  - Mac OS
- 프로젝트 생성
  - Java8 +
  - Gradle
  - Spring Boot 2.x + 
    - Spring Batch, Spring JDBC, Spring Data JPA, Lombok, etc...
  - H2 DB
  - MySQL DB 
- DB 생성
  - `create database spring_batch;`

</details>

<details><summary> 3. Hello, Spring Batch </summary>

## 3. Hello, Spring Batch

- program argument: `--job.name=helloJob`

</details>

## 2. 스프링 배치 아키텍쳐 

<details> <summary> 1. 스프링 배치 기본 구조 </summary>

</details>

<details> <summary> 2. 스프링 배치 테이블 구조와 이해 </summary>

</details>

<details> <summary> 3. Job, JobInstance, JobExecution, Step, StepExecution 이해 </summary>

</details>
<details> <summary> 4. 데이터 공유 ExecutionContext 이해 </summary>

</details>
