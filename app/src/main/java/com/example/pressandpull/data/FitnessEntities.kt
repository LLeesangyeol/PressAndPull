package com.example.pressandpull.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.pressandpull.model.BodyMetric
import com.example.pressandpull.model.UserAccount
import com.example.pressandpull.model.WorkoutLog

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val name: String,
    val email: String,
    val birthDate: String,
    val passwordHash: String,
    val createdAt: String
) {
    fun toModel(): UserAccount = UserAccount(
        id = id,
        username = username,
        name = name,
        email = email,
        birthDate = birthDate
    )
}

@Entity(
    tableName = "workout_logs",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class WorkoutLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val date: String,
    val exercise: String,
    val category: String,
    val sets: Int,
    val reps: Int,
    val weightKg: Double,
    val memo: String
) {
    fun toModel(): WorkoutLog = WorkoutLog(
        id = id,
        userId = userId,
        date = date,
        exercise = exercise,
        category = category,
        sets = sets,
        reps = reps,
        weightKg = weightKg,
        memo = memo
    )
}

@Entity(
    tableName = "body_metrics",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class BodyMetricEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val date: String,
    val weightKg: Double,
    val skeletalMuscleKg: Double,
    val bodyFatPercent: Double,
    val memo: String
) {
    fun toModel(): BodyMetric = BodyMetric(
        id = id,
        userId = userId,
        date = date,
        weightKg = weightKg,
        skeletalMuscleKg = skeletalMuscleKg,
        bodyFatPercent = bodyFatPercent,
        memo = memo
    )
}

fun WorkoutLog.toEntity(): WorkoutLogEntity = WorkoutLogEntity(
    id = id,
    userId = userId,
    date = date,
    exercise = exercise,
    category = category,
    sets = sets,
    reps = reps,
    weightKg = weightKg,
    memo = memo
)

fun BodyMetric.toEntity(): BodyMetricEntity = BodyMetricEntity(
    id = id,
    userId = userId,
    date = date,
    weightKg = weightKg,
    skeletalMuscleKg = skeletalMuscleKg,
    bodyFatPercent = bodyFatPercent,
    memo = memo
)
