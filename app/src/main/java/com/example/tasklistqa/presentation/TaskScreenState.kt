package com.example.tasklistqa.presentation

import com.example.tasklistqa.data.models.ShortTaskModel

sealed class TaskScreenState {
    data object Loading : TaskScreenState()
    data object Empty : TaskScreenState()
    data class Error(val message: String) : TaskScreenState()
    data class Success(val content: List<ShortTaskModel>) : TaskScreenState()
}