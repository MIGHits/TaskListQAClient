package com.example.tasklistqa.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tasklistqa.R
import com.example.tasklistqa.common.Constant.ACTIVE
import com.example.tasklistqa.common.Constant.COMPLETED
import com.example.tasklistqa.common.Constant.LATE
import com.example.tasklistqa.common.Constant.OVERDUE
import com.example.tasklistqa.data.models.FullTaskModel
import com.example.tasklistqa.data.models.TaskPriority
import com.example.tasklistqa.data.models.TaskStatus
import com.example.tasklistqa.presentation.components.DatePickerField
import com.example.tasklistqa.presentation.components.PrioritySelector
import com.example.tasklistqa.presentation.components.ScreenHeader
import com.example.tasklistqa.presentation.components.TaskField
import com.example.tasklistqa.ui.theme.ActiveColor
import com.example.tasklistqa.ui.theme.CompletedColor
import com.example.tasklistqa.ui.theme.LateColor
import com.example.tasklistqa.ui.theme.OverdueColor
import com.example.tasklistqa.ui.theme.PurpleGrey40
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun FullTaskScreen(onBackAction: () -> Unit) {
    val content by remember {
        mutableStateOf(
            FullTaskModel(
                id = "",
                name = "Имя",
                description = "Описание",
                createDate = "28.04.2025",
                updateDate = "29.04.2025",
                status = TaskStatus.ACTIVE,
                deadline = "30.04.2025",
                priority = TaskPriority.MEDIUM
            )
        )
    }

    val dateFormatter = remember {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
    var isValid by remember { mutableStateOf(false) }
    var isPriorityMenuVisible by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {

        ScreenHeader(
            onBackClick = { onBackAction() },
            title = stringResource(R.string.edit_full_task),
            modifier = Modifier.align(Alignment.TopCenter)
        )

        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxSize()
                .zIndex(1f)
                .align(Alignment.Center),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                TaskField(
                    value = content.name,
                    onValueChange = { },
                    name = stringResource(R.string.name)
                )
            }
            item {
                TaskField(
                    value = content.description,
                    onValueChange = { },
                    name = stringResource(R.string.description)
                )
            }
            item {
                DatePickerField(
                    deadline = content.deadline,
                    onDateTimeSelected = {},
                    minDateTime = Date.from(
                        LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
                    ),
                    dateFormatter = dateFormatter
                )
            }
            item {
                ImmutableCardField(
                    fieldName = stringResource(R.string.creation_date_label),
                    value = content.createDate
                )
            }
            item {
                ImmutableCardField(
                    fieldName = stringResource(R.string.last_update),
                    value = content.updateDate
                )
            }
            item {
                ImmutableCardField(
                    fieldName = stringResource(R.string.task_status),
                    value = content.status.localizedName(),
                    textColor = content.status.color()
                )
            }
            item {
                PrioritySelector(
                    priority = content.priority,
                    isMenuVisible = isPriorityMenuVisible,
                    onMenuVisibilityChange = { isPriorityMenuVisible = it },
                    onPrioritySelected = {}
                )
            }
        }
        TextButton(
            onClick = {},
            enabled = isValid,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(0.9f)
                .background(
                    color = if (isValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
                    shape = RoundedCornerShape(16)
                )
                .zIndex(0f)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.save_task),
                style = MaterialTheme.typography.bodyLarge,
                color = if (isValid) MaterialTheme.colorScheme.secondary else PurpleGrey40
            )
        }
    }
}


@Composable
fun ImmutableCardField(
    fieldName: String,
    value: String,
    textColor: Color = MaterialTheme.colorScheme.secondary
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onTertiary,
                shape = RoundedCornerShape(16)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = fieldName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

fun TaskStatus.localizedName(): String = when (this) {
    TaskStatus.ACTIVE -> ACTIVE
    TaskStatus.LATE -> LATE
    TaskStatus.COMPLETED -> COMPLETED
    TaskStatus.OVERDUE -> OVERDUE
}

fun TaskStatus.color(): Color = when (this) {
    TaskStatus.OVERDUE -> OverdueColor
    TaskStatus.COMPLETED -> CompletedColor
    TaskStatus.ACTIVE -> ActiveColor
    TaskStatus.LATE -> LateColor
}