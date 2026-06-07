package com.example.pressandpull.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pressandpull.data.FitnessDatabase
import com.example.pressandpull.model.UserAccount
import com.example.pressandpull.ui.components.AppCard
import com.example.pressandpull.ui.components.CleanInput
import com.example.pressandpull.ui.components.PasswordInput
import com.example.pressandpull.ui.components.PrimaryButton
import com.example.pressandpull.ui.design.AppColor

@Composable
fun AuthScreen(database: FitnessDatabase, onLoggedIn: (UserAccount) -> Unit) {
    var isSignup by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.Black)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(18.dp), modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("PRESS & PULL", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Black, color = AppColor.Paper)
                Text("운동 기록, 루틴 추천, 동작 코칭을 한 곳에서 관리합니다.", color = AppColor.Line)
            }
            AppCard {
                Text(if (isSignup) "회원가입" else "로그인", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black)
                Text(if (isSignup) "개인 정보를 입력해 계정을 만듭니다." else "아이디와 비밀번호를 입력하세요.", color = AppColor.Muted)
                CleanInput("아이디", username) { username = it }
                if (isSignup) {
                    CleanInput("이름", name) { name = it }
                    CleanInput("이메일", email) { email = it }
                    CleanInput("생년월일", birthDate) { birthDate = it }
                }
                PasswordInput("비밀번호", password) { password = it }
                if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(2.dp))
                PrimaryButton(if (isSignup) "가입하기" else "로그인") {
                    error = ""
                    if (username.isBlank() || password.length < 4 || (isSignup && (name.isBlank() || email.isBlank() || birthDate.isBlank()))) {
                        error = "모든 항목을 입력하세요. 비밀번호는 4자 이상이어야 합니다."
                        return@PrimaryButton
                    }
                    val nextUser = if (isSignup) {
                        database.register(username, name, email, birthDate, password)
                    } else {
                        database.login(username, password)
                    }
                    if (nextUser == null) {
                        error = if (isSignup) "이미 사용 중인 아이디 또는 이메일입니다." else "아이디 또는 비밀번호가 올바르지 않습니다."
                    } else {
                        onLoggedIn(nextUser)
                    }
                }
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = { isSignup = !isSignup; error = "" }) {
                    Text(if (isSignup) "로그인으로 돌아가기" else "회원가입", color = AppColor.Black)
                }
                Text("데모 계정: 아이디 demo / 비밀번호 1234", color = AppColor.Muted, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

