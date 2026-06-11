package com.example.pressandpull.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.pressandpull.model.UserAccount
import com.example.pressandpull.ui.components.CleanInput
import com.example.pressandpull.ui.components.PasswordInput
import com.example.pressandpull.ui.design.AppColor

@Composable
fun ProfileDialog(
    user: UserAccount,
    onDismiss: () -> Unit,
    onSave: (UserAccount, String) -> Unit
) {
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var birthDate by remember { mutableStateOf(user.birthDate) }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("프로필 수정") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("아이디: ${user.username}", color = AppColor.Muted)
                CleanInput("이름", name) { name = it }
                CleanInput("이메일", email) { email = it }
                CleanInput("생년월일", birthDate) { birthDate = it }
                PasswordInput("새 비밀번호", password) { password = it }
                Text("비밀번호를 바꾸지 않으려면 비워두세요.", color = AppColor.Muted, style = MaterialTheme.typography.labelMedium)
                if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = AppColor.Black, contentColor = AppColor.Paper),
                onClick = {
                    if (name.isBlank() || email.isBlank() || birthDate.isBlank()) {
                        error = "이름, 이메일, 생년월일을 입력하세요."
                        return@Button
                    }
                    if (password.isNotBlank() && password.length < 4) {
                        error = "새 비밀번호는 4자 이상이어야 합니다."
                        return@Button
                    }
                    onSave(user.copy(name = name, email = email, birthDate = birthDate), password)
                }
            ) { Text("저장") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("취소", color = AppColor.Black) } }
    )
}
