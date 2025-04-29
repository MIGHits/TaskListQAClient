package com.example.tasklistqa.common

import androidx.compose.ui.graphics.Color
import com.example.tasklistqa.AppContext.Companion.instance
import com.example.tasklistqa.R
import com.example.tasklistqa.common.Constant.ACTIVE
import com.example.tasklistqa.common.Constant.COMPLETED
import com.example.tasklistqa.common.Constant.CRITICAL
import com.example.tasklistqa.common.Constant.HIGH
import com.example.tasklistqa.common.Constant.LATE
import com.example.tasklistqa.common.Constant.LOW
import com.example.tasklistqa.common.Constant.MEDIUM
import com.example.tasklistqa.common.Constant.OVERDUE
import com.example.tasklistqa.data.models.ResponseModel
import com.example.tasklistqa.data.models.TaskPriority
import com.example.tasklistqa.data.models.TaskStatus
import com.example.tasklistqa.ui.theme.ActiveColor
import com.example.tasklistqa.ui.theme.CompletedColor
import com.example.tasklistqa.ui.theme.LateColor
import com.example.tasklistqa.ui.theme.OverdueColor
import com.example.tasklistqa.ui.theme.deadlineMarkerColor
import com.google.gson.Gson
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Utils {
    fun parseErrorMessage(response: Response<*>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                val apiError = Gson().fromJson(errorBody, ResponseModel::class.java)
                apiError.message
            } else {
                instance.getString(R.string.unknown_error)
            }
        } catch (e: Exception) {
            instance.getString(R.string.error_handling_exception, e.localizedMessage)
        }
    }

    fun String.toDisplayFormat(): String {
        return try {
            LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        } catch (e: Exception) {
            this
        }
    }

    fun String.toApiFormat(): String {
        return try {
            LocalDate.parse(this, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } catch (e: Exception) {
            this
        }
    }

    fun TaskStatus.localizedName(): String = when (this) {
        TaskStatus.ACTIVE -> ACTIVE
        TaskStatus.LATE -> LATE
        TaskStatus.COMPLETED -> COMPLETED
        TaskStatus.OVERDUE -> OVERDUE
    }

    fun TaskStatus.color(): Color = when (this) {
        TaskStatus.OVERDUE -> OverdueColor
        TaskStatus.COMPLETED -> CompletedColor
        TaskStatus.ACTIVE -> ActiveColor
        TaskStatus.LATE -> LateColor
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
}