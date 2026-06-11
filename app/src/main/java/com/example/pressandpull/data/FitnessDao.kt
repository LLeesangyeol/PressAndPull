package com.example.pressandpull.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessDao {
    @Query("SELECT COUNT(*) FROM users")
    suspend fun userCount(): Int

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun findUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username AND passwordHash = :passwordHash LIMIT 1")
    suspend fun login(username: String, passwordHash: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity): Int

    @Query("SELECT * FROM workout_logs WHERE userId = :userId ORDER BY date DESC, id DESC")
    fun observeWorkouts(userId: Long): Flow<List<WorkoutLogEntity>>

    @Query("SELECT * FROM body_metrics WHERE userId = :userId ORDER BY date DESC, id DESC")
    fun observeBodyMetrics(userId: Long): Flow<List<BodyMetricEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWorkout(log: WorkoutLogEntity)

    @Delete
    suspend fun deleteWorkout(log: WorkoutLogEntity)

    @Query("DELETE FROM workout_logs WHERE id = :id AND userId = :userId")
    suspend fun deleteWorkoutById(id: Long, userId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBodyMetric(metric: BodyMetricEntity)

    @Delete
    suspend fun deleteBodyMetric(metric: BodyMetricEntity)

    @Query("DELETE FROM body_metrics WHERE id = :id AND userId = :userId")
    suspend fun deleteBodyMetricById(id: Long, userId: Long)
}
