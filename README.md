# CMS Content Management API

## 📌 프로젝트 소개

간단한 CMS(Content Management System) REST API를 구현한 프로젝트입니다.
콘텐츠 CRUD 기능과 사용자 인증 및 권한 처리를 포함하고 있습니다.

---

## ⚙️ 기술 스택

* Java 25
* Spring Boot 4
* Spring Security
* JPA (Hibernate)
* H2 Database
* Lombok

---

## 🧱 설계 방식

### ✔ Entity 생성 방식

* Builder 패턴과 정적 팩토리 메서드(`of`)를 사용하여 엔티티를 생성
* 생성 로직을 명확하게 분리하여 가독성과 유지보수성 향상
* 필수값 누락 방지 및 객체 생성 일관성 유지

---

### ✔ DTO 변환 방식

* DTO 내부에 정적 메서드(`from`)를 정의하여 Entity → DTO 변환 수행
* 변환 책임을 DTO로 위임하여 Service 계층의 역할 단순화
* 코드 재사용성과 가독성 향상

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

* Content는 Account를 참조 (작성자, 수정자)
* ManyToOne 관계로 설계
* created_by, last_modified_by를 통해 사용자 식별

---

## 🔐 인증 및 권한 처리

* Spring Security 기반 인증 구현
* 로그인 방식: (사용한 방식 기입, 예: Session / JWT 등)

### ✔ 권한 정책

* USER: 본인이 작성한 콘텐츠만 수정/삭제 가능
* ADMIN: 모든 콘텐츠 수정/삭제 가능

---

## 📡 API 명세

### 1️⃣ Auth API

| Method | URL          | 설명            |
| ------ | ------------ | ------------- |
| GET    | /auth/me     | 현재 로그인 사용자 조회 |
| POST   | /auth/signup | 회원가입          |
| POST   | /auth/login  | 로그인           |
| POST   | /auth/logout | 로그아웃          |

---

### 2️⃣ Content API

| Method | URL            | 설명              |
| ------ | -------------- | --------------- |
| GET    | /contents      | 콘텐츠 목록 조회 (페이징) |
| GET    | /contents/{id} | 콘텐츠 상세 조회       |
| POST   | /contents      | 콘텐츠 생성          |
| PUT    | /contents/{id} | 콘텐츠 수정          |
| DELETE | /contents/{id} | 콘텐츠 삭제          |

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

## 🚀 실행 방법

1. 프로젝트 클론
2. 애플리케이션 실행
3. 브라우저에서 Swagger 접속

```
http://localhost:8080/swagger-ui.html
```

---

## 📄 API 문서

* Swagger UI를 통해 API 테스트 및 확인 가능

---

## 🤖 AI 활용

* ChatGPT를 활용하여:

    * Spring Security 구조 설계 참고
    * 예외 처리 방식 개선
    * README 작성 및 정리

---

## 💡 추가 구현 사항

* 조회수 증가 기능
* 생성/수정 시간 자동 처리
* 작성자 기반 권한 검증 로직 구현

---
