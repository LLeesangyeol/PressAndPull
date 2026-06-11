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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pressandpull.model.BodyMetric
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

private enum class AppRoute(val path: String, val label: String) {
    Home("home", "홈"),
    Workouts("workouts", "기록"),
    Routine("routine", "코치"),
    Body("body", "인바디")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PressPullApp(viewModel: FitnessViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val currentUser = uiState.user
    var workoutDialog by remember { mutableStateOf<WorkoutLog?>(null) }
    var metricDialog by remember { mutableStateOf<BodyMetric?>(null) }
    var showProfile by remember { mutableStateOf(false) }

    if (currentUser == null) {
        AuthScreen(
            authError = uiState.authError,
            onClearError = viewModel::clearAuthError,
            onLogin = viewModel::login,
            onRegister = viewModel::register
        )
        return
    }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: AppRoute.Home.path

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
                    TextButton(onClick = { viewModel.logout() }) { Text("로그아웃", color = AppColor.Black) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColor.Paper)
            )
        },
        floatingActionButton = {
            when (currentRoute) {
                AppRoute.Workouts.path -> FloatingActionButton(
                    containerColor = AppColor.Black,
                    contentColor = AppColor.Paper,
                    onClick = {
                        workoutDialog = WorkoutLog(userId = currentUser.id, date = today(), exercise = "", category = "Push", sets = 3, reps = 10, weightKg = 0.0, memo = "")
                    }
                ) { Text("+", fontWeight = FontWeight.Black) }

                AppRoute.Body.path -> FloatingActionButton(
                    containerColor = AppColor.Black,
                    contentColor = AppColor.Paper,
                    onClick = {
                        metricDialog = BodyMetric(userId = currentUser.id, date = today(), weightKg = 72.0, skeletalMuscleKg = 34.0, bodyFatPercent = 18.0, memo = "")
                    }
                ) { Text("+", fontWeight = FontWeight.Black) }
            }
        },
        bottomBar = {
            BottomTabs(
                currentRoute = currentRoute,
                onSelected = { route ->
                    navController.navigate(route.path) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { padding ->
        Surface(modifier = Modifier.fillMaxSize().padding(padding), color = AppColor.Paper) {
            NavHost(navController = navController, startDestination = AppRoute.Home.path) {
                composable(AppRoute.Home.path) {
                    HomeScreen(uiState.snapshot)
                }
                composable(AppRoute.Workouts.path) {
                    WorkoutScreen(
                        workouts = uiState.snapshot.workouts,
                        onEdit = { workoutDialog = it },
                        onDelete = viewModel::deleteWorkout
                    )
                }
                composable(AppRoute.Routine.path) {
                    RoutineScreen(uiState.snapshot)
                }
                composable(AppRoute.Body.path) {
                    BodyScreen(
                        metrics = uiState.snapshot.bodyMetrics,
                        onEdit = { metricDialog = it },
                        onDelete = viewModel::deleteBodyMetric
                    )
                }
            }
        }
    }

    if (showProfile) {
        ProfileDialog(
            user = currentUser,
            onDismiss = { showProfile = false },
            onSave = { editedUser, newPassword ->
                viewModel.updateProfile(editedUser, newPassword)
                showProfile = false
            }
        )
    }

    workoutDialog?.let { draft ->
        WorkoutDialog(
            original = draft,
            onDismiss = { workoutDialog = null },
            onSave = {
                viewModel.saveWorkout(it.copy(userId = currentUser.id))
                workoutDialog = null
            }
        )
    }

    metricDialog?.let { draft ->
        BodyMetricDialog(
            original = draft,
            onDismiss = { metricDialog = null },
            onSave = {
                viewModel.saveBodyMetric(it.copy(userId = currentUser.id))
                metricDialog = null
            }
        )
    }
}

@Composable
private fun BottomTabs(currentRoute: String, onSelected: (AppRoute) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColor.Paper)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppRoute.entries.forEach { route ->
            val selected = currentRoute == route.path
            Text(
                text = route.label,
                color = if (selected) AppColor.Paper else AppColor.Black,
                fontWeight = if (selected) FontWeight.Black else FontWeight.Medium,
                modifier = Modifier
                    .background(if (selected) AppColor.Black else AppColor.Paper, androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .clickable { onSelected(route) }
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            )
        }
    }
}

private fun today(): String = LocalDate.now().toString()
