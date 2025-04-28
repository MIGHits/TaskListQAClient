package com.example.tasklistqa.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tasklistqa.data.models.TaskPriority

@Composable
fun PrioritySelector(
    priority: TaskPriority?,
    isMenuVisible: Boolean,
    onMenuVisibilityChange: (Boolean) -> Unit,
    onPrioritySelected: (TaskPriority) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        PriorityButton(
            priority = priority,
            onClick = { onMenuVisibilityChange(!isMenuVisible) }
        )

        PriorityDropdownMenu(
            isVisible = isMenuVisible,
            onPrioritySelected = {
                onPrioritySelected(it)
                onMenuVisibilityChange(false)
            }
        )
    }
}