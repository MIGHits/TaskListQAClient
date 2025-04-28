package com.example.tasklistqa.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R
import com.example.tasklistqa.common.Constant.CRITICAL
import com.example.tasklistqa.common.Constant.HIGH
import com.example.tasklistqa.common.Constant.LOW
import com.example.tasklistqa.common.Constant.MEDIUM
import com.example.tasklistqa.data.TaskPriority
import com.example.tasklistqa.presentation.components.PrioritySelector
import com.example.tasklistqa.presentation.components.ScreenHeader
import com.example.tasklistqa.presentation.components.TaskInputFields
import com.example.tasklistqa.ui.theme.CompletedColor
import com.example.tasklistqa.ui.theme.LateColor
import com.example.tasklistqa.ui.theme.OverdueColor
import com.example.tasklistqa.ui.theme.deadlineMarkerColor
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun CreateTaskScreen(
    onBackClick: () -> Unit = {},
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var isPriorityMenuVisible by remember { mutableStateOf(false) }
    var priority by remember { mutableStateOf<TaskPriority?>(null) }

    val dateFormatter = remember {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ScreenHeader(
            onBackClick = onBackClick,
            title = stringResource(R.string.CreateTask)
        )

        Spacer(Modifier.height(8.dp))

        TaskInputFields(
            name = name,
            onNameChange = { name = it },
            description = description,
            onDescriptionChange = { description = it },
            deadline = deadline,
            onDeadlineChange = { deadline = it },
            dateFormatter = dateFormatter
        )

        PrioritySelector(
            priority = priority,
            isMenuVisible = isPriorityMenuVisible,
            onMenuVisibilityChange = { isPriorityMenuVisible = it },
            onPrioritySelected = { priority = it }
        )
    }
}


fun TaskPriority.localizedName(): String = when (this) {
    TaskPriority.CRITICAL -> CRITICAL
    TaskPriority.HIGH -> HIGH
    TaskPriority.MEDIUM -> MEDIUM
    TaskPriority.LOW -> LOW
}

fun TaskPriority.color(): Color = when (this) {
    TaskPriority.CRITICAL -> OverdueColor
    TaskPriority.HIGH -> deadlineMarkerColor
    TaskPriority.MEDIUM -> CompletedColor
    TaskPriority.LOW -> LateColor
}


