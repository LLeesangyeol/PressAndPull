package com.example.pressandpull.ui

import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.model.RoutineRecommendation

fun recommendRoutine(snapshot: FitnessSnapshot): List<RoutineRecommendation> {
    val recentCategories = snapshot.workouts.take(5).map { it.category }.toSet()
    val latestFat = snapshot.latestBodyMetric?.bodyFatPercent ?: 18.0

    val base = listOf(
        RoutineRecommendation(
            title = "밀기 근력 루틴",
            focus = "가슴, 어깨, 삼두를 강하게 미는 루틴",
            exercises = listOf("벤치프레스 4x6", "오버헤드프레스 3x8", "인클라인 덤벨프레스 3x10", "트라이셉스 푸시다운 3x12")
        ),
        RoutineRecommendation(
            title = "당기기 근력 루틴",
            focus = "등, 이두, 후면 사슬을 당기는 루틴",
            exercises = listOf("풀업 4세트", "바벨로우 4x8", "랫풀다운 3x10", "페이스풀 3x15")
        ),
        RoutineRecommendation(
            title = "하체 파워 루틴",
            focus = "스쿼트 중심의 하체 근력 루틴",
            exercises = listOf("스쿼트 4x6", "루마니안 데드리프트 3x8", "런지 3x10", "플랭크 3x45초")
        ),
        RoutineRecommendation(
            title = "상체 볼륨 루틴",
            focus = "상체 근비대를 위한 반복 볼륨 루틴",
            exercises = listOf("덤벨 벤치프레스 4x10", "시티드 로우 4x10", "사이드 레터럴 레이즈 3x15", "케이블 컬 3x12")
        ),
        RoutineRecommendation(
            title = "전신 컨디셔닝 루틴",
            focus = "체력과 체성분 관리를 함께 노리는 루틴",
            exercises = listOf("고블릿 스쿼트 3x12", "푸시업 4세트", "랫풀다운 3x12", "Zone 2 유산소 25분")
        ),
        RoutineRecommendation(
            title = "코어 안정화 루틴",
            focus = "복압, 골반, 견갑 안정성을 만드는 루틴",
            exercises = listOf("플랭크 3x60초", "데드버그 3x12", "파머 캐리 4라운드", "페이스풀 3x15")
        )
    )

    val priority = when {
        "밀기" !in recentCategories -> "밀기"
        "당기기" !in recentCategories -> "당기기"
        "하체" !in recentCategories -> "하체"
        latestFat >= 20.0 -> "컨디셔닝"
        else -> "볼륨"
    }

    return base.sortedBy {
        when {
            priority == "밀기" && "밀기" in it.title -> 0
            priority == "당기기" && "당기기" in it.title -> 0
            priority == "하체" && "하체" in it.title -> 0
            priority == "컨디셔닝" && "컨디셔닝" in it.title -> 0
            priority == "볼륨" && "볼륨" in it.title -> 0
            else -> 1
        }
    }
}

