# Bugfix Log

## BF-01: 권장 아키텍처 미적용

- Reproduction: 코드 확인 시 SQLiteOpenHelper 직접 사용, ViewModel/Hilt/Navigation Compose 미적용 상태였다.
- Cause: 초기 구현이 빠른 로컬 저장소 중심 구조로 작성되었다.
- Fix: Room, Repository, Hilt, ViewModel, Navigation Compose를 적용했다.
- Regression Prevention: `rg -n "SQLiteOpenHelper|selectedTab"`로 이전 구조 잔존 여부를 확인했다.

## BF-02: Hilt Gradle Plugin 호환 오류

- Reproduction: `.\gradlew.bat assembleDebug` 실행 시 `Android BaseExtension not found` 오류가 발생했다.
- Cause: 기존 Hilt Gradle Plugin 버전과 AGP 9 조합의 호환 문제였다.
- Fix: Hilt 버전을 `2.59.2`로 갱신했다.
- Regression Prevention: `assembleDebug`를 다시 실행하여 빌드 성공을 확인했다.

## BF-03: AGP 9 Built-In Kotlin + KSP Source Set 오류

- Reproduction: KSP 적용 후 빌드 시 `Using kotlin.sourceSets DSL to add Kotlin sources is not allowed with built-in Kotlin` 오류가 발생했다.
- Cause: AGP 9 built-in Kotlin 설정과 KSP generated source 등록 방식이 충돌했다.
- Fix: `gradle.properties`에 `android.disallowKotlinSourceSets=false`를 추가했다.
- Regression Prevention: `assembleDebug`, `test`, `lint`를 실행하여 통과를 확인했다.

## BF-04: 문서와 실제 구현 불일치

- Reproduction: docs와 README가 SQLiteOpenHelper 기준으로 작성되어 Room/Hilt/MVVM 변경 사항을 반영하지 못했다.
- Cause: 구조 변경 후 문서 갱신이 필요했다.
- Fix: README와 docs 필수 산출물을 최신 구현 기준으로 갱신했다.
- Regression Prevention: 제출 체크리스트에 문서 갱신 항목을 포함했다.
