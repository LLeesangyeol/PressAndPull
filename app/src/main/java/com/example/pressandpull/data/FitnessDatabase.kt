package com.example.pressandpull.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pressandpull.model.BodyMetric
import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.model.UserAccount
import com.example.pressandpull.model.WorkoutLog
import java.security.MessageDigest

class FitnessDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                birthDate TEXT NOT NULL,
                passwordHash TEXT NOT NULL,
                createdAt TEXT NOT NULL
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE workout_logs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER NOT NULL,
                date TEXT NOT NULL,
                exercise TEXT NOT NULL,
                category TEXT NOT NULL,
                sets INTEGER NOT NULL,
                reps INTEGER NOT NULL,
                weightKg REAL NOT NULL,
                memo TEXT NOT NULL,
                FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE body_metrics (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER NOT NULL,
                date TEXT NOT NULL,
                weightKg REAL NOT NULL,
                skeletalMuscleKg REAL NOT NULL,
                bodyFatPercent REAL NOT NULL,
                memo TEXT NOT NULL,
                FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        seed(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS body_metrics")
        db.execSQL("DROP TABLE IF EXISTS workout_logs")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun register(username: String, name: String, email: String, birthDate: String, password: String): UserAccount? {
        if (findUserByUsername(username) != null || findUserByEmail(email) != null) return null
        val id = writableDatabase.insert("users", null, userValues(username, name, email, birthDate, password))
        return if (id > 0) UserAccount(id, username.trim(), name.trim(), email.trim(), birthDate.trim()) else null
    }

    fun login(username: String, password: String): UserAccount? {
        return readableDatabase.query(
            "users",
            arrayOf("id", "username", "name", "email", "birthDate"),
            "username = ? AND passwordHash = ?",
            arrayOf(username.trim(), password.sha256()),
            null,
            null,
            null
        ).use { cursor ->
            if (!cursor.moveToFirst()) return@use null
            cursor.toUser()
        }
    }

    fun updateUser(user: UserAccount, newPassword: String): UserAccount? {
        if (findUserByEmail(user.email)?.id?.let { it != user.id } == true) return null
        val values = ContentValues().apply {
            put("name", user.name.trim())
            put("email", user.email.trim())
            put("birthDate", user.birthDate.trim())
            if (newPassword.isNotBlank()) put("passwordHash", newPassword.sha256())
        }
        val updated = writableDatabase.update("users", values, "id = ?", arrayOf(user.id.toString()))
        return if (updated > 0) findUserByUsername(user.username) else null
    }

    fun snapshot(userId: Long): FitnessSnapshot = FitnessSnapshot(
        workouts = readWorkouts(userId),
        bodyMetrics = readBodyMetrics(userId)
    )

    fun saveWorkout(log: WorkoutLog) {
        writableDatabase.insert("workout_logs", null, log.toValues())
    }

    fun updateWorkout(log: WorkoutLog) {
        writableDatabase.update("workout_logs", log.toValues(), "id = ? AND userId = ?", arrayOf(log.id.toString(), log.userId.toString()))
    }

    fun deleteWorkout(id: Long, userId: Long) {
        writableDatabase.delete("workout_logs", "id = ? AND userId = ?", arrayOf(id.toString(), userId.toString()))
    }

    fun saveBodyMetric(metric: BodyMetric) {
        writableDatabase.insert("body_metrics", null, metric.toValues())
    }

    fun updateBodyMetric(metric: BodyMetric) {
        writableDatabase.update("body_metrics", metric.toValues(), "id = ? AND userId = ?", arrayOf(metric.id.toString(), metric.userId.toString()))
    }

    fun deleteBodyMetric(id: Long, userId: Long) {
        writableDatabase.delete("body_metrics", "id = ? AND userId = ?", arrayOf(id.toString(), userId.toString()))
    }

    private fun readWorkouts(userId: Long): List<WorkoutLog> {
        return readableDatabase.query("workout_logs", null, "userId = ?", arrayOf(userId.toString()), null, null, "date DESC, id DESC").use { cursor ->
            buildList {
                while (cursor.moveToNext()) {
                    add(
                        WorkoutLog(
                            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                            userId = cursor.getLong(cursor.getColumnIndexOrThrow("userId")),
                            date = cursor.getString(cursor.getColumnIndexOrThrow("date")),
                            exercise = cursor.getString(cursor.getColumnIndexOrThrow("exercise")),
                            category = cursor.getString(cursor.getColumnIndexOrThrow("category")),
                            sets = cursor.getInt(cursor.getColumnIndexOrThrow("sets")),
                            reps = cursor.getInt(cursor.getColumnIndexOrThrow("reps")),
                            weightKg = cursor.getDouble(cursor.getColumnIndexOrThrow("weightKg")),
                            memo = cursor.getString(cursor.getColumnIndexOrThrow("memo"))
                        )
                    )
                }
            }
        }
    }

    private fun readBodyMetrics(userId: Long): List<BodyMetric> {
        return readableDatabase.query("body_metrics", null, "userId = ?", arrayOf(userId.toString()), null, null, "date DESC, id DESC").use { cursor ->
            buildList {
                while (cursor.moveToNext()) {
                    add(
                        BodyMetric(
                            id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                            userId = cursor.getLong(cursor.getColumnIndexOrThrow("userId")),
                            date = cursor.getString(cursor.getColumnIndexOrThrow("date")),
                            weightKg = cursor.getDouble(cursor.getColumnIndexOrThrow("weightKg")),
                            skeletalMuscleKg = cursor.getDouble(cursor.getColumnIndexOrThrow("skeletalMuscleKg")),
                            bodyFatPercent = cursor.getDouble(cursor.getColumnIndexOrThrow("bodyFatPercent")),
                            memo = cursor.getString(cursor.getColumnIndexOrThrow("memo"))
                        )
                    )
                }
            }
        }
    }

    private fun findUserByEmail(email: String): UserAccount? = findUser("email = ?", email.trim())

    private fun findUserByUsername(username: String): UserAccount? = findUser("username = ?", username.trim())

    private fun findUser(selection: String, value: String): UserAccount? {
        return readableDatabase.query("users", arrayOf("id", "username", "name", "email", "birthDate"), selection, arrayOf(value), null, null, null).use { cursor ->
            if (!cursor.moveToFirst()) return@use null
            cursor.toUser()
        }
    }

    private fun android.database.Cursor.toUser(): UserAccount = UserAccount(
        id = getLong(getColumnIndexOrThrow("id")),
        username = getString(getColumnIndexOrThrow("username")),
        name = getString(getColumnIndexOrThrow("name")),
        email = getString(getColumnIndexOrThrow("email")),
        birthDate = getString(getColumnIndexOrThrow("birthDate"))
    )

    private fun seed(db: SQLiteDatabase) {
        val userId = db.insert("users", null, userValues("demo", "데모 리프터", "demo@presspull.local", "1998-01-01", "1234"))
        db.insert("workout_logs", null, WorkoutLog(userId = userId, date = "2026-06-01", exercise = "벤치프레스", category = "밀기", sets = 4, reps = 8, weightKg = 60.0, memo = "가슴 자극 좋음").toValues())
        db.insert("workout_logs", null, WorkoutLog(userId = userId, date = "2026-06-03", exercise = "풀업", category = "당기기", sets = 4, reps = 6, weightKg = 0.0, memo = "맨몸").toValues())
        db.insert("body_metrics", null, BodyMetric(userId = userId, date = "2026-06-04", weightKg = 72.4, skeletalMuscleKg = 34.1, bodyFatPercent = 17.8, memo = "아침 공복 측정").toValues())
    }

    companion object {
        private const val DB_NAME = "press_and_pull.db"
        private const val DB_VERSION = 5
    }
}

private fun userValues(username: String, name: String, email: String, birthDate: String, password: String) = ContentValues().apply {
    put("username", username.trim())
    put("name", name.trim())
    put("email", email.trim())
    put("birthDate", birthDate.trim())
    put("passwordHash", password.sha256())
    put("createdAt", "2026-06-07")
}

private fun WorkoutLog.toValues() = ContentValues().apply {
    put("userId", userId)
    put("date", date)
    put("exercise", exercise)
    put("category", category)
    put("sets", sets)
    put("reps", reps)
    put("weightKg", weightKg)
    put("memo", memo)
}

private fun BodyMetric.toValues() = ContentValues().apply {
    put("userId", userId)
    put("date", date)
    put("weightKg", weightKg)
    put("skeletalMuscleKg", skeletalMuscleKg)
    put("bodyFatPercent", bodyFatPercent)
    put("memo", memo)
}

private fun String.sha256(): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

