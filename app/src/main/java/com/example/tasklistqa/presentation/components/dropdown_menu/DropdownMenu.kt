package com.example.tasklistqa.presentation.components.dropdown_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tasklistqa.R
import com.example.tasklistqa.data.TaskStatus

@Composable
fun DropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var expandedSection by remember { mutableStateOf<ExpandedSection?>(null) }

    val statusList = listOf(
        TaskStatus.ACTIVE,
        TaskStatus.OVERDUE,
        TaskStatus.COMPLETED,
        TaskStatus.LATE,
    )

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        DropdownMenu(
            expanded = expanded,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.onTertiary),
            onDismissRequest = {
                expanded = false
                expandedSection = null
            }
        ) {

            ExpandableMenuItem(
                label = stringResource(R.string.sorting),
                isExpanded = expandedSection == ExpandedSection.SORT_DIRECTION,
                onClick = {
                    expandedSection = if (expandedSection == ExpandedSection.SORT_DIRECTION) null
                    else ExpandedSection.SORT_DIRECTION
                }
            ) {
                DropdownMenuItemText(R.string.sort_asc) { expandedSection = null }
                DropdownMenuItemText(R.string.sort_desc) { expandedSection = null }
            }


            ExpandableMenuItem(
                label = stringResource(R.string.sortingField),
                isExpanded = expandedSection == ExpandedSection.SORT_FIELD,
                onClick = {
                    expandedSection = if (expandedSection == ExpandedSection.SORT_FIELD) null
                    else ExpandedSection.SORT_FIELD
                }
            ) {
                DropdownMenuItemText(R.string.create_time) { expandedSection = null }
                DropdownMenuItemText(R.string.priority) { expandedSection = null }
            }

            ExpandableMenuItem(
                label = stringResource(R.string.statusFilter),
                isExpanded = expandedSection == ExpandedSection.STATUS_FILTER,
                onClick = {
                    expandedSection = if (expandedSection == ExpandedSection.STATUS_FILTER) null
                    else ExpandedSection.STATUS_FILTER
                }
            ) {
                statusList.forEach { status ->
                    DropdownMenuItemText(status.name) { expandedSection = null }
                }
            }
        }
    }
}


