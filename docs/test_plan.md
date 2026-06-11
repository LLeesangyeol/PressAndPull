# Test Plan

## Strategy

테스트는 핵심 비즈니스 로직, Compose UI, 빌드 가능성, 정적 분석으로 나누어 수행한다.

## Unit Test

실행 명령:

```powershell
.\gradlew.bat test
```

| Case ID | Target | Scenario | Expected |
| --- | --- | --- | --- |
| UT-01 | `WorkoutLog.volume` | 세트, 반복 횟수, 중량이 주어졌을 때 총 볼륨 계산 | `sets * reps * weightKg` 값 반환 |
| UT-02 | `FitnessSnapshot.totalVolume` | 여러 운동 기록이 있을 때 전체 볼륨 합산 | 모든 운동 볼륨 합계 반환 |
| UT-03 | `FitnessSnapshot.latestBodyMetric` | 여러 인바디 기록이 있을 때 최신 기록 조회 | 가장 최근 날짜 기록 반환 |

## Compose UI Test

실행 명령:

```powershell
.\gradlew.bat connectedDebugAndroidTest
```

| Case ID | Target | Scenario | Expected |
| --- | --- | --- | --- |
| UI-01 | `AuthScreen` | 로그인 화면 최초 표시 | 앱 제목, 로그인 버튼, 데모 계정 안내 표시 |
| UI-02 | `AuthScreen` | 빈 값으로 로그인 버튼 클릭 | 입력 검증 오류 메시지 표시 |

## Build Test

실행 명령:

```powershell
.\gradlew.bat assembleDebug
```

| Case ID | Target | Scenario | Expected |
| --- | --- | --- | --- |
| BT-01 | Android project | Debug APK 빌드 | 빌드 성공 |

## Static Analysis

실행 명령:

```powershell
.\gradlew.bat lint
```

| Case ID | Target | Scenario | Expected |
| --- | --- | --- | --- |
| SA-01 | Android lint | 정적 분석 실행 | 치명적 오류 없음 |

## Manual Test

| Case ID | Scenario | Expected |
| --- | --- | --- |
| MT-01 | 데모 계정 로그인 | 홈 화면 진입 |
| MT-02 | 운동 기록 추가/수정/삭제 | 목록에 변경 사항 반영 및 Room DB에 유지 |
| MT-03 | 인바디 기록 추가/수정/삭제 | 목록에 변경 사항 반영 및 Room DB에 유지 |
| MT-04 | 하단 탭 화면 이동 | 홈, 기록, 코치, 인바디 화면 이동 |
| MT-05 | 앱 종료 후 재실행 | 저장된 기록 유지 |
