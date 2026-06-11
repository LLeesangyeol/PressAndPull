package com.example.pressandpull.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pressandpull.model.WorkoutLog
import com.example.pressandpull.ui.components.AppCard
import com.example.pressandpull.ui.components.EmptyState
import com.example.pressandpull.ui.components.ExerciseMotion
import com.example.pressandpull.ui.components.PageTitle
import com.example.pressandpull.ui.design.AppColor
import com.example.pressandpull.ui.design.AppDimen

@Composable
fun WorkoutScreen(
    workouts: List<WorkoutLog>,
    onEdit: (WorkoutLog) -> Unit,
    onDelete: (WorkoutLog) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimen.PagePadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            PageTitle("운동 기록", "세트, 반복, 중량을 남기고 동작 안내까지 확인합니다.")
        }
        if (workouts.isEmpty()) {
            item { EmptyState("아직 운동 기록이 없습니다.") }
        } else {
            items(workouts, key = { it.id }) { workout ->
                AppCard {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(Modifier.weight(1f)) {
                            Text(workout.exercise, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                            Text("${workout.date} | ${workout.category}", color = AppColor.Muted)
                        }
                        Text("${workout.volume} kg", fontWeight = FontWeight.Black)
                    }
                    Text("${workout.sets}세트 x ${workout.reps}회 x ${workout.weightKg} kg")
                    if (workout.memo.isNotBlank()) Text(workout.memo, color = AppColor.Muted)
                    ExerciseMotion(exercise = workout.exercise, category = workout.category)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = { onEdit(workout) }) { Text("수정", color = AppColor.Black) }
                        TextButton(onClick = { onDelete(workout) }) { Text("삭제", color = AppColor.Black) }
                    }
                }
            }
        }
    }
}
