package com.example.tasklistqa.data.models

data class FullTaskModel(
    val id:String,
    val name:String,
    val description:String,
    val createDate:String,
    val updateDate:String,
    val status: TaskStatus,
    val deadline:String,
    val priority: TaskPriority
)
