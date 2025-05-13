package com.example.tasklistqa.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasklistqa.AppContext.Companion.instance
import com.example.tasklistqa.R
import com.example.tasklistqa.common.Utils.parseErrorMessage
import com.example.tasklistqa.data.repository.TaskRepository
import com.example.tasklistqa.presentation.TaskScreenState
import com.example.tasklistqa.presentation.components.dropdown_menu.FilterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException

open class TaskListViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _screenState = MutableStateFlow<TaskScreenState>(TaskScreenState.Empty)
    open val screenState: StateFlow<TaskScreenState> get() = _screenState

    private val previousState = MutableStateFlow<TaskScreenState>(TaskScreenState.Empty)

    private val _filterState = MutableStateFlow(FilterState())
    open val filterState: StateFlow<FilterState> = _filterState



    open fun updateFilter(newFilter: FilterState) {
        _filterState.update { newFilter }
        getTasks()
    }

    init {
        getTasks()
    }

    open fun getTasks() {
        saveCurrentState()
        _screenState.update { TaskScreenState.Loading }
        viewModelScope.launch {
            try {
                val response = repository.getAllTasks(
                    sortDirection = _filterState.value.sortDirection?.name,
                    sortField = _filterState.value.sortField?.uppercase(),
                    status = _filterState.value.statusFilter?.name
                )
                if (response.isSuccessful) {
                    _screenState.update {
                        if (response.body()?.isEmpty() == true) {
                            TaskScreenState.Empty
                        } else {
                            TaskScreenState.Success(response.body() ?: emptyList())
                        }
                    }
                } else {
                    val errorMessage = parseErrorMessage(response)
                    _screenState.update { TaskScreenState.Error(errorMessage) }
                }
            } catch (e: ConnectException) {
                _screenState.update { TaskScreenState.Error(instance.getString(R.string.connectionError)) }
            } catch (e: Exception) {
                _screenState.update { TaskScreenState.Error(instance.getString(R.string.unknown_error)) }
            }
        }
    }

    open fun deleteTask(id: String) {
        saveCurrentState()
        _screenState.update { TaskScreenState.Loading }
        viewModelScope.launch {
            try {
                val response = repository.deleteTask(id)
                if (response.isSuccessful) {
                    getTasks()
                } else {
                    val errorMessage = parseErrorMessage(response)
                    _screenState.update { TaskScreenState.Error(errorMessage) }
                }
            } catch (e: ConnectException) {
                _screenState.update { TaskScreenState.Error(instance.getString(R.string.connectionError)) }
            } catch (e: Exception) {
                _screenState.update { TaskScreenState.Error(instance.getString(R.string.unknown_error)) }
            }
        }
    }

    open fun markAsCompleted(id: String) {
        saveCurrentState()
        viewModelScope.launch {
            try {
                val response = repository.changeTaskStatus(id)
                if (response.isSuccessful) {
                    getTasks()
                } else {
                    val errorMessage = parseErrorMessage(response)
                    _screenState.update { TaskScreenState.Error(errorMessage) }
                }
            } catch (e: ConnectException) {
                _screenState.update { TaskScreenState.Error(instance.getString(R.string.connectionError)) }
            } catch (e: Exception) {
                _screenState.update { TaskScreenState.Error(instance.getString(R.string.unknown_error)) }
            }
        }
    }

    private fun saveCurrentState() {
        previousState.value = _screenState.value
    }

    open fun restorePreviousState() {
        _screenState.value = previousState.value
    }
}