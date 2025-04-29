package com.example.tasklistqa.di

import com.example.tasklistqa.presentation.viewModel.TaskListViewModel
import com.example.tasklistqa.presentation.viewModel.TaskDetailsViewModel
import com.example.tasklistqa.presentation.viewModel.TaskCreationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::TaskListViewModel)
    viewModelOf(::TaskDetailsViewModel)
    viewModelOf(::TaskCreationViewModel)
}