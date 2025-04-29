package com.example.tasklistqa.presentation.route

import kotlinx.serialization.Serializable

@Serializable
object FullTaskScreen {
    private const val BASE_ROUTE = "full_task"

    fun createRoute(taskId: String) = "$BASE_ROUTE/$taskId"
}