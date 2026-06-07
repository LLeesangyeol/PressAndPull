# Design And Planning

## Product Direction

운동 기록 앱은 반복 사용성이 중요하므로 로그인 후 첫 화면을 대시보드로 구성한다. 사용자는 앱을 열자마자 최근 운동량, 인바디 요약, 오늘의 루틴 추천을 확인한다.

## Information Architecture

- Auth: 로그인, 회원가입, 데모 계정 안내
- Home: 운동 기록 수, 총 볼륨, 루틴 점수, 최신 체지방률, 추천 루틴
- Workouts: 운동 기록 목록, 추가, 수정, 삭제
- Routine: 최근 기록 기반 Push/Pull/Legs 추천과 운동 모션
- InBody: 체중, 골격근량, 체지방률 기록 목록, 추가, 수정, 삭제

## UI Style

- Material 3 Compose
- 블랙앤화이트 톤
- 흰 배경, 검정 타이포그래피, 얇은 보더 카드
- 로그인 화면은 검정 배경으로 앱의 첫 인상을 강하게 구성
- 하단 NavigationBar로 주요 화면 이동
- FloatingActionButton으로 기록 추가
- 입력 Dialog에서 검증 오류 표시
- Compose Canvas 기반 애니메이션 모션 패널로 운동 동작을 시각 안내

## Routine Recommendation Rule

1. 최근 5개 운동 기록에서 빠진 부위를 우선 추천한다.
2. 체지방률이 20% 이상이면 컨디셔닝 루틴을 강화한다.
3. 기본 추천은 Push, Pull, Legs 중 하나와 보조 컨디셔닝 루틴으로 구성한다.

## Motion Coaching

- Bench/Press 계열: 누워서 프레스하는 경로를 위아래 애니메이션으로 표시
- Pull 계열: 풀업 상승/하강 동작 표시
- Row 계열: 힌지 자세에서 팔꿈치를 뒤로 당기는 동작 표시
- Squat/Leg 계열: 힙과 무릎이 함께 접히는 하강/상승 동작 표시
