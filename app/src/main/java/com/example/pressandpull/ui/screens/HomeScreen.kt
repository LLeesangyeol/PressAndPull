package com.example.pressandpull.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.ui.components.AppCard
import com.example.pressandpull.ui.components.ExerciseMotion
import com.example.pressandpull.ui.components.PageTitle
import com.example.pressandpull.ui.components.StatRow
import com.example.pressandpull.ui.design.AppColor
import com.example.pressandpull.ui.design.AppDimen
import com.example.pressandpull.ui.recommendRoutine

@Composable
fun HomeScreen(snapshot: FitnessSnapshot) {
    val routines = recommendRoutine(snapshot)
    val topRoutine = routines.first()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimen.PagePadding),
        verticalArrangement = Arrangement.spacedBy(AppDimen.CardPadding)
    ) {
        item {
            PageTitle("홈", "오늘 추천 루틴과 주요 운동 지표를 빠르게 확인하세요.")
        }
        item {
            AppCard(inverted = true) {
                Text("오늘의 1순위 루틴", color = AppColor.Paper, style = MaterialTheme.typography.labelLarge)
                Text(topRoutine.title, color = AppColor.Paper, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
                Text(topRoutine.focus, color = AppColor.Line)
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(AppDimen.CardPadding)) {
                StatRow("운동 기록", "${snapshot.workouts.size}", "총 볼륨", "${snapshot.totalVolume} kg")
                StatRow("루틴 점수", "${snapshot.strengthScore}", "체지방률", snapshot.latestBodyMetric?.let { "${it.bodyFatPercent}%" } ?: "-")
            }
        }
        item {
            AppCard {
                Text("추천 루틴 미리보기", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                routines.take(3).forEachIndexed { index, routine ->
                    Text("${index + 1}. ${routine.title} - ${routine.focus}", color = if (index == 0) AppColor.Black else AppColor.Muted)
                }
            }
        }
        item {
            AppCard {
                Text("주요 동작 1", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                ExerciseMotion(exercise = topRoutine.exercises.getOrElse(0) { topRoutine.title }, category = topRoutine.title)
            }
        }
        item {
            AppCard {
                Text("주요 동작 2", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                ExerciseMotion(exercise = topRoutine.exercises.getOrElse(1) { topRoutine.title }, category = topRoutine.title)
            }
        }
    }
}
