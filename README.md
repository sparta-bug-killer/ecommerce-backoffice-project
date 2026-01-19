> # 스프링 (입문/숙련) 프로젝트

### 스파르타 내일배움캠프 Spring 백엔드 2기 3조 버그잡이단

### 팀장 : 정인호
### 팀원 : 정은식, 방효경, 김동진, 김규범
<img width="322" height="165" alt="image" src="https://github.com/user-attachments/assets/96c3b59d-00d0-495d-8875-d740f32b0469" />


---

> ## 기능 요약

### 요구사항 : 고객 상품, 주문 데이터를 효율적으로 관리하기

### 구현 기능 정리 

#### 필수 기능
- 관리자 회원가입 및 인증
- 관리자 정보 관리
- 고객 정보 관리
- 상품 정보 관리
#### 도전 기능
- 주문 정보 관리
- 고객 조회 데이터 확장
- 리뷰 정보 관리
- 상품별 리뷰 조회
- 전역 예외처리 및 공통 응답 구조 구현

 
---
 
> ## 실습 목표
- **목표**
    - 고객, 상품, 주문 관리 효율화를 통해 운영자의 업무 시간을 단축
    - 데이터 기반 통계 제공하여 마케팅·재고 관리 의사결정에 도움

---

> ## 프로젝트 구조

- Layered Architecture
  - Controller
  - Service
  - Repository
  - Entity (도메인 로직 포함)

- 도메인
  - Admin / Customer
  - Product
  - Order
  - review


<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/b485a229-2245-4b3f-bdfe-30f381921536" />
<br>
<img width="400" height="300" alt="image" src="https://github.com/user-attachments/assets/1850526d-5fd5-4af4-949d-1de5cfe180b2" />



---
> ## ERD

필수 기능 버전
<img width="2526" height="1197" alt="image" src="https://github.com/user-attachments/assets/c036f409-aeed-4763-bc8d-fc6b8bd734a8" />

필수 + 도전 기능 버전
<img width="1714" height="926" alt="image" src="https://github.com/user-attachments/assets/64f0c1ac-2539-4bcd-b6a8-16ac44344615" />



---
> ## 팀원별 기능 API 명세서
> 
필수 기능 API 명세서
<img width="1953" height="1371" alt="image" src="https://github.com/user-attachments/assets/896bbe3c-60b3-4012-b43b-9c38c19be187" />

도전 기능 API 명세서
<img width="1948" height="613" alt="image" src="https://github.com/user-attachments/assets/ba603906-0a12-4cc0-a02a-1fc803751f5f" />

[팀 노션 API 명세서 참고](https://teamsparta.notion.site/3-2d32dc3ef514803a8f00d456d2660580?source=copy_link)



---

## 설치 방법

* 프로젝트 클론
  * git clone https://github.com/sparta-bug-killer/ecommerce-backoffice-project.git
  * ecommerce-backoffice-project
 
* 개발 환경
  * Java : 17
  * Spring Boot : 4.x
  * Build Tool : Gradle
  * Database : MySQL
  * ORM : Spring Data JPA
 
* 기타
  * Postman(API 테스트)
  * Git/GitHub   
 
---


🔥 그럼 20000 입니다!🔥
