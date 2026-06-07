package com.example.pressandpull

import com.example.pressandpull.model.BodyMetric
import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.model.WorkoutLog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class FitnessModelTest {
    @Test
    fun workoutVolume_isCalculatedFromSetsRepsAndWeight() {
        val workout = WorkoutLog(
            date = "2026-06-07",
            exercise = "Squat",
            category = "Legs",
            sets = 5,
            reps = 5,
            weightKg = 100.0,
            memo = ""
        )

        assertEquals(2500, workout.volume)
    }

    @Test
    fun snapshot_summarizesWorkoutAndLatestBodyMetric() {
        val snapshot = FitnessSnapshot(
            workouts = listOf(
                WorkoutLog(date = "2026-06-01", exercise = "Bench", category = "Push", sets = 4, reps = 8, weightKg = 60.0, memo = ""),
                WorkoutLog(date = "2026-06-02", exercise = "Row", category = "Pull", sets = 3, reps = 10, weightKg = 50.0, memo = "")
            ),
            bodyMetrics = listOf(
                BodyMetric(date = "2026-06-01", weightKg = 73.0, skeletalMuscleKg = 34.0, bodyFatPercent = 18.5, memo = ""),
                BodyMetric(date = "2026-06-05", weightKg = 72.0, skeletalMuscleKg = 34.2, bodyFatPercent = 17.9, memo = "")
            )
        )

        assertEquals(3420, snapshot.totalVolume)
        assertNotNull(snapshot.latestBodyMetric)
        assertEquals(17.9, snapshot.latestBodyMetric!!.bodyFatPercent, 0.01)
    }
}
