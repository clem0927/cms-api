# CMS Content Management API

## 📌 프로젝트 소개

간단한 CMS(Content Management System) REST API를 구현한 프로젝트입니다.

---

## ⚙️ 기술 스택

* Java 25
* Spring Boot 4
* Spring Security
* JPA (Hibernate)
* H2 Database
* Lombok

## 🚀 실행 방법

1. 프로젝트 클론
2. 아래 명령어 실행

./gradlew bootRun
3. 브라우저에서 Swagger 접속
4. HomeController의 리다이텍트로 아래로접속
```
http://localhost:8080/swagger-ui/index.html
```
- 기본 포트: 8080
- H2 콘솔: http://localhost:8080/h2-console
- Swagger: http://localhost:8080/swagger-ui/index.html
---

## 🗂️ DB 설계

### 📌 contents

| 컬럼명                | 설명               | 데이터 타입                            |
| ------------------ | ---------------- | --------------------------------- |
| id                 | 고유 아이디           | bigint primary key auto_increment |
| title              | 콘텐츠 제목           | varchar(100) not null             |
| description        | 콘텐츠 내용           | text                              |
| view_count         | 조회수              | bigint not null                   |
| created_date       | 생성일              | timestamp                         |
| created_by         | 생성자 (account.id) | bigint not null                   |
| last_modified_date | 수정일              | timestamp                         |
| last_modified_by   | 수정자 (account.id) | bigint                            |

---

### 📌 account

| 컬럼명      | 설명                | 데이터 타입                            |
| -------- | ----------------- | --------------------------------- |
| id       | 고유 아이디            | bigint primary key auto_increment |
| username | 사용자 아이디           | varchar(50) not null unique       |
| password | 비밀번호 (암호화 저장)     | varchar(100) not null             |
| role     | 권한 (USER / ADMIN) | varchar(20) not null              |

---

### 🔹 관계 설명

* Account 와 Contents 의 1:N 관계를 외래키로 참조(OneToMany)

---
## 🗃️ 초기 데이터 (data.sql)

애플리케이션 실행 시 테스트를 위한 초기 데이터가 자동으로 생성됩니다.

### ✔ Account 데이터

* 총 3명의 사용자 계정이 생성됩니다.

    * `admin` (ADMIN 권한)
    * `user1` (USER 권한)
    * `user2` (USER 권한)

* 모든 계정의 초기 비밀번호는 아래와 같습니다.

```
1234
```

* 비밀번호는 BCrypt로 암호화되어 저장됩니다.

---

### ✔ Content 데이터

* `user1` 계정: 10개의 콘텐츠 생성
* `user2` 계정: 1개의 콘텐츠 생성

👉 테스트 시 다양한 데이터 조회 및 페이징 기능을 확인할 수 있습니다.

---

## 🔐 유효성 검사

회원가입 시 다음과 같은 유효성 검사를 적용했습니다.

* username

    * 공백 입력 불가
* password

    * 공백 입력 불가
    * 최소 길이 및 최대 길이 제한 적용

👉 유효성 검사 실패 시 적절한 에러 메시지가 반환됩니다.


## 📡 API 명세

### 1️⃣ Auth API

| Method | URL          | 설명            | 예시 |
| ------ | ------------ | ------------- | ---- |
| GET    | /auth/me     | 현재 로그인 사용자 조회 | Authorization:{token} |
| POST   | /auth/signup | 회원가입          | { "username": "user1", "password": "1234" } |
| POST   | /auth/login  | 로그인           | { "username": "user1", "password": "1234" } |
| POST   | /auth/logout | 로그아웃          | Authorization:{token} |

---

### 2️⃣ Content API

| Method | URL            | 설명              | 예시                                       |
| ------ | -------------- | --------------- |------------------------------------------|
| GET    | /contents      | 콘텐츠 목록 조회 (페이징) | /contents?page=0&size=5&sort=id,desc     |
| GET    | /contents/{id} | 콘텐츠 상세 조회       | /contents/1                              |
| POST   | /contents      | 콘텐츠 생성          | { "title": "제목", "description": "내용" }   |
| PUT    | /contents/{id} | 콘텐츠 수정          | { "title": "수정", "description": "수정내용" } |
| DELETE | /contents/{id} | 콘텐츠 삭제          | /contents/1                              |

---
---

## 🧱 설계 방식

### ✔ Entity 생성 방식

* Builder 패턴과 정적 팩토리 메서드(`of`)를 사용하여 엔티티를 생성
* 생성 로직을 명확하게 분리하여 가독성과 유지보수성 향상

---

### ✔ DTO 변환 방식

* DTO 내부에 정적 메서드(`from`)를 정의하여 Entity → DTO 변환 수행
* 변환 책임을 DTO로 위임하여 Service 계층의 역할 단순화
* 코드 재사용성과 가독성 향상

---
## 🔐 인증 및 권한 처리

* HttpSession 기반 인증 방식을 사용합니다.
* 로그인 시 인증 정보를 세션에 저장하여 사용자 상태를 유지합니다.
* Swagger 테스트 편의를 위해 CSRF는 비활성화했습니다.

### ✔ 권한 정책

* USER: 본인이 작성한 콘텐츠만 수정/삭제 가능
* ADMIN: 모든 콘텐츠 수정/삭제 가능

---
## ⚠️ 예외 처리

전역 예외 처리를 위해 `@RestControllerAdvice`를 사용했습니다.

### ✔ 유효성 검사 실패

* @Valid 검증 실패 시 에러 메시지 반환

### ✔ 로그인 실패

* 아이디 또는 비밀번호 불일치 시 401 Unauthorized 반환

### ✔ 커스텀 예외

* 비즈니스 로직에서 발생하는 예외를 CustomException으로 처리
* 상태 코드와 메시지를 함께 반환

---


## 📄 API 문서

* Swagger UI를 통해 API 테스트 및 확인 가능

---

## 🤖 AI 활용

* ChatGPT를 활용하여:

    * Spring Security 구조 설계 참고
    * Swagger 환경 개선
    * 예외 처리 방식 개선
    * README 정리

---

## 💡 추가 구현 사항

* 조회수 증가 기능
* 생성/수정 시간 자동 처리

---
