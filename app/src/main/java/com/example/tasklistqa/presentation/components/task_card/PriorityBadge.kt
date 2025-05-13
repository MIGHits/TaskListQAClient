package com.example.tasklistqa.presentation.components.task_card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R
import com.example.tasklistqa.common.Utils.localizedName
import com.example.tasklistqa.data.models.TaskPriority

@Composable
fun PriorityBadge(priority: TaskPriority, modifier: Modifier = Modifier, id: String) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.flag),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = priority.localizedName(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.testTag("taskPriority_${id}")
            )
        }
    }
}