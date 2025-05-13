package com.example.tasklistqa

import com.example.tasklistqa.data.models.ShortTaskModel
import com.example.tasklistqa.data.models.TaskStatus

data class MarkAsCompletedTestCase(
    val initialTask: ShortTaskModel,
    val expectedStatusAfterClick: TaskStatus,
    val testName: String
)
