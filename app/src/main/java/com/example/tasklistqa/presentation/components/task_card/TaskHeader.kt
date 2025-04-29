package com.example.tasklistqa.presentation.components.task_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tasklistqa.data.models.TaskStatus

@Composable
fun TaskHeader(
    title: String,
    status: TaskStatus,
    isChecked: Boolean,
    id: String,
    onCheckAction: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TaskTitle(title, isChecked, id = id, onCheckAction = onCheckAction)
        TaskStatus(status)
    }
}