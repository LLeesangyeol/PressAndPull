package com.example.pressandpull.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pressandpull.data.FitnessDatabase
import com.example.pressandpull.model.BodyMetric
import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.model.UserAccount
import com.example.pressandpull.model.WorkoutLog
import com.example.pressandpull.ui.design.AppColor
import com.example.pressandpull.ui.dialogs.BodyMetricDialog
import com.example.pressandpull.ui.dialogs.ProfileDialog
import com.example.pressandpull.ui.dialogs.WorkoutDialog
import com.example.pressandpull.ui.screens.AuthScreen
import com.example.pressandpull.ui.screens.BodyScreen
import com.example.pressandpull.ui.screens.HomeScreen
import com.example.pressandpull.ui.screens.RoutineScreen
import com.example.pressandpull.ui.screens.WorkoutScreen
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PressPullApp(database: FitnessDatabase) {
    var user by remember { mutableStateOf<UserAccount?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var snapshot by remember { mutableStateOf(FitnessSnapshot()) }
    var workoutDialog by remember { mutableStateOf<WorkoutLog?>(null) }
    var metricDialog by remember { mutableStateOf<BodyMetric?>(null) }
    var showProfile by remember { mutableStateOf(false) }

    fun reload(currentUser: UserAccount? = user) {
        snapshot = currentUser?.let { database.snapshot(it.id) } ?: FitnessSnapshot()
    }

    LaunchedEffect(user) { reload(user) }

    if (user == null) {
        AuthScreen(database = database) {
            user = it
            selectedTab = 0
        }
        return
    }

    val currentUser = user ?: return

    Scaffold(
        containerColor = AppColor.Paper,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Press & Pull", fontWeight = FontWeight.Black, color = AppColor.Black)
                        Text("${currentUser.name} 님", style = MaterialTheme.typography.labelMedium, color = AppColor.Muted)
                    }
                },
                actions = {
                    TextButton(onClick = { showProfile = true }) { Text("프로필", color = AppColor.Black) }
                    TextButton(onClick = { user = null }) { Text("로그아웃", color = AppColor.Black) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColor.Paper)
            )
        },
        floatingActionButton = {
            when (selectedTab) {
                1 -> FloatingActionButton(
                    containerColor = AppColor.Black,
                    contentColor = AppColor.Paper,
                    onClick = {
                        workoutDialog = WorkoutLog(userId = currentUser.id, date = today(), exercise = "", category = "밀기", sets = 3, reps = 10, weightKg = 0.0, memo = "")
                    }
                ) { Text("+", fontWeight = FontWeight.Black) }
                3 -> FloatingActionButton(
                    containerColor = AppColor.Black,
                    contentColor = AppColor.Paper,
                    onClick = {
                        metricDialog = BodyMetric(userId = currentUser.id, date = today(), weightKg = 72.0, skeletalMuscleKg = 34.0, bodyFatPercent = 18.0, memo = "")
                    }
                ) { Text("+", fontWeight = FontWeight.Black) }
            }
        },
        bottomBar = {
            BottomTabs(selectedTab = selectedTab, onSelected = { selectedTab = it })
        }
    ) { padding ->
        Surface(modifier = Modifier.fillMaxSize().padding(padding), color = AppColor.Paper) {
            when (selectedTab) {
                0 -> HomeScreen(snapshot)
                1 -> WorkoutScreen(
                    workouts = snapshot.workouts,
                    onEdit = { workoutDialog = it },
                    onDelete = {
                        database.deleteWorkout(it.id, currentUser.id)
                        reload()
                    }
                )
                2 -> RoutineScreen(snapshot)
                3 -> BodyScreen(
                    metrics = snapshot.bodyMetrics,
                    onEdit = { metricDialog = it },
                    onDelete = {
                        database.deleteBodyMetric(it.id, currentUser.id)
                        reload()
                    }
                )
            }
        }
    }

    if (showProfile) {
        ProfileDialog(
            user = currentUser,
            onDismiss = { showProfile = false },
            onSave = { editedUser, newPassword ->
                val updated = database.updateUser(editedUser, newPassword)
                if (updated != null) user = updated
                showProfile = false
            }
        )
    }

    workoutDialog?.let { draft ->
        WorkoutDialog(
            original = draft,
            onDismiss = { workoutDialog = null },
            onSave = {
                if (it.id == 0L) database.saveWorkout(it.copy(userId = currentUser.id)) else database.updateWorkout(it.copy(userId = currentUser.id))
                workoutDialog = null
                reload()
            }
        )
    }

    metricDialog?.let { draft ->
        BodyMetricDialog(
            original = draft,
            onDismiss = { metricDialog = null },
            onSave = {
                if (it.id == 0L) database.saveBodyMetric(it.copy(userId = currentUser.id)) else database.updateBodyMetric(it.copy(userId = currentUser.id))
                metricDialog = null
                reload()
            }
        )
    }
}

@Composable
private fun BottomTabs(selectedTab: Int, onSelected: (Int) -> Unit) {
    val labels = listOf("홈", "기록", "코치", "인바디")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColor.Paper)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        labels.forEachIndexed { index, label ->
            val selected = selectedTab == index
            Text(
                text = label,
                color = if (selected) AppColor.Paper else AppColor.Black,
                fontWeight = if (selected) FontWeight.Black else FontWeight.Medium,
                modifier = Modifier
                    .background(if (selected) AppColor.Black else AppColor.Paper, androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .clickable { onSelected(index) }
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            )
        }
    }
}

private fun today(): String = LocalDate.now().toString()

