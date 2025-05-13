package com.example.tasklistqa.presentation.components.task_card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.data.models.ShortTaskModel
import com.example.tasklistqa.data.models.TaskStatus
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TaskCard(
    task: ShortTaskModel,
    toFullTask: (String) -> Unit,
    onDelete: (String) -> Unit,
    onCheckAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val delete = SwipeAction(
        onSwipe = {
            onDelete(task.id)
        },
        icon = {

        },
        background = Color.Transparent
    )
    SwipeableActionsBox(
        startActions = listOf(delete),
        endActions = listOf(delete),
        backgroundUntilSwipeThreshold = Color.Transparent,
        swipeThreshold = 100.dp,
        modifier = modifier
            .clickable { toFullTask(task.id) }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .testTag("task_${task.id}")
        ) {
            Column(Modifier.padding(16.dp)) {
                TaskHeader(
                    title = task.name,
                    status = task.status,
                    isChecked = listOf(TaskStatus.LATE, TaskStatus.COMPLETED).contains(task.status),
                    onCheckAction = { onCheckAction(task.id) },
                    id = task.id
                )
                Spacer(Modifier.height(12.dp))
                TaskFooter(deadline = task.deadline, priority = task.priority, id = task.id)
            }
        }
    }
}



