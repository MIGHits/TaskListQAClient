package com.example.tasklistqa.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasklistqa.common.Utils.parseErrorMessage
import com.example.tasklistqa.data.models.TaskModel
import com.example.tasklistqa.data.models.TaskPriority
import com.example.tasklistqa.data.repository.TaskRepository
import com.example.tasklistqa.presentation.CreateTaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class TaskCreationViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _screenState = MutableStateFlow<CreateTaskState>(
        CreateTaskState.Content(
            TaskModel(
                name = "",
                description = "",
                deadline = "",
                priority = null
            )
        )
    )
    open val screenState: StateFlow<CreateTaskState> get() = _screenState

    private val _previousState = MutableStateFlow(
        TaskModel(
            name = "",
            description = "",
            deadline = "",
            priority = null
        )
    )
    open val previousState: StateFlow<TaskModel> get() = _previousState

    open fun createTask(task: TaskModel, onCreate: () -> Unit) {
        _screenState.update { CreateTaskState.Loading }
        viewModelScope.launch {
            val response = repository.createTask(task)
            if (response.isSuccessful) {
                _screenState.update { CreateTaskState.Content(task) }
                onCreate()
            } else {
                val errorMessage = parseErrorMessage(response)
                _screenState.update { CreateTaskState.Error(errorMessage) }
            }
        }
    }

    open fun changeTaskModel(task: TaskModel) {
        _screenState.update { CreateTaskState.Content(task) }
        saveCurrentState(task)
    }

    private fun saveCurrentState(task: TaskModel) {
        _previousState.update { task }
    }

    open fun restorePreviousState() {
        _screenState.update { CreateTaskState.Content(_previousState.value) }
    }
}
