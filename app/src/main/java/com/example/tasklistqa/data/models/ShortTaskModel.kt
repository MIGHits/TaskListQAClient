package com.example.tasklistqa.data.models

data class ShortTaskModel(
    val id:String,
    val name:String,
    val status: TaskStatus,
    val deadline:String,
    val priority: TaskPriority
)
