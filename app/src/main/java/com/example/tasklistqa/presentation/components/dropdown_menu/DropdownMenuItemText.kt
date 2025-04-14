package com.example.tasklistqa.presentation.components.dropdown_menu

import androidx.annotation.StringRes
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun DropdownMenuItemText(
    @StringRes textRes: Int,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(textRes),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        onClick = onClick
    )
}

@Composable
fun DropdownMenuItemText(
    text: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        onClick = onClick
    )
}