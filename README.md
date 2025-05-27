# 일정 관리 프로젝트

간단한 콘솔 기반의 일정 관리 웹 애플리케이션니다.  
사용자는 회원가입 및 로그인을 통해 자신만의 일정을 생성, 조회, 수정, 삭제할 수 있습니다.

---

## 사용 기술

Java 17

IntelliJ IDEA

Spring Boot 3.2.0   

Spring Data JPA

Gradle

---

## 주요 기능

- 회원가입
- 로그인 (쿠키 기반 인증)
- 사용자 조회 / 수정 / 삭제
- 일정 등록
- 전체 일정 조회
- 일정 상세 조회
- 일정 수정
- 일정 삭제
- 회원가입, 비밀번호 입력 시 시 유효성 검증

---

## 프로젝트 구조 및 설명

src/main/java/com/example/schedulepj/
├── controller # 사용자 및 일정 요청 처리
├── repository # DB 접근 레이어 (Spring Data JPA)
├── service # 비즈니스 로직 처리
├── dto # 요청/응답 DTO 클래스
├── entity # JPA 엔티티 클래스
├── config # 필터, 설정 관련 클래스
└── SchedulepjApplication.java
