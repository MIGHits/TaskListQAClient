package com.example.tasklistqa.presentation.components.task_card

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
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.data.ShortTaskModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TaskCard(task: ShortTaskModel) {
    val delete = SwipeAction(
        onSwipe = {
            TODO()
        },
        icon = {
            TODO()
        },
        background = Color.Transparent
    )

    val edit = SwipeAction(
        onSwipe = {
            TODO()
        },
        icon = {
            TODO()
        },
        background = Color.Transparent
    )
    SwipeableActionsBox(
        startActions = listOf(delete),
        endActions = listOf(edit),
        backgroundUntilSwipeThreshold = Color.Transparent
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(Modifier.padding(16.dp)) {
                TaskHeader(title = task.name, status = task.status)
                Spacer(Modifier.height(12.dp))
                TaskFooter(deadline = task.deadline, priority = task.priority)
            }
        }
    }
}



