package com.example.tasklistqa.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R
import com.example.tasklistqa.data.TaskPriority
import com.example.tasklistqa.presentation.color
import com.example.tasklistqa.presentation.localizedName
import com.example.tasklistqa.ui.theme.PurpleGrey40

@Composable
fun PriorityButton(
    priority: TaskPriority?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onTertiary,
                shape = RoundedCornerShape(16)
            )
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = priority?.localizedName() ?: stringResource(R.string.priority_label),
                style = MaterialTheme.typography.bodyMedium,
                color = priority?.color() ?: PurpleGrey40,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}