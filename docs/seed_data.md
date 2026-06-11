# Seed Data

앱 최초 실행 시 `FitnessRepository.seedIfNeeded()`가 Room DB의 사용자 수를 확인하고, 사용자가 없으면 데모 데이터를 생성한다.

## Demo Account

| Field | Value |
| --- | --- |
| username | demo |
| password | 1234 |
| name | Demo Lifter |
| email | demo@presspull.local |
| birthDate | 1998-01-01 |

## Workout Logs

| Date | Exercise | Category | Sets | Reps | Weight | Memo |
| --- | --- | --- | --- | --- | --- | --- |
| 2026-06-01 | Bench Press | Push | 4 | 8 | 60.0 | Good tempo |
| 2026-06-03 | Pull Up | Pull | 4 | 6 | 0.0 | Full range |

## Body Metrics

| Date | Weight | Muscle | Fat | Memo |
| --- | --- | --- | --- | --- |
| 2026-06-04 | 72.4 | 34.1 | 17.8 | Morning check |

## Purpose

- 로그인 및 회원가입 전 앱 기능 시연
- 운동 기록 목록/추천 루틴 화면 확인
- 인바디 기록 조회 확인
- 테스트와 수동 검증의 기준 데이터 제공
