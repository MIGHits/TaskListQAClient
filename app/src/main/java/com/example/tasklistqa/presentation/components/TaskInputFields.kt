package com.example.tasklistqa.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.tasklistqa.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun TaskInputFields(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    deadline: String,
    onDeadlineChange: (String) -> Unit,
    dateFormatter: SimpleDateFormat
) {
    TaskField(
        value = name,
        onValueChange = onNameChange,
        name = stringResource(R.string.name),
        modifier = Modifier.testTag("nameInput")
    )

    TaskField(
        value = description,
        onValueChange = onDescriptionChange,
        name = stringResource(R.string.description),
        modifier = Modifier.testTag("descriptionInput")
    )

    DatePickerField(
        deadline = deadline,
        onDateTimeSelected = onDeadlineChange,
        minDateTime = Date.from(
            LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
        ),
        dateFormatter = dateFormatter,
        modifier = Modifier.testTag("deadlineInput")
    )
}