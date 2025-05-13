package com.example.tasklistqa.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.tasklistqa.data.models.TaskPriority

@Composable
fun PrioritySelector(
    priority: TaskPriority?,
    isMenuVisible: Boolean,
    onMenuVisibilityChange: (Boolean) -> Unit,
    onPrioritySelected: (TaskPriority) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().testTag("priorityColumn")) {
        PriorityButton(
            priority = priority,
            onClick = {
                onMenuVisibilityChange(!isMenuVisible)
            }
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