# Press & Pull

Press & Pull은 Android Native(Kotlin + Jetpack Compose)로 만든 운동 기록 및 루틴 추천 앱입니다. 사용자는 오프라인 상태에서도 운동 기록과 인바디 기록을 생성, 조회, 수정, 삭제할 수 있으며 최근 기록을 기반으로 추천 루틴과 운동 동작 안내를 확인할 수 있습니다.

## 주요 기능

- 로그인 및 회원가입
- 운동 기록 CRUD: 날짜, 운동명, 부위, 세트, 반복 횟수, 중량, 메모
- 인바디 기록 CRUD: 체중, 골격근량, 체지방률, 메모
- 사용자별 데이터 분리 저장
- 최근 운동/인바디 기록 기반 루틴 추천
- Compose Canvas 기반 운동 동작 안내
- Material 3 Compose UI
- MVVM, Room, Navigation Compose, Hilt 적용
- Room 로컬 DB 기반 오프라인 핵심 기능 동작

## 데모 계정

- 아이디: `demo`
- 비밀번호: `1234`

## 실행 방법

1. Android Studio에서 프로젝트 루트 `PressAndPull` 폴더를 엽니다.
2. Gradle Sync를 실행합니다.
3. 에뮬레이터 또는 실제 기기를 선택합니다.
4. `app` 실행 구성을 Run 합니다.

## 빌드 방법

```powershell
.\gradlew.bat assembleDebug
```

## 테스트 실행 방법

단위 테스트:

```powershell
.\gradlew.bat test
```

정적 분석:

```powershell
.\gradlew.bat lint
```

Compose UI 테스트는 에뮬레이터 또는 실제 기기가 연결된 상태에서 실행합니다.

```powershell
.\gradlew.bat connectedDebugAndroidTest
```

## 구현 구조

- `MainActivity.kt`: Hilt 진입점 및 Compose 앱 시작
- `PressAndPullApplication.kt`: Hilt Application
- `data/`: Room Entity, DAO, Database, Repository
- `ui/FitnessViewModel.kt`: MVVM 상태관리 및 비즈니스 이벤트 처리
- `ui/PressPullApp.kt`: Navigation Compose 기반 화면 라우팅
- `ui/screens/`: 로그인, 홈, 운동 기록, 루틴, 인바디 화면
- `ui/dialogs/`: 운동/인바디/프로필 입력 및 수정 Dialog
- `ui/components/`: 공통 UI 컴포넌트
- `docs/`: 요구사항, 데이터 모델, 테스트, 구현 이력, 버그 수정 이력, UI 목업
