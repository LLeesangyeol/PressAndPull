# Implementation Log

## 2026-06-07 Initial Implementation

- Kotlin + Jetpack Compose 기반 Press & Pull 앱을 구성했다.
- 운동 기록, 인바디 기록, 루틴 추천 모델을 추가했다.
- 로그인/회원가입, 홈, 운동 기록, 루틴, 인바디 화면을 구현했다.
- 운동 기록 및 인바디 기록의 CRUD Dialog를 구현했다.
- Compose Canvas 기반 운동 동작 안내 컴포넌트를 추가했다.
- 초기에는 SQLiteOpenHelper 기반 로컬 저장소로 데이터 영속화를 구현했다.

## 2026-06-11 Architecture Upgrade

- 권장 스택 충족을 위해 SQLiteOpenHelper를 Room으로 교체했다.
- `UserEntity`, `WorkoutLogEntity`, `BodyMetricEntity`를 추가했다.
- `FitnessDao`를 추가하여 사용자, 운동 기록, 인바디 기록 쿼리를 정의했다.
- `FitnessDatabase`를 RoomDatabase로 변경했다.
- `FitnessRepository`를 추가해 데이터 접근을 단일 계층으로 캡슐화했다.
- `FitnessViewModel`을 추가해 UI 상태와 CRUD 이벤트를 관리하도록 했다.
- Hilt를 적용하고 `PressAndPullApplication`, `AppModule`, `@AndroidEntryPoint`, `@HiltViewModel`을 추가했다.
- 기존 상태 기반 탭 전환을 Navigation Compose `NavHost` 기반 화면 이동으로 변경했다.
- `MainActivity`는 앱 진입점 역할만 수행하도록 단순화했다.
- `AuthScreen`의 로그인/회원가입 처리를 ViewModel 이벤트 기반으로 변경했다.
- 깨진 한국어 UI 문구 일부를 정상 한국어로 정리했다.

## 2026-06-11 Test And Documentation

- Compose UI 테스트 `AuthScreenTest`를 추가했다.
- README를 최신 실행/빌드/테스트 방법 기준으로 갱신했다.
- docs 필수 산출물을 현재 구현 기준으로 갱신했다.
- `assembleDebug`, `test`, `lint` 실행 결과를 `test_result.md`에 기록했다.

## Changed Files

- `gradle/libs.versions.toml`: Room, Navigation Compose, Hilt, KSP 의존성 추가
- `build.gradle.kts`: KSP, Hilt 플러그인 추가
- `app/build.gradle.kts`: Room/Hilt/Navigation 의존성 추가
- `gradle.properties`: AGP 9 + KSP 호환 설정 추가
- `AndroidManifest.xml`: Hilt Application 등록
- `MainActivity.kt`: Hilt 진입점 적용
- `PressAndPullApplication.kt`: Hilt Application 추가
- `data/*`: Room 데이터 계층 추가
- `di/AppModule.kt`: DB/DAO 의존성 제공
- `ui/FitnessViewModel.kt`: MVVM 상태관리 추가
- `ui/PressPullApp.kt`: Navigation Compose 적용
- `ui/screens/AuthScreen.kt`: ViewModel 이벤트 기반 로그인/회원가입 UI로 변경
- `app/src/androidTest/.../AuthScreenTest.kt`: Compose UI 테스트 추가

## Trade-Offs

- 원격 DB는 사용하지 않았다. 과제 조건상 로컬 또는 원격 저장소 중 하나면 되며, 오프라인 핵심 기능을 보장하기 위해 Room 로컬 DB를 선택했다.
- Room schema export는 제출 범위에서 필수 산출물이 아니므로 `exportSchema=false`로 설정했다.
- AGP 9와 KSP 조합에서 generated source 등록 호환 이슈가 있어 `android.disallowKotlinSourceSets=false` 설정을 추가했다.
- Compose UI 테스트는 기기/에뮬레이터가 필요하므로 코드에 포함하고 실행 방법을 문서화했다.
