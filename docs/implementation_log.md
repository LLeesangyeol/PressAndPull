# Implementation Log

## 2026-06-07

- 기존 기본 Compose 템플릿을 운동 관리 앱으로 전환했다.
- 외부 API 키 없이 실행 가능하도록 Android `SQLiteOpenHelper` 기반 저장소를 구현했다.
- `WorkoutLog`, `BodyMetric`, `RoutineRecommendation`, `FitnessSnapshot` 모델을 추가했다.
- 홈, 운동 기록, 루틴 추천, 인바디 하단 탭 구조를 구현했다.
- 운동 기록과 인바디 기록의 추가/수정/삭제 Dialog를 구현했다.
- Supabase PostgreSQL + 로컬 SQLite 캐시 구조를 권장 DB 아키텍처로 문서화했다.
- `assembleDebug` 빌드를 실행해 APK 생성 가능 상태를 확인했다.

## 2026-06-07 추가 개선

- 로컬 로그인/회원가입 기능을 추가했다.
- `users` 테이블을 추가하고 운동/인바디 데이터를 `userId` 기준으로 분리했다.
- UI를 블랙앤화이트 톤으로 재설계했다.
- 로그인 화면을 검정 배경의 첫 화면으로 구성했다.
- Compose Canvas 애니메이션으로 운동별 모션 안내를 추가했다.
- Bench/Press, Pull Up, Row, Squat/Leg 계열 동작 큐를 표시한다.
- `assembleDebug`, `test`를 다시 실행해 통과를 확인했다.

## 2026-06-07 구조 및 UI 개선

- 루트에 몰려 있던 모델, DB, UI 코드를 패키지별로 분리했다.
- `model`, `data`, `ui/screens`, `ui/components`, `ui/design`, `ui/dialogs` 구조를 추가했다.
- `MainActivity`는 앱 진입점 역할만 하도록 축소했다.
- 공통 카드, 입력 필드, 통계 타일, 운동 모션 컴포넌트를 분리했다.
- 홈 화면에 검정 추천 카드와 명확한 통계 타일을 배치해 블랙앤화이트 톤을 강화했다.
- 로그인 화면, 운동 기록 카드, 루틴 카드, 인바디 카드의 정보 위계를 정리했다.
- 구조 변경 후 `assembleDebug`, `test` 통과를 확인했다.

## Trade-Offs

- Room을 사용하면 타입 안정성과 DAO 테스트가 좋아지지만, 현재 프로젝트는 즉시 실행 가능성을 우선해 추가 의존성 없이 SQLite를 사용했다.
- 실제 어디서든 접근 가능한 데이터는 원격 DB가 필요하다. 현재 구현은 로컬 영속 저장이며, 운영 버전에서는 Supabase 동기화 계층을 추가해야 한다.
