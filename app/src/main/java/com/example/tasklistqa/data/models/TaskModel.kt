package com.example.tasklistqa.data.models

data class TaskModel(
    val name:String,
    val description:String,
    val deadline:String,
    val priority: TaskPriority?
)
