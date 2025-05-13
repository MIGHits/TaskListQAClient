package com.example.tasklistqa.data.models

data class EditTaskModel(
    val id:String,
    val name:String,
    val description:String?,
    val deadline:String?,
    val priority: TaskPriority
)
