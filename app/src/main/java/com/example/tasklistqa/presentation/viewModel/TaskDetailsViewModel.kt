package com.example.tasklistqa.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasklistqa.common.Utils.parseErrorMessage
import com.example.tasklistqa.data.models.EditTaskModel
import com.example.tasklistqa.data.models.FullTaskModel
import com.example.tasklistqa.data.repository.TaskRepository
import com.example.tasklistqa.presentation.TaskContentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskDetailsViewModel(private val repository: TaskRepository, private val id: String) :
    ViewModel() {
    private val _screenState = MutableStateFlow<TaskContentState>(TaskContentState.Empty)
    val screenState: StateFlow<TaskContentState> get() = _screenState
    private val previousState = MutableStateFlow<TaskContentState>(TaskContentState.Empty)

    init {
        getTaskDetails()
    }

    fun getTaskDetails() {
        saveCurrentState()
        _screenState.update { TaskContentState.Loading }
        viewModelScope.launch {
            val response = repository.getTaskById(id)
            if (response.isSuccessful) {
                _screenState.update { TaskContentState.Success(response.body()) }
            } else {
                val errorMessage = parseErrorMessage(response)
                _screenState.update { TaskContentState.Error(errorMessage) }
            }
        }
    }

    fun editTask(newTaskState: FullTaskModel) {
        _screenState.update { TaskContentState.Success(newTaskState) }
    }

    fun editTaskById(editedTask: EditTaskModel) {
        saveCurrentState()
        _screenState.update { TaskContentState.Loading }
        viewModelScope.launch {
            val response = repository.editTask(editedTask)
            if (response.isSuccessful) {
                getTaskDetails()
            } else {
                val errorMessage = parseErrorMessage(response)
                _screenState.update { TaskContentState.Error(errorMessage) }
            }
        }
    }

    private fun saveCurrentState() {
        previousState.value = _screenState.value
    }

    fun restorePreviousState() {
        _screenState.value = previousState.value
    }
}