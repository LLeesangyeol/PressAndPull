package com.example.pressandpull.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.pressandpull.model.BodyMetric
import com.example.pressandpull.ui.components.AppCard
import com.example.pressandpull.ui.components.EmptyState
import com.example.pressandpull.ui.components.PageTitle
import com.example.pressandpull.ui.components.StatTile
import com.example.pressandpull.ui.design.AppColor
import com.example.pressandpull.ui.design.AppDimen

@Composable
fun BodyScreen(
    metrics: List<BodyMetric>,
    onEdit: (BodyMetric) -> Unit,
    onDelete: (BodyMetric) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimen.PagePadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            PageTitle("인바디", "체중, 골격근량, 체지방률 변화를 기록합니다.")
        }
        if (metrics.isEmpty()) {
            item { EmptyState("아직 인바디 기록이 없습니다.") }
        } else {
            items(metrics, key = { it.id }) { metric ->
                AppCard {
                    Text(metric.date, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatTile("체중", "${metric.weightKg}", Modifier.weight(1f))
                        StatTile("골격근량", "${metric.skeletalMuscleKg}", Modifier.weight(1f))
                        StatTile("체지방", "${metric.bodyFatPercent}%", Modifier.weight(1f))
                    }
                    if (metric.memo.isNotBlank()) Text(metric.memo, color = AppColor.Muted)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = { onEdit(metric) }) { Text("수정", color = AppColor.Black) }
                        TextButton(onClick = { onDelete(metric) }) { Text("삭제", color = AppColor.Black) }
                    }
                }
            }
        }
    }
}

