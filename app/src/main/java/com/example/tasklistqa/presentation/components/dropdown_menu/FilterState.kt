package com.example.tasklistqa.presentation.components.dropdown_menu

import com.example.tasklistqa.data.models.SortDirection
import com.example.tasklistqa.data.models.TaskStatus

data class FilterState(
    val sortDirection: SortDirection? = null,
    val sortField: String? = null,
    val statusFilter: TaskStatus? = null
)
