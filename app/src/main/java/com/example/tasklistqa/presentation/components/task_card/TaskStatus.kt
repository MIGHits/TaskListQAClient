package com.example.tasklistqa.presentation.components.task_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.data.models.TaskStatus
import com.example.tasklistqa.ui.theme.ActiveColor
import com.example.tasklistqa.ui.theme.CompletedColor
import com.example.tasklistqa.ui.theme.LateColor
import com.example.tasklistqa.ui.theme.OverdueColor

@Composable
fun TaskStatus(status: TaskStatus,id:String) {
    Text(
        text = status.name,
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(8.dp),
                color = when (status) {
                    TaskStatus.ACTIVE -> ActiveColor
                    TaskStatus.OVERDUE -> OverdueColor
                    TaskStatus.LATE -> LateColor
                    TaskStatus.COMPLETED -> CompletedColor
                },
            )
            .padding(4.dp)
            .testTag("taskStatus_${id}")
    )
}