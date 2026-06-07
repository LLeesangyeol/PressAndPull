# Test Plan

## Unit Test Targets

- `WorkoutLog.volume` calculation
- `FitnessSnapshot.totalVolume`
- `FitnessSnapshot.latestBodyMetric`
- Routine recommendation rules

## Manual Test Cases

| ID | Case | Expected |
| --- | --- | --- |
| TC-01 | 앱 실행 | 홈 대시보드가 표시된다. |
| TC-02 | 운동 기록 추가 | Workouts 화면에 새 기록이 표시된다. |
| TC-03 | 운동 기록 수정 | 수정된 세트/반복/중량이 목록에 반영된다. |
| TC-04 | 운동 기록 삭제 | 삭제된 기록이 목록에서 사라진다. |
| TC-05 | 인바디 기록 추가 | InBody 화면에 새 기록이 표시된다. |
| TC-06 | 잘못된 입력 저장 | 오류 메시지가 표시되고 저장되지 않는다. |
| TC-07 | 앱 재실행 | SQLite에 저장된 데이터가 유지된다. |

## Execution

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat test
```

