package com.example.pressandpull.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.ui.components.AppCard
import com.example.pressandpull.ui.components.ExerciseMotion
import com.example.pressandpull.ui.components.PageTitle
import com.example.pressandpull.ui.design.AppColor
import com.example.pressandpull.ui.design.AppDimen
import com.example.pressandpull.ui.recommendRoutine

@Composable
fun RoutineScreen(snapshot: FitnessSnapshot) {
    val routines = remember(snapshot) { recommendRoutine(snapshot) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimen.PagePadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            PageTitle("코치", "추천 루틴과 운동별 동작 안내를 한 번에 확인합니다.")
        }
        items(routines) { routine ->
            AppCard {
                Text(routine.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                Text(routine.focus, color = AppColor.Muted)
                routine.exercises.take(2).forEachIndexed { index, exercise ->
                    Text("동작 ${index + 1}: $exercise", fontWeight = FontWeight.Bold)
                    ExerciseMotion(exercise = exercise, category = routine.title)
                }
                Text("전체 구성", fontWeight = FontWeight.Black)
                routine.exercises.forEach { Text("- $it") }
            }
        }
    }
}
