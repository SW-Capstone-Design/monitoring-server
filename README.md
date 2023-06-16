# Monitoring-Server

SpringBoot와 Android를 통해 Beacon과 연동하여 프로젝트 개발

## ✅ 프로젝트 정보
Beacon 센서를 활용한 자동화 출입 및 안전 관리 시스템

## 🖥️ 프로젝트 소개
건설 현장 및 제조 공장별 근태 및 출입 관리에 대한 관리 사각지대 개선
건설 현장 제조 공장 내 안전 환경을 위한 센서(Beacon) 모니터링 기술 개발
보안 및 위험 구역의 임의 접근에 대한 경고 기능 구현 및 모니터링을 통한 취약성 강화
<br>

## 🕰️ 개발 기간
* 23.03.30일 - 22.06.19일

### 🧑‍🤝‍🧑 맴버구성
 - 팀장  : 김진형 - 메인 페이지, 메인 CSS, 사용자 로그인 및 로그아웃, 회원가입, 배포 관리
 - 팀원1 : 강솔문 - 보안구역 및 사용자 실시간 위치 구현을 위한 삼변측량 구현
 - 팀원2 : 김영록 - 비콘과 모바일 앱 연동 및 서버와의 데이터 통신 관리 및 구현

### ⚙️ 개발 환경
- `Java 17`
- `JDK 1.8.0` : 수정 필요
- **IDE** : STS 3.9 : 수정 필요
- **Framework** : Springboot(3.0.4)
- **Database** : MariaDB
- **ORM** : JPA

### 🪜 패키지 구조
```bash
├── java
│   └── kr
│       └── co
│           └── monitoringserver
│               ├── controller
│               │   └── api
│               ├── infra
│               │   ├── config
│               │   │   └── auth
│               │   ├── global
│               │   │   ├── exception
│               │   │   └── model
│               │   └── handler
│               ├── persistence
│               │   ├── entity
│               │   │   ├── alert
│               │   │   ├── attendance
│               │   │   ├── beacon
│               │   │   ├── securityArea
│               │   │   └── user
│               │   └── repository
│               │       
│               └── service
│                   ├── dtos
│                   │   ├── request
│                   │   │   ├── attendance
│                   │   │   ├── beacon
│                   │   │   ├── fcm
│                   │   │   ├── securityArea
│                   │   │   └── user
│                   │   └── response
│                   ├── enums
│                   ├── mappers
│                   └── service
│                       ├── alert
│                       ├── attendance
│                       ├── beacon
│                       ├── fcm
│                       ├── securityArea
│                       └── user
```


## 📌 주요 기능
#### 회원가입 - <a href="https://github.com/SW-Capstone-Design/monitoring-server/wiki/%EA%B8%B0%EB%8A%A5-%7C-MainPage-&-MyPage(UserPage)" >상세보기 - WIKI 이동</a>
- 주소 API 연동
- ID 중복 체크
- Security를 통해 PW 암호화
#### 마이 페이지 (사용자 페이지) - <a href="https://github.com/SW-Capstone-Design/monitoring-server/wiki/%EA%B8%B0%EB%8A%A5-%7C-MainPage-&-MyPage(UserPage)" >상세보기 - WIKI 이동</a>
- 주소 API 연동
- 회원정보 변경
- 출/퇴근 정보 확인

#### 삼변 측량 - <a href="https://github.com/SW-Capstone-Design/monitoring-server/wiki/%EC%82%BC%EB%B3%80%EC%B8%A1%EB%9F%89" >상세보기 - WIKI 이동</a>
- 사용자 위치 계산
- 보안구역 접근 감지
- 사용자 RSSI 값과 비콘의 txPower 값 계산을 통해 position과 distance를 계산

#### 사용자 위치 계산 - <a href="https://github.com/SW-Capstone-Design/monitoring-server/wiki/%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%9C%84%EC%B9%98-%EA%B3%84%EC%82%B0" >상세보기 - WIKI 이동</a>
- 삼변측량을 통해 비콘과 사용자 간의 거리를 계산
- 계산된 사용자 정보를 통해 보안구역 접근을 감지
#### 보안구역 등록 및 수정, 삭제 - <a href="https://github.com/SW-Capstone-Design/monitoring-server/wiki/%EB%B3%B4%EC%95%88%EA%B5%AC%EC%97%AD-%EB%93%B1%EB%A1%9D-%EB%B0%8F-%EC%88%98%EC%A0%95,-%EC%82%AD%EC%A0%9C" >상세보기 - WIKI 이동</a> 
- 비안가된 사용자가 접근할 경우, 경고 알림 생성
- 인가 및 비인가된 사용자 보안구역 접근 시 보안구역 출입 기록 생성
- 보안구역 등록, 읽기, 수정, 삭제(CRUD)
- 보안구역 출입 기록 등록, 읽기, 수정, 삭제(CRUD)
#### 비콘 등록 및 수정, 삭제 - <a href="https://github.com/SW-Capstone-Design/monitoring-server/wiki/%EB%B9%84%EC%BD%98-%EB%93%B1%EB%A1%9D-%EB%B0%8F-%EC%88%98%EC%A0%95,-%EC%82%AD%EC%A0%9C" >상세보기 - WIKI 이동</a> 
- 모바일로 등록된 비콘 정보를 서버로 넘겨받아 비콘의 정보 등록
- 해당 비콘 읽기, 수정, 삭제(CRUD)
#### 출석 등록 및 수정, 삭제 - <a href="https://github.com/SW-Capstone-Design/monitoring-server/wiki/%EC%B6%9C%EC%84%9D-%EB%93%B1%EB%A1%9D-%EB%B0%8F-%EC%88%98%EC%A0%95,-%EC%82%AD%EC%A0%9C" >상세보기 - WIKI 이동</a> 
- 근로 구역 내 비콘과 모바일 간 위치 계산을 통한 출 / 퇴근 자동화
- 출/퇴근 등록, 읽기, 수정, 삭제(CRUD)

#### 관리자 페이지  - <a href="https://github.com/SW-Capstone-Design/monitoring-server/wiki/%EA%B4%80%EB%A6%AC%EC%9E%90-%ED%8E%98%EC%9D%B4%EC%A7%80" >상세보기 - WIKI 이동</a> 
- 사용자 출/퇴근 정보 조회 및 수정, 삭제
- 등록된 보안구역 정보 조회 및 등록, 수정, 삭제
- 등록된 비콘 정보 조회 및 등록, 수정, 삭제
- 실시간 사용자 위치 모니터링
- 비콘의 배터리 정보 및 비인가 사용자 보안구역 접근 시 이벤트 발생 알림


### 예상 결과물
  - 근로자 근태 및 출입 관리 기능
  - 현장 내 근로자 위치 및 경고 로그 실시간 모니터링 기능
  - 근로자의 위험지역 및 제한지역 접근 경고 기능
