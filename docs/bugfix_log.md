# Bugfix Log

## BUG-001: Android Studio 실행 실패

- Symptom: `MainActivity.kt` 교체 중 파일이 삭제된 상태라 앱 진입점이 없어 실행할 수 없었다.
- Cause: 화면 전체 교체 패치가 중간에 끊기면서 삭제 후 추가 작업이 완료되지 않았다.
- Fix: 새 `MainActivity.kt`를 추가하고 Compose 앱 진입점을 복구했다.
- Verification: `.\gradlew.bat assembleDebug` 성공.

## BUG-002: Compose Slot 타입 컴파일 오류

- Symptom: `Unresolved reference 'Column'` 오류가 발생했다.
- Cause: `SectionCard` content receiver 타입을 `Column` 함수명으로 잘못 지정했다.
- Fix: receiver 타입을 `ColumnScope`로 변경했다.
- Verification: `.\gradlew.bat assembleDebug` 성공.

