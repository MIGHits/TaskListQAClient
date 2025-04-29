package com.example.tasklistqa.presentation

import com.example.tasklistqa.data.models.TaskModel

sealed class CreateTaskState {
    data object Loading : CreateTaskState()
    data class Content(val task: TaskModel) : CreateTaskState()
    data class Error(val message: String) : CreateTaskState()
}