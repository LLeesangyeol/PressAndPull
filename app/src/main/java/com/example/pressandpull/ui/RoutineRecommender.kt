package com.example.pressandpull.ui

import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.model.RoutineRecommendation

fun recommendRoutine(snapshot: FitnessSnapshot): List<RoutineRecommendation> {
    val recentCategories = snapshot.workouts.take(5).map { it.category }.toSet()
    val latestFat = snapshot.latestBodyMetric?.bodyFatPercent ?: 18.0

    val base = listOf(
        RoutineRecommendation(
            title = "Push Strength",
            focus = "가슴, 어깨, 삼두를 강하게 미는 루틴",
            exercises = listOf("Bench Press 4x6", "Overhead Press 3x8", "Incline Dumbbell Press 3x10", "Triceps Pushdown 3x12")
        ),
        RoutineRecommendation(
            title = "Pull Strength",
            focus = "등, 이두, 후면 사슬을 당기는 루틴",
            exercises = listOf("Pull Up 4세트", "Barbell Row 4x8", "Lat Pulldown 3x10", "Face Pull 3x15")
        ),
        RoutineRecommendation(
            title = "Leg Power",
            focus = "스쿼트 중심의 하체 근력 루틴",
            exercises = listOf("Squat 4x6", "Romanian Deadlift 3x8", "Lunge 3x10", "Plank 3x45초")
        ),
        RoutineRecommendation(
            title = "Upper Volume",
            focus = "상체 근비대를 위한 반복 볼륨 루틴",
            exercises = listOf("Dumbbell Bench Press 4x10", "Seated Row 4x10", "Side Lateral Raise 3x15", "Cable Curl 3x12")
        ),
        RoutineRecommendation(
            title = "Conditioning",
            focus = "체력과 체성분 관리를 함께 노리는 루틴",
            exercises = listOf("Goblet Squat 3x12", "Push Up 4세트", "Lat Pulldown 3x12", "Zone 2 유산소 25분")
        ),
        RoutineRecommendation(
            title = "Core Stability",
            focus = "복압, 골반, 견갑 안정성을 만드는 루틴",
            exercises = listOf("Plank 3x60초", "Dead Bug 3x12", "Farmer Carry 4라운드", "Face Pull 3x15")
        )
    )

    val priority = when {
        "Push" !in recentCategories -> "Push"
        "Pull" !in recentCategories -> "Pull"
        "Legs" !in recentCategories -> "Legs"
        latestFat >= 20.0 -> "Conditioning"
        else -> "Volume"
    }

    return base.sortedBy {
        when {
            priority == "Push" && "Push" in it.title -> 0
            priority == "Pull" && "Pull" in it.title -> 0
            priority == "Legs" && "Leg" in it.title -> 0
            priority == "Conditioning" && "Conditioning" in it.title -> 0
            priority == "Volume" && "Volume" in it.title -> 0
            else -> 1
        }
    }
}
