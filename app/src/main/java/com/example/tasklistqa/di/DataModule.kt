package com.example.tasklistqa.di

import com.example.tasklistqa.data.repository.TaskRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    factoryOf(::TaskRepository)
}