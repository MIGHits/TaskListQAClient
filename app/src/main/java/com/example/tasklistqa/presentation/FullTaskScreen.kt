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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.example.tasklistqa.common.Utils.color
import com.example.tasklistqa.common.Utils.localizedName
import com.example.tasklistqa.common.Utils.toApiFormat
import com.example.tasklistqa.common.Utils.toDisplayFormat
import com.example.tasklistqa.data.models.EditTaskModel
import com.example.tasklistqa.data.models.FullTaskModel
import com.example.tasklistqa.data.models.TaskPriority
import com.example.tasklistqa.data.models.TaskStatus
import com.example.tasklistqa.presentation.components.DatePickerField
import com.example.tasklistqa.presentation.components.ErrorComponent
import com.example.tasklistqa.presentation.components.LoadingIndicator
import com.example.tasklistqa.presentation.components.PrioritySelector
import com.example.tasklistqa.presentation.components.ScreenHeader
import com.example.tasklistqa.presentation.components.TaskField
import com.example.tasklistqa.presentation.viewModel.TaskDetailsViewModel
import com.example.tasklistqa.ui.theme.ActiveColor
import com.example.tasklistqa.ui.theme.CompletedColor
import com.example.tasklistqa.ui.theme.LateColor
import com.example.tasklistqa.ui.theme.OverdueColor
import com.example.tasklistqa.ui.theme.PurpleGrey40
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun FullTaskScreen(
    onBackAction: () -> Unit,
    viewModel: TaskDetailsViewModel
) {
    val screenState by viewModel.screenState.collectAsState()
    var isPriorityMenuVisible by remember { mutableStateOf(false) }

    val dateFormatter = remember {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    when (val state = screenState) {
        is TaskContentState.Loading -> return LoadingIndicator()
        TaskContentState.Empty -> return Unit
        is TaskContentState.Error -> return ErrorComponent(
            state.message,
            onRetry = { viewModel.getTaskDetails() },
            onDismiss = { viewModel.restorePreviousState() }
        )

        is TaskContentState.Success -> {
            val content = state.content ?: run {
                ErrorComponent("Content is null", onRetry = {}, onDismiss = onBackAction)
                return
            }

            Box(modifier = Modifier.fillMaxSize()) {
                ScreenHeader(
                    onBackClick = onBackAction,
                    title = stringResource(R.string.edit_full_task),
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(top = 48.dp, bottom = 48.dp)
                        .fillMaxSize()
                        .zIndex(1f)
                        .align(Alignment.Center),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        TaskField(
                            value = content.name,
                            onValueChange = { newName ->
                                viewModel.editTask(content.copy(name = newName))
                            },
                            name = stringResource(R.string.name)
                        )
                    }
                    item {
                        TaskField(
                            value = content.description,
                            onValueChange = { newDescription ->
                                viewModel.editTask(content.copy(description = newDescription))
                            },
                            name = stringResource(R.string.description)
                        )
                    }
                    item {
                        DatePickerField(
                            deadline = content.deadline.toDisplayFormat(),
                            onDateTimeSelected = { newDeadline ->
                                viewModel.editTask(content.copy(deadline = newDeadline))
                            },
                            minDateTime = Date.from(
                                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
                            ),
                            dateFormatter = dateFormatter
                        )
                    }
                    item {
                        ImmutableCardField(
                            fieldName = stringResource(R.string.creation_date_label),
                            value = content.createDate.toDisplayFormat()
                        )
                    }
                    item {
                        ImmutableCardField(
                            fieldName = stringResource(R.string.last_update),
                            value = content.updateDate.toDisplayFormat()
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
                            onMenuVisibilityChange = {
                                isPriorityMenuVisible = !isPriorityMenuVisible
                            },
                            onPrioritySelected = { newPriority ->
                                viewModel.editTask(content.copy(priority = newPriority))
                            }
                        )
                    }
                }

                val isValid by remember(content) {
                    derivedStateOf { content.name.isNotEmpty() }
                }

                TextButton(
                    onClick = {
                        viewModel.editTaskById(
                            EditTaskModel(
                                id = content.id,
                                name = content.name,
                                deadline = content.deadline.toApiFormat(),
                                description = content.description,
                                priority = content.priority
                            )
                        )
                    },
                    enabled = true,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(0.9f)
                        .background(
                            color = if (isValid) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(16)
                        )
                        .zIndex(0f)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = stringResource(R.string.save_task),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isValid) MaterialTheme.colorScheme.secondary
                        else PurpleGrey40
                    )
                }
            }
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
