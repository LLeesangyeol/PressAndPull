package com.example.pressandpull

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.pressandpull.ui.screens.AuthScreen
import com.example.pressandpull.ui.theme.PressAndPullTheme
import org.junit.Rule
import org.junit.Test

class AuthScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun authScreen_showsDemoAccountAndLoginAction() {
        composeRule.setContent {
            PressAndPullTheme {
                AuthScreen(
                    authError = null,
                    onClearError = {},
                    onLogin = { _, _ -> },
                    onRegister = { _, _, _, _, _ -> }
                )
            }
        }

        composeRule.onNodeWithText("PRESS & PULL").assertIsDisplayed()
        composeRule.onAllNodesWithText("로그인")[0].assertIsDisplayed()
        composeRule.onNodeWithText("데모 계정: 아이디 demo / 비밀번호 1234").assertIsDisplayed()
    }

    @Test
    fun authScreen_emptyLogin_showsValidationMessage() {
        composeRule.setContent {
            PressAndPullTheme {
                AuthScreen(
                    authError = null,
                    onClearError = {},
                    onLogin = { _, _ -> },
                    onRegister = { _, _, _, _, _ -> }
                )
            }
        }

        composeRule.onAllNodesWithText("로그인").filter(hasClickAction())[0].performClick()
        composeRule.onNodeWithText("모든 항목을 입력하세요. 비밀번호는 4자 이상이어야 합니다.").assertIsDisplayed()
    }
}
