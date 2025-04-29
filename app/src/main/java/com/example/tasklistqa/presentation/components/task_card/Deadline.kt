package com.example.tasklistqa.presentation.components.task_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R
import com.example.tasklistqa.ui.theme.OverdueColor
import com.example.tasklistqa.ui.theme.deadlineMarkerColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun Deadline(deadline: String) {
    val daysRemaining = ChronoUnit.DAYS.between(
        LocalDate.now(),
        LocalDate.parse(
            deadline,
        )
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(
            painter = painterResource(R.drawable.task_icon),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = when (daysRemaining) {
                in 1L..2L -> deadlineMarkerColor
                0L -> OverdueColor
                else -> {
                    MaterialTheme.colorScheme.onBackground
                }
            }
        )
        Text(
            text = stringResource(
                R.string.deadline, LocalDate.parse(
                    deadline,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

