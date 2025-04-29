package com.example.tasklistqa.presentation.components.dropdown_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tasklistqa.AppContext.Companion.instance
import com.example.tasklistqa.R
import com.example.tasklistqa.common.Utils.localizedName
import com.example.tasklistqa.data.models.SortDirection
import com.example.tasklistqa.data.models.TaskStatus

@Composable
fun DropdownMenu(
    filterState: FilterState,
    onFilterChanged: (FilterState) -> Unit
) {
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
                contentDescription = null,
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
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(R.string.sort_asc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    onClick = {
                        onFilterChanged(filterState.copy(sortDirection = SortDirection.ASC))
                        expandedSection = null
                    },
                    trailingIcon = {
                        if (filterState.sortDirection == SortDirection.ASC) {
                            Icon(
                                Icons.Default.Check,
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(R.string.sort_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    onClick = {
                        onFilterChanged(filterState.copy(sortDirection = SortDirection.DESC))
                        expandedSection = null
                    },
                    trailingIcon = {
                        if (filterState.sortDirection == SortDirection.DESC) {
                            Icon(
                                Icons.Default.Check,
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            }

            ExpandableMenuItem(
                label = stringResource(R.string.sortingField),
                isExpanded = expandedSection == ExpandedSection.SORT_FIELD,
                onClick = {
                    expandedSection = if (expandedSection == ExpandedSection.SORT_FIELD) null
                    else ExpandedSection.SORT_FIELD
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(R.string.create_time),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    onClick = {
                        onFilterChanged(filterState.copy(sortField = instance.getString(R.string.creation_date)))
                        expandedSection = null
                    },
                    trailingIcon = {
                        if (filterState.sortField == instance.getString(R.string.creation_date)) {
                            Icon(
                                Icons.Default.Check,
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(R.string.priority),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    onClick = {
                        onFilterChanged(filterState.copy(sortField = instance.getString(R.string.priority_sort_field)))
                        expandedSection = null
                    },
                    trailingIcon = {
                        if (filterState.sortField == instance.getString(R.string.priority_sort_field)) {
                            Icon(
                                Icons.Default.Check,
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
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
                    DropdownMenuItem(
                        text = {
                            Text(
                                status.localizedName(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = {
                            onFilterChanged(
                                filterState.copy(
                                    statusFilter = if (filterState.statusFilter == status) null else status
                                )
                            )
                            expandedSection = null
                        },
                        trailingIcon = {
                            if (filterState.statusFilter == status) {
                                Icon(
                                    Icons.Default.Check,
                                    null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


