package com.example.tasklistqa.presentation

import com.example.tasklistqa.data.models.FullTaskModel

sealed class TaskContentState {
    data object Loading : TaskContentState()
    data object Empty : TaskContentState()
    data class Error(val message: String) : TaskContentState()
    data class Success(val content: FullTaskModel) : TaskContentState()
}
