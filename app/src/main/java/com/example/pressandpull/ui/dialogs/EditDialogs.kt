package com.example.pressandpull.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pressandpull.model.BodyMetric
import com.example.pressandpull.model.WorkoutLog
import com.example.pressandpull.ui.components.CleanInput
import com.example.pressandpull.ui.components.NumberInput
import com.example.pressandpull.ui.design.AppColor

@Composable
fun WorkoutDialog(original: WorkoutLog, onDismiss: () -> Unit, onSave: (WorkoutLog) -> Unit) {
    var date by remember { mutableStateOf(original.date) }
    var exercise by remember { mutableStateOf(original.exercise) }
    var category by remember { mutableStateOf(original.category) }
    var sets by remember { mutableStateOf(original.sets.toString()) }
    var reps by remember { mutableStateOf(original.reps.toString()) }
    var weight by remember { mutableStateOf(original.weightKg.toString()) }
    var memo by remember { mutableStateOf(original.memo) }
    var error by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (original.id == 0L) "운동 추가" else "운동 수정") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CleanInput("날짜", date) { date = it }
                CleanInput("운동명", exercise) { exercise = it }
                CategoryChips(category) { category = it }
                NumberInput("세트", sets) { sets = it }
                NumberInput("반복 횟수", reps) { reps = it }
                NumberInput("중량 kg", weight) { weight = it }
                CleanInput("메모", memo) { memo = it }
                if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = AppColor.Black, contentColor = AppColor.Paper),
                onClick = {
                    val parsedSets = sets.toIntOrNull()
                    val parsedReps = reps.toIntOrNull()
                    val parsedWeight = weight.toDoubleOrNull()
                    if (exercise.isBlank() || parsedSets == null || parsedReps == null || parsedWeight == null || parsedSets <= 0 || parsedReps <= 0 || parsedWeight < 0) {
                        error = "운동명, 세트, 반복 횟수, 중량을 올바르게 입력하세요."
                    } else {
                        onSave(original.copy(date = date, exercise = exercise, category = category, sets = parsedSets, reps = parsedReps, weightKg = parsedWeight, memo = memo))
                    }
                }
            ) { Text("저장") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("취소", color = AppColor.Black) } }
    )
}

@Composable
fun BodyMetricDialog(original: BodyMetric, onDismiss: () -> Unit, onSave: (BodyMetric) -> Unit) {
    var date by remember { mutableStateOf(original.date) }
    var weight by remember { mutableStateOf(original.weightKg.toString()) }
    var muscle by remember { mutableStateOf(original.skeletalMuscleKg.toString()) }
    var fat by remember { mutableStateOf(original.bodyFatPercent.toString()) }
    var memo by remember { mutableStateOf(original.memo) }
    var error by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (original.id == 0L) "인바디 추가" else "인바디 수정") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CleanInput("날짜", date) { date = it }
                NumberInput("체중 kg", weight) { weight = it }
                NumberInput("골격근량 kg", muscle) { muscle = it }
                NumberInput("체지방률 %", fat) { fat = it }
                CleanInput("메모", memo) { memo = it }
                if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = AppColor.Black, contentColor = AppColor.Paper),
                onClick = {
                    val parsedWeight = weight.toDoubleOrNull()
                    val parsedMuscle = muscle.toDoubleOrNull()
                    val parsedFat = fat.toDoubleOrNull()
                    if (parsedWeight == null || parsedMuscle == null || parsedFat == null || parsedWeight <= 0 || parsedMuscle < 0 || parsedFat < 0) {
                        error = "체중, 골격근량, 체지방률을 올바르게 입력하세요."
                    } else {
                        onSave(original.copy(date = date, weightKg = parsedWeight, skeletalMuscleKg = parsedMuscle, bodyFatPercent = parsedFat, memo = memo))
                    }
                }
            ) { Text("저장") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("취소", color = AppColor.Black) } }
    )
}

@Composable
private fun CategoryChips(selected: String, onSelected: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
        listOf("Push", "Pull", "Legs", "Core").forEach { item ->
            FilterChip(selected = selected == item, onClick = { onSelected(item) }, label = { Text(item) })
        }
    }
}
