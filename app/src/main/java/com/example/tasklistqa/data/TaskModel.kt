package com.example.tasklistqa.data

data class TaskModel(
    val name:String,
    val description:String,
    val deadline:String,
    val priority:TaskPriority
)
