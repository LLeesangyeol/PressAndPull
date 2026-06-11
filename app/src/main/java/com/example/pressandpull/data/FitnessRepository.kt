package com.example.pressandpull.data

import com.example.pressandpull.model.BodyMetric
import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.model.UserAccount
import com.example.pressandpull.model.WorkoutLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FitnessRepository @Inject constructor(
    private val dao: FitnessDao
) {
    suspend fun seedIfNeeded() {
        if (dao.userCount() > 0) return
        val userId = dao.insertUser(
            UserEntity(
                username = "demo",
                name = "Demo Lifter",
                email = "demo@presspull.local",
                birthDate = "1998-01-01",
                passwordHash = "1234".sha256(),
                createdAt = "2026-06-07"
            )
        )
        dao.upsertWorkout(WorkoutLog(userId = userId, date = "2026-06-01", exercise = "Bench Press", category = "Push", sets = 4, reps = 8, weightKg = 60.0, memo = "Good tempo").toEntity())
        dao.upsertWorkout(WorkoutLog(userId = userId, date = "2026-06-03", exercise = "Pull Up", category = "Pull", sets = 4, reps = 6, weightKg = 0.0, memo = "Full range").toEntity())
        dao.upsertBodyMetric(BodyMetric(userId = userId, date = "2026-06-04", weightKg = 72.4, skeletalMuscleKg = 34.1, bodyFatPercent = 17.8, memo = "Morning check").toEntity())
    }

    suspend fun register(username: String, name: String, email: String, birthDate: String, password: String): UserAccount? {
        val cleanUsername = username.trim()
        val cleanEmail = email.trim()
        if (dao.findUserByUsername(cleanUsername) != null || dao.findUserByEmail(cleanEmail) != null) return null
        val id = dao.insertUser(
            UserEntity(
                username = cleanUsername,
                name = name.trim(),
                email = cleanEmail,
                birthDate = birthDate.trim(),
                passwordHash = password.sha256(),
                createdAt = "2026-06-07"
            )
        )
        return dao.findUserByUsername(cleanUsername)?.copy(id = id)?.toModel()
    }

    suspend fun login(username: String, password: String): UserAccount? {
        return dao.login(username.trim(), password.sha256())?.toModel()
    }

    suspend fun updateUser(user: UserAccount, newPassword: String): UserAccount? {
        val existing = dao.findUserByEmail(user.email.trim())
        if (existing != null && existing.id != user.id) return null
        val current = dao.findUserByUsername(user.username) ?: return null
        val updated = current.copy(
            name = user.name.trim(),
            email = user.email.trim(),
            birthDate = user.birthDate.trim(),
            passwordHash = newPassword.takeIf { it.isNotBlank() }?.sha256() ?: current.passwordHash
        )
        return if (dao.updateUser(updated) > 0) updated.toModel() else null
    }

    fun observeSnapshot(userId: Long): Flow<FitnessSnapshot> {
        return combine(dao.observeWorkouts(userId), dao.observeBodyMetrics(userId)) { workouts, bodyMetrics ->
            FitnessSnapshot(
                workouts = workouts.map { it.toModel() },
                bodyMetrics = bodyMetrics.map { it.toModel() }
            )
        }
    }

    suspend fun saveWorkout(log: WorkoutLog) = dao.upsertWorkout(log.toEntity())

    suspend fun deleteWorkout(id: Long, userId: Long) = dao.deleteWorkoutById(id, userId)

    suspend fun saveBodyMetric(metric: BodyMetric) = dao.upsertBodyMetric(metric.toEntity())

    suspend fun deleteBodyMetric(id: Long, userId: Long) = dao.deleteBodyMetricById(id, userId)
}

private fun String.sha256(): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
