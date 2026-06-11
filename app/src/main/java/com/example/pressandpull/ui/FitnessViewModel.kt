package com.example.pressandpull.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pressandpull.data.FitnessRepository
import com.example.pressandpull.model.BodyMetric
import com.example.pressandpull.model.FitnessSnapshot
import com.example.pressandpull.model.UserAccount
import com.example.pressandpull.model.WorkoutLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FitnessUiState(
    val user: UserAccount? = null,
    val snapshot: FitnessSnapshot = FitnessSnapshot(),
    val authError: String? = null
)

@HiltViewModel
class FitnessViewModel @Inject constructor(
    private val repository: FitnessRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FitnessUiState())
    val uiState: StateFlow<FitnessUiState> = _uiState.asStateFlow()

    private var snapshotJob: Job? = null

    init {
        viewModelScope.launch {
            repository.seedIfNeeded()
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            if (user == null) {
                _uiState.update { it.copy(authError = "아이디 또는 비밀번호가 올바르지 않습니다.") }
            } else {
                setUser(user)
            }
        }
    }

    fun register(username: String, name: String, email: String, birthDate: String, password: String) {
        viewModelScope.launch {
            val user = repository.register(username, name, email, birthDate, password)
            if (user == null) {
                _uiState.update { it.copy(authError = "이미 사용 중인 아이디 또는 이메일입니다.") }
            } else {
                setUser(user)
            }
        }
    }

    fun clearAuthError() {
        _uiState.update { it.copy(authError = null) }
    }

    fun logout() {
        snapshotJob?.cancel()
        _uiState.value = FitnessUiState()
    }

    fun updateProfile(user: UserAccount, newPassword: String) {
        viewModelScope.launch {
            repository.updateUser(user, newPassword)?.let { setUser(it) }
        }
    }

    fun saveWorkout(log: WorkoutLog) {
        viewModelScope.launch {
            repository.saveWorkout(log)
        }
    }

    fun deleteWorkout(log: WorkoutLog) {
        viewModelScope.launch {
            repository.deleteWorkout(log.id, log.userId)
        }
    }

    fun saveBodyMetric(metric: BodyMetric) {
        viewModelScope.launch {
            repository.saveBodyMetric(metric)
        }
    }

    fun deleteBodyMetric(metric: BodyMetric) {
        viewModelScope.launch {
            repository.deleteBodyMetric(metric.id, metric.userId)
        }
    }

    private fun setUser(user: UserAccount) {
        _uiState.update { it.copy(user = user, authError = null) }
        snapshotJob?.cancel()
        snapshotJob = viewModelScope.launch {
            repository.observeSnapshot(user.id).collect { snapshot ->
                _uiState.update { it.copy(snapshot = snapshot) }
            }
        }
    }
}
