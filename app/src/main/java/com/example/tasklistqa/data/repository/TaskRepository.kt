package com.example.tasklistqa.data.repository

import com.example.tasklistqa.data.models.EditTaskModel
import com.example.tasklistqa.data.models.FullTaskModel
import com.example.tasklistqa.data.models.ResponseModel
import com.example.tasklistqa.data.models.ShortTaskModel
import com.example.tasklistqa.data.models.SortDirection
import com.example.tasklistqa.data.models.TaskModel
import com.example.tasklistqa.data.remote.TaskService
import retrofit2.Response

open class TaskRepository(private val taskService: TaskService) {
    suspend fun getAllTasks(
        sortDirection: String?,
        sortField: String?,
        status: String?
    ): Response<List<ShortTaskModel>> {
        return taskService.getAllTasks(
            sortField = sortField,
            sortDirection = sortDirection,
            status = status
        )
    }

    suspend fun getTaskById(id: String): Response<FullTaskModel> {
        return taskService.getTaskById(id)
    }

    open suspend fun createTask(task: TaskModel): Response<String> {
        return taskService.createTask(task)
    }

    suspend fun deleteTask(id: String): Response<ResponseModel> {
        return taskService.deleteTask(id)
    }

    suspend fun changeTaskStatus(id: String): Response<Unit> {
        return taskService.changeStatus(id)
    }

    suspend fun editTask(task: EditTaskModel): Response<ResponseModel> {
        return taskService.editTask(task)
    }
}