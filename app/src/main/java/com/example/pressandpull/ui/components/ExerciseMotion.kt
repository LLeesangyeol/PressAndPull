package com.example.pressandpull.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pressandpull.ui.design.AppColor
import com.example.pressandpull.ui.design.AppDimen

@Composable
fun ExerciseMotion(exercise: String, category: String, modifier: Modifier = Modifier) {
    val motion = motionType(exercise, category)
    val transition = rememberInfiniteTransition(label = "exercise-motion")
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse),
        label = "phase"
    )

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(motion.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
            Text(if (phase < 0.5f) "시작 자세" else "동작 중", color = AppColor.Muted, style = MaterialTheme.typography.labelLarge)
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(AppColor.Black, RoundedCornerShape(AppDimen.CardRadius))
                .border(1.dp, Color(0xFF282828), RoundedCornerShape(AppDimen.CardRadius))
                .padding(8.dp)
                .fillMaxSize()
        ) {
            val w = size.width
            val h = size.height
            val body = AppColor.Paper
            val shadow = Color(0xFF5F5F5F)
            val equipment = Color(0xFFBDBDBD)

            fun capsule(a: Offset, b: Offset, width: Float, color: Color = body) {
                drawLine(color, a, b, strokeWidth = width, cap = StrokeCap.Round)
            }

            fun head(center: Offset) {
                drawCircle(body, radius = 19f, center = center)
            }

            fun torso(center: Offset, width: Float, height: Float) {
                drawOval(
                    color = body,
                    topLeft = Offset(center.x - width / 2f, center.y - height / 2f),
                    size = Size(width, height)
                )
            }

            fun ghostPose(points: List<Pair<Offset, Offset>>, width: Float) {
                points.forEach { (a, b) -> capsule(a, b, width, shadow) }
            }

            drawLine(shadow, Offset(w * 0.10f, h * 0.90f), Offset(w * 0.90f, h * 0.90f), strokeWidth = 3f)

            when (motion) {
                MotionType.Squat -> {
                    val drop = phase * h * 0.18f
                    val head = Offset(w * 0.50f, h * 0.22f + drop)
                    val chest = Offset(w * 0.48f, h * 0.40f + drop)
                    val hip = Offset(w * 0.46f, h * 0.57f + drop)
                    val leftKnee = Offset(w * 0.36f, h * 0.73f + drop * 0.1f)
                    val rightKnee = Offset(w * 0.62f, h * 0.72f + drop * 0.1f)
                    val leftFoot = Offset(w * 0.27f, h * 0.88f)
                    val rightFoot = Offset(w * 0.74f, h * 0.88f)
                    val barA = Offset(w * 0.25f, h * 0.31f + drop)
                    val barB = Offset(w * 0.75f, h * 0.31f + drop)

                    ghostPose(
                        listOf(
                            Offset(w * 0.46f, h * 0.55f) to Offset(w * 0.36f, h * 0.70f),
                            Offset(w * 0.46f, h * 0.55f) to Offset(w * 0.62f, h * 0.70f)
                        ),
                        18f
                    )
                    drawLine(equipment, barA, barB, strokeWidth = 7f, cap = StrokeCap.Round)
                    capsule(chest, hip, 42f)
                    capsule(Offset(w * 0.35f, h * 0.35f + drop), Offset(w * 0.65f, h * 0.35f + drop), 18f)
                    capsule(hip, leftKnee, 24f)
                    capsule(leftKnee, leftFoot, 24f)
                    capsule(hip, rightKnee, 24f)
                    capsule(rightKnee, rightFoot, 24f)
                    capsule(leftFoot, Offset(leftFoot.x - 24f, leftFoot.y), 16f)
                    capsule(rightFoot, Offset(rightFoot.x + 24f, rightFoot.y), 16f)
                    torso(chest, 48f, 72f)
                    head(head)
                }

                MotionType.Pull -> {
                    val rise = phase * h * 0.24f
                    val head = Offset(w * 0.50f, h * 0.43f - rise)
                    val chest = Offset(w * 0.50f, h * 0.58f - rise)
                    val hip = Offset(w * 0.50f, h * 0.73f - rise)
                    val leftHand = Offset(w * 0.34f, h * 0.17f)
                    val rightHand = Offset(w * 0.66f, h * 0.17f)
                    val leftElbow = Offset(w * 0.39f, h * 0.36f - rise * 0.55f)
                    val rightElbow = Offset(w * 0.61f, h * 0.36f - rise * 0.55f)

                    drawLine(equipment, Offset(w * 0.20f, h * 0.17f), Offset(w * 0.80f, h * 0.17f), strokeWidth = 9f, cap = StrokeCap.Round)
                    capsule(leftHand, leftElbow, 20f)
                    capsule(leftElbow, chest, 22f)
                    capsule(rightHand, rightElbow, 20f)
                    capsule(rightElbow, chest, 22f)
                    capsule(chest, hip, 42f)
                    capsule(hip, Offset(w * 0.42f, h * 0.88f - rise * 0.15f), 22f)
                    capsule(hip, Offset(w * 0.58f, h * 0.88f - rise * 0.15f), 22f)
                    torso(chest, 50f, 78f)
                    head(head)
                }

                MotionType.Row -> {
                    val pull = phase * w * 0.13f
                    val head = Offset(w * 0.65f, h * 0.31f)
                    val chest = Offset(w * 0.56f, h * 0.47f)
                    val hip = Offset(w * 0.42f, h * 0.63f)
                    val hand = Offset(w * 0.74f - pull, h * 0.57f)
                    val elbow = Offset(w * 0.63f - pull * 0.45f, h * 0.52f)

                    drawLine(equipment, hand, Offset(w * 0.86f, h * 0.58f), strokeWidth = 7f, cap = StrokeCap.Round)
                    capsule(hip, chest, 44f)
                    capsule(chest, elbow, 22f)
                    capsule(elbow, hand, 20f)
                    capsule(hip, Offset(w * 0.32f, h * 0.88f), 25f)
                    capsule(hip, Offset(w * 0.58f, h * 0.88f), 25f)
                    torso(chest, 50f, 76f)
                    head(head)
                }

                MotionType.OverheadPress -> {
                    val press = phase * h * 0.22f
                    val head = Offset(w * 0.31f, h * 0.59f)
                    val chest = Offset(w * 0.46f, h * 0.66f)
                    val hip = Offset(w * 0.64f, h * 0.72f)
                    val leftHand = Offset(w * 0.43f, h * 0.43f - press)
                    val rightHand = Offset(w * 0.69f, h * 0.43f - press)
                    val leftElbow = Offset(w * 0.41f, h * 0.57f - press * 0.45f)
                    val rightElbow = Offset(w * 0.62f, h * 0.57f - press * 0.45f)

                    drawLine(equipment, Offset(w * 0.15f, h * 0.78f), Offset(w * 0.85f, h * 0.78f), strokeWidth = 8f, cap = StrokeCap.Round)
                    drawLine(equipment, leftHand, rightHand, strokeWidth = 7f, cap = StrokeCap.Round)
                    capsule(chest, hip, 46f)
                    capsule(chest, leftElbow, 22f)
                    capsule(leftElbow, leftHand, 20f)
                    capsule(chest, rightElbow, 22f)
                    capsule(rightElbow, rightHand, 20f)
                    capsule(hip, Offset(w * 0.75f, h * 0.78f), 24f)
                    torso(chest, 55f, 76f)
                    head(head)
                }

                MotionType.BenchPress -> {
                    val press = phase * h * 0.18f
                    val benchY = h * 0.66f
                    val head = Offset(w * 0.22f, h * 0.58f)
                    val chest = Offset(w * 0.46f, h * 0.60f)
                    val hip = Offset(w * 0.68f, h * 0.62f)
                    val leftHand = Offset(w * 0.40f, h * 0.78f - press)
                    val rightHand = Offset(w * 0.54f, h * 0.78f - press)
                    val leftElbow = Offset(w * 0.38f, h * 0.68f - press * 0.4f)
                    val rightElbow = Offset(w * 0.54f, h * 0.68f - press * 0.4f)
                    val knee = Offset(w * 0.78f, h * 0.78f)
                    val foot = Offset(w * 0.86f, h * 0.90f)

                    drawLine(equipment, Offset(w * 0.10f, benchY + 14f), Offset(w * 0.90f, benchY + 14f), strokeWidth = 18f, cap = StrokeCap.Round)
                    drawLine(equipment, leftHand, rightHand, strokeWidth = 8f, cap = StrokeCap.Round)
                    capsule(chest, hip, 46f)
                    capsule(chest, leftElbow, 20f)
                    capsule(leftElbow, leftHand, 18f)
                    capsule(chest, rightElbow, 20f)
                    capsule(rightElbow, rightHand, 18f)
                    capsule(hip, knee, 24f)
                    capsule(knee, foot, 22f)
                    torso(chest, 76f, 50f)
                    head(head)
                }

                MotionType.InclinePress -> {
                    val press = phase * h * 0.16f
                    val hip = Offset(w * 0.66f, h * 0.74f)
                    val chest = Offset(w * 0.46f, h * 0.54f)
                    val head = Offset(w * 0.34f, h * 0.40f)
                    val leftHand = Offset(w * 0.38f, h * 0.30f - press)
                    val rightHand = Offset(w * 0.52f, h * 0.26f - press)
                    val leftElbow = Offset(w * 0.38f, h * 0.46f - press * 0.4f)
                    val rightElbow = Offset(w * 0.50f, h * 0.42f - press * 0.4f)
                    val knee = Offset(w * 0.78f, h * 0.86f)
                    val foot = Offset(w * 0.88f, h * 0.92f)

                    drawLine(equipment, Offset(w * 0.30f, h * 0.92f), Offset(w * 0.70f, h * 0.50f), strokeWidth = 16f, cap = StrokeCap.Round)
                    drawLine(equipment, leftHand, rightHand, strokeWidth = 8f, cap = StrokeCap.Round)
                    capsule(chest, hip, 44f)
                    capsule(chest, leftElbow, 20f)
                    capsule(leftElbow, leftHand, 18f)
                    capsule(chest, rightElbow, 20f)
                    capsule(rightElbow, rightHand, 18f)
                    capsule(hip, knee, 24f)
                    capsule(knee, foot, 22f)
                    torso(chest, 70f, 48f)
                    head(head)
                }
            }
        }
        Text(motion.guide, color = AppColor.Muted, style = MaterialTheme.typography.bodySmall)
    }
}

private enum class MotionType(val title: String, val guide: String) {
    Squat("스쿼트 패턴", "복압을 먼저 잡고, 엉덩이와 무릎을 함께 접은 뒤 바닥을 밀며 올라옵니다."),
    Pull("풀업 패턴", "완전히 매달린 상태에서 시작하고, 팔꿈치를 아래로 당긴 뒤 천천히 내려옵니다."),
    Row("로우 패턴", "힌지 자세를 고정하고, 갈비뼈를 닫은 채 팔꿈치를 뒤로 당깁니다."),
    OverheadPress("오버헤드프레스 패턴", "견갑을 고정하고, 바를 머리 위로 곧게 밀어 올린 뒤 팔꿈치를 잠급니다."),
    BenchPress("벤치프레스 패턴", "벤치에 누워 견갑을 모으고, 가슴까지 내린 뒤 바를 수직으로 밀어 올립니다."),
    InclinePress("인클라인 프레스 패턴", "비스듬한 벤치에 기대어 윗가슴까지 내린 뒤, 사선 위쪽으로 밀어 올립니다.")
}

private fun motionType(exercise: String, category: String): MotionType {
    val text = "$exercise $category".lowercase()
    return when {
        "스쿼트" in text || "런지" in text || "하체" in text || "squat" in text || "leg" in text -> MotionType.Squat
        "풀업" in text || "랫풀다운" in text || "당기기" in text || "pull" in text -> MotionType.Pull
        "로우" in text || "row" in text -> MotionType.Row
        "인클라인" in text || "incline" in text -> MotionType.InclinePress
        "벤치" in text || "bench" in text -> MotionType.BenchPress
        else -> MotionType.OverheadPress
    }
}

