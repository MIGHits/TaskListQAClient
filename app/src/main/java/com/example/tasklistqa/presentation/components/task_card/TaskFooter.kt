package com.example.tasklistqa.presentation.components.task_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.data.models.TaskPriority

@Composable
fun TaskFooter(deadline: String?, priority: TaskPriority) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (deadline) {
            null -> {
                Spacer(modifier = Modifier.width(0.dp))
            }

            else -> {
                Deadline(deadline)
            }
        }
        PriorityBadge(priority)
    }
}