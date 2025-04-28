package com.example.tasklistqa.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.data.models.TaskPriority
import com.example.tasklistqa.presentation.color
import com.example.tasklistqa.presentation.localizedName

@Composable
fun PriorityMenuItem(
    priority: TaskPriority,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = priority.localizedName().uppercase(),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .fillMaxWidth()
            .background(color = priority.color(), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable(onClick = onClick)
    )
}