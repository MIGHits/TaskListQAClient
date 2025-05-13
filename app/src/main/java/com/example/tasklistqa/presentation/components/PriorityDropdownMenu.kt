package com.example.tasklistqa.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.data.models.TaskPriority

@Composable
fun PriorityDropdownMenu(
    isVisible: Boolean,
    onPrioritySelected: (TaskPriority) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .testTag("PriorityMenu")
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.testTag("priorityColumn")
        ) {
            TaskPriority.entries.forEach { priority ->
                PriorityMenuItem(
                    priority = priority,
                    onClick = { onPrioritySelected(priority) }
                )
            }
        }
    }
}