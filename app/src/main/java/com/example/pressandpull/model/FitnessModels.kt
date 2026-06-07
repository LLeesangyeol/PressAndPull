package com.example.pressandpull.model

import kotlin.math.max
import kotlin.math.roundToInt

data class UserAccount(
    val id: Long = 0,
    val username: String,
    val name: String,
    val email: String,
    val birthDate: String
)

data class WorkoutLog(
    val id: Long = 0,
    val userId: Long = 0,
    val date: String,
    val exercise: String,
    val category: String,
    val sets: Int,
    val reps: Int,
    val weightKg: Double,
    val memo: String
) {
    val volume: Int = (sets * reps * weightKg).roundToInt()
}

data class BodyMetric(
    val id: Long = 0,
    val userId: Long = 0,
    val date: String,
    val weightKg: Double,
    val skeletalMuscleKg: Double,
    val bodyFatPercent: Double,
    val memo: String
)

data class RoutineRecommendation(
    val title: String,
    val focus: String,
    val exercises: List<String>
)

data class FitnessSnapshot(
    val workouts: List<WorkoutLog> = emptyList(),
    val bodyMetrics: List<BodyMetric> = emptyList()
) {
    val totalVolume: Int = workouts.sumOf { it.volume }
    val latestBodyMetric: BodyMetric? = bodyMetrics.maxByOrNull { it.date }
    val weeklyWorkoutCount: Int = workouts.take(7).size
    val strengthScore: Int = max(0, (totalVolume / 1000) + weeklyWorkoutCount * 4)
}

