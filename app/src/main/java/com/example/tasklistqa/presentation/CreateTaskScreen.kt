package com.example.tasklistqa.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R
import com.example.tasklistqa.common.Utils.toApiFormat
import com.example.tasklistqa.presentation.components.ErrorComponent
import com.example.tasklistqa.presentation.components.LoadingIndicator
import com.example.tasklistqa.presentation.components.PrioritySelector
import com.example.tasklistqa.presentation.components.ScreenHeader
import com.example.tasklistqa.presentation.components.TaskInputFields
import com.example.tasklistqa.presentation.viewModel.TaskCreationViewModel
import com.example.tasklistqa.ui.theme.PurpleGrey40
import com.google.accompanist.swiperefresh.SwipeRefresh
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun CreateTaskScreen(
    onBackClick: () -> Unit,
    viewModel: TaskCreationViewModel
) {
    val screenState by viewModel.screenState.collectAsState()
    var isPriorityMenuVisible by remember { mutableStateOf(false) }


    val dateFormatter = remember {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    when (val state = screenState) {
        is CreateTaskState.Loading -> LoadingIndicator()
        is CreateTaskState.Error -> ErrorComponent(
            state.message,
            onRetry = { viewModel.createTask(viewModel.previousState.value, onBackClick) },
            onDismiss = { viewModel.restorePreviousState() })

        is CreateTaskState.Content -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ScreenHeader(
                        onBackClick = onBackClick,
                        title = stringResource(R.string.CreateTask)
                    )

                    Spacer(Modifier.height(8.dp))

                    TaskInputFields(
                        name = state.task.name,
                        onNameChange = { newName ->
                            viewModel.changeTaskModel(state.task.copy(name = newName))
                        },
                        description = state.task.description,
                        onDescriptionChange = { newDesc ->
                            viewModel.changeTaskModel(state.task.copy(description = newDesc))
                        },
                        deadline = state.task.deadline,
                        onDeadlineChange = { newDeadline ->
                            viewModel.changeTaskModel(state.task.copy(deadline = newDeadline))
                        },
                        dateFormatter = dateFormatter
                    )

                    PrioritySelector(
                        priority = state.task.priority,
                        isMenuVisible = isPriorityMenuVisible,
                        onMenuVisibilityChange = { isPriorityMenuVisible = it },
                        onPrioritySelected = { viewModel.changeTaskModel(state.task.copy(priority = it)) },
                        modifier = Modifier.testTag("prioritySelector")
                    )
                }

                val isValid by remember(state) {
                    derivedStateOf { state.task.name.isNotEmpty() }
                }

                TextButton(
                    onClick = {
                        viewModel.createTask(
                            state.task.copy(deadline = state.task.deadline.toApiFormat()),
                            onBackClick
                        )
                    },
                    enabled = isValid,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(0.9f)
                        .background(
                            color = if (isValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(16)
                        )
                        .align(Alignment.BottomCenter)
                        .testTag("CreateButton")
                ) {
                    Text(
                        text = stringResource(R.string.create_task),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isValid) MaterialTheme.colorScheme.secondary else PurpleGrey40
                    )
                }
            }
        }
    }
}




