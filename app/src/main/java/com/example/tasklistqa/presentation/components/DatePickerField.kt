package com.example.tasklistqa.presentation.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R
import com.example.tasklistqa.ui.theme.PurpleGrey40
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun DatePickerField(
    deadline: String,
    onDateTimeSelected: (String) -> Unit,
    minDateTime: Date? = null,
    dateFormatter: SimpleDateFormat,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val currentCalendar = Calendar.getInstance()
    val minCalendar = minDateTime?.let {
        Calendar.getInstance().apply { time = it }
    } ?: currentCalendar

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                val contextThemeWrapper = androidx.appcompat.view.ContextThemeWrapper(
                    context,
                    R.style.CustomTimePickerDialogTheme
                )
                val dialog = DatePickerDialog(
                    contextThemeWrapper,
                    { _, year, month, day ->
                        onDateTimeSelected(
                            dateFormatter.format(
                                Calendar.getInstance().apply { set(year, month, day) }.time
                            )
                        )
                    },
                    currentCalendar.get(Calendar.YEAR),
                    currentCalendar.get(Calendar.MONTH),
                    currentCalendar.get(Calendar.DAY_OF_MONTH)
                ).apply {
                    datePicker.minDate = minCalendar.timeInMillis
                }
                dialog.show()
            }
    ) {
        TextField(
            value = deadline,
            onValueChange = { value -> onDateTimeSelected(value) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().testTag("deadlineText"),
            enabled = false,
            label = {
                Text(
                    text = stringResource(R.string.deadline_field),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            },
            colors = TextFieldDefaults.colors(
                disabledLabelColor = if (deadline.isEmpty()) {
                    PurpleGrey40
                } else {
                    MaterialTheme.colorScheme.primary
                },
                disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
                disabledTextColor = MaterialTheme.colorScheme.secondary
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(16)
        )
    }
}