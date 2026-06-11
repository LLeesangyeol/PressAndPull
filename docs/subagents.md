# Agent Collaboration

## Roles

| Role | Responsibility |
| --- | --- |
| Product/Requirement Agent | 과제 요구사항을 기능/비기능 요구사항으로 분해 |
| Architecture Agent | MVVM, Room, Navigation Compose, Hilt 적용 방향 결정 |
| Implementation Agent | Kotlin/Compose 코드 수정, 데이터 계층 및 UI 연결 |
| Test Agent | 단위 테스트, Compose UI 테스트, lint 실행 기준 정리 |
| Documentation Agent | docs 산출물과 README 최신화 |

## Collaboration Flow

1. 요구사항을 Android Native, CRUD, 오프라인 저장, 문서/테스트 기준으로 분류했다.
2. 기존 SQLiteOpenHelper 구조가 권장 스택을 완전히 충족하지 못한다고 판단했다.
3. Room, Repository, Hilt, ViewModel, Navigation Compose를 적용하는 방향으로 수정했다.
4. 변경 후 `assembleDebug`, `test`, `lint` 기준으로 검증했다.
5. 구현 변경 사항과 트레이드오프를 문서화했다.

## Decisions

- 원격 저장소는 사용하지 않고 Room 로컬 DB를 사용한다.
- 오프라인 핵심 기능을 최우선으로 하며 별도 서버 배포 부담을 제거한다.
- 화면 이동은 Navigation Compose route 기반으로 구성한다.
- 입력 검증은 Dialog 및 AuthScreen에서 즉시 UI 피드백으로 제공한다.
