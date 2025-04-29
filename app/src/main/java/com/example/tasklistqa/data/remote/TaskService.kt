package com.example.tasklistqa.data.remote

import com.example.tasklistqa.common.Constant.TASK
import com.example.tasklistqa.common.Constant.TASK_TARGET
import com.example.tasklistqa.data.models.EditTaskModel
import com.example.tasklistqa.data.models.FullTaskModel
import com.example.tasklistqa.data.models.ResponseModel
import com.example.tasklistqa.data.models.ShortTaskModel
import com.example.tasklistqa.data.models.TaskModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TaskService {
    @GET(TASK)
    suspend fun getAllTasks(
        @Query("sortDirection") sortDirection: String? = null,
        @Query("sortField") sortField: String? = null,
        @Query("status") status: String? = null
    ): Response<List<ShortTaskModel>>

    @GET(TASK_TARGET)
    suspend fun getTaskById(@Path("id") taskId: String): Response<FullTaskModel>

    @POST(TASK)
    suspend fun createTask(@Body task: TaskModel): Response<String>

    @DELETE(TASK_TARGET)
    suspend fun deleteTask(@Path("id") taskId: String): Response<ResponseModel>

    @PUT(TASK_TARGET)
    suspend fun changeStatus(@Path("id") taskId: String): Response<Unit>

    @PUT(TASK)
    suspend fun editTask(@Body task: EditTaskModel): Response<ResponseModel>

}