# Requirements

## App Concept

Press & Pull은 개인 운동 기록, 인바디 변화, 루틴 추천을 오프라인에서도 관리할 수 있는 Android Native 헬스케어 앱이다.

## User Scenarios

1. 사용자는 로그인하거나 회원가입하여 본인 계정으로 앱을 사용한다.
2. 사용자는 오늘 수행한 운동의 날짜, 운동명, 부위, 세트, 반복 횟수, 중량, 메모를 등록한다.
3. 사용자는 기존 운동 기록을 조회, 수정, 삭제한다.
4. 사용자는 체중, 골격근량, 체지방률 등 인바디 기록을 등록한다.
5. 사용자는 기존 인바디 기록을 조회, 수정, 삭제한다.
6. 사용자는 최근 운동 및 인바디 데이터를 기반으로 추천 루틴을 확인한다.
7. 사용자는 운동별 동작 안내 애니메이션을 확인한다.
8. 사용자는 네트워크가 없어도 기존 기록 조회와 핵심 CRUD 기능을 사용할 수 있다.

## Functional Requirements

| ID | Requirement | Required | Status |
| --- | --- | --- | --- |
| F-01 | 핵심 엔티티 1종 이상에 대해 생성/조회/수정/삭제(CRUD)를 제공한다. | O | Done |
| F-02 | 최소 3개 이상의 주요 화면을 구성한다. | O | Done |
| F-03 | 입력 검증 및 예외 처리 UI 피드백을 제공한다. | O | Done |
| F-04 | Room 기반 로컬 저장소로 데이터 영속화를 제공한다. | O | Done |
| F-05 | Agent와 협업한 의사결정/수정 이력을 문서화한다. | O | Done |

## Non-Functional Requirements

| ID | Requirement | Status |
| --- | --- | --- |
| NF-01 | Android Native Kotlin + Jetpack Compose로 구현한다. | Done |
| NF-02 | 오프라인에서도 핵심 기능이 동작한다. | Done |
| NF-03 | MVVM 구조를 적용한다. | Done |
| NF-04 | Room을 사용해 로컬 데이터를 영속화한다. | Done |
| NF-05 | Navigation Compose로 화면 이동을 구성한다. | Done |
| NF-06 | Material 3 Compose UI를 사용한다. | Done |
| NF-07 | Hilt로 의존성을 주입한다. | Done |
| NF-08 | Android Studio에서 별도 서버 없이 실행 가능해야 한다. | Done |
