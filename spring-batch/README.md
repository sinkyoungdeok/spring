
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

##  1. 스프링 배치 기본 구조

![image](https://user-images.githubusercontent.com/28394879/145978804-5dc6a43f-fa54-4c16-b530-79ffd9afd464.png)

### Job
- Job은 JobLauncher에 의해 실행
- Job은 배치의 실행 단위를 의미
- Job은 N개의 Step을 실행할 수 있으며, 흐름(Flow)을 관리할 수 있다
  - 예를 들면, A Step 실행 후 조건에 따라 B Step 또는 C Step을 실행 설정 

### Step
- Step은 Job의 세부 실행 단위이며, N개의 등록돼 실행된다.
- Step의 실행 단위는 크게 2가지로 나눌 수 있다
  1. Chunk 기반: 하나의 큰 덩어리를 n개씩 나누어서 실행
  2. Task 기반: 하나의 작업 기반으로 실행
- Chunk 기반 Step은 ItemReader, ItemProcessor, ItemWriter가 있다
  - 여기서 Item은 배치 처리 대상 객체를 의미한다
- ItemReader
  - 배치 처리 대상 객체를 읽어 ItemProcessor 또는 ItemWriter에게 전달한다
  - 예) 파일 또는 DB에서 데이터를 읽는다
- ItemProcessor
  - Input 객체를 Output 객체로 filtering 또는 processing해 ItemWriter에게 전달한다
  - 예) ItemReader에서 읽은 데이터를 수정 또는 ItemWriter 대상인지 filtering 한다
  - ItemProcessor는 optional 하다
  - ItemProcessor가 하는 일을 ItemReader 또는 ItemWriter가 대신할 수 있다. 
- ItemWriter
  - 배치 처리 대상 객체를 처리한다
  - 예) DB update를 하거나, 처리 대상 사용자에게 알림을 보낸다 

</details>

<details> <summary> 2. 스프링 배치 테이블 구조와 이해 </summary>

</details>

<details> <summary> 3. Job, JobInstance, JobExecution, Step, StepExecution 이해 </summary>

</details>
<details> <summary> 4. 데이터 공유 ExecutionContext 이해 </summary>

</details>
