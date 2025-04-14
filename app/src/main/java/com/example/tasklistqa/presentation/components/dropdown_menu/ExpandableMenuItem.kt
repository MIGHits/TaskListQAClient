package com.example.tasklistqa.presentation.components.dropdown_menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ExpandableMenuItem(
    label: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        trailingIcon = {
            Icon(
                Icons.Default.ArrowDropDown,
                null,
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        onClick = onClick
    )

    if (isExpanded) {
        HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
        content()
        HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
    }
}