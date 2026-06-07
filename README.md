# Press & Pull

Kotlin + Jetpack Compose 기반 운동 기록, 루틴 추천, 인바디 기록 앱입니다.

## 주요 기능

- 로그인/회원가입
- 회원가입 필드: 아이디, 이름, 이메일, 생년월일, 비밀번호
- 비밀번호 입력 마스킹: 입력 시 `*`로 표시
- 비밀번호 저장: 평문 대신 SHA-256 해시값 저장
- 사용자별 운동/인바디 데이터 분리 저장
- 운동 기록 CRUD: 날짜, 운동명, 카테고리, 세트, 반복 횟수, 중량, 메모
- 인바디 기록 CRUD: 체중, 골격근량, 체지방률, 메모
- 루틴 추천: 최근 운동 부위와 인바디 상태 기반 추천
- 운동별 동작 안내: Compose Canvas 애니메이션으로 프레스, 풀업, 로우, 스쿼트 계열 모션 제공
- 블랙앤화이트 톤 UI

## 데모 로그인

- 아이디: `demo`
- 비밀번호: `1234`

## DB 추천

최종 서비스 DB는 Supabase PostgreSQL을 권장합니다.

- 어디서든 접근 가능: 클라우드 PostgreSQL + REST/Auth API 제공
- 데이터 유지: 관리형 백업, 마이그레이션, 테이블 정책 적용 가능
- 앱 확장성: 사용자별 Row Level Security, 실시간 구독, 스토리지 연동 가능
- 오프라인 대응: Android 앱 내부 SQLite 또는 Room을 로컬 캐시로 두고 로그인 사용자 기준으로 동기화

현재 프로젝트는 외부 키 없이 바로 실행되도록 Android 기본 SQLiteOpenHelper로 로컬 회원가입/로그인과 사용자별 로컬 영속 저장을 구현했습니다.

## 실행 방법

1. Android Studio에서 `C:\Users\aya21\AndroidStudioProjects\PressAndPull` 프로젝트 루트 폴더를 엽니다.
2. Gradle Sync를 실행합니다.
3. 에뮬레이터 또는 실제 기기를 선택합니다.
4. `app` 실행 구성을 Run 합니다.

CLI 빌드:

```powershell
.\gradlew.bat assembleDebug
```

테스트:

```powershell
.\gradlew.bat test
```

## 구현 파일

- `app/src/main/java/com/example/pressandpull/MainActivity.kt`: 앱 진입점
- `app/src/main/java/com/example/pressandpull/model/`: 사용자, 운동 기록, 인바디, 루틴 모델
- `app/src/main/java/com/example/pressandpull/data/`: SQLite 저장소와 로그인/회원가입
- `app/src/main/java/com/example/pressandpull/ui/PressPullApp.kt`: 앱 상태, 탭 네비게이션, 다이얼로그 연결
- `app/src/main/java/com/example/pressandpull/ui/screens/`: 로그인, 홈, 운동 기록, 루틴, 인바디 화면
- `app/src/main/java/com/example/pressandpull/ui/components/`: 카드, 입력 필드, 운동 모션 등 공통 컴포넌트
- `app/src/main/java/com/example/pressandpull/ui/design/`: 블랙앤화이트 디자인 토큰
- `app/src/main/java/com/example/pressandpull/ui/dialogs/`: 운동/인바디 입력 다이얼로그
- `docs/`: 요구사항, DB/ERD, 디자인, 테스트, 구현 로그
