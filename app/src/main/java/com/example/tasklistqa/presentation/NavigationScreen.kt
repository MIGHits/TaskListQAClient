package com.example.tasklistqa.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasklistqa.presentation.route.CreateTaskScreen
import com.example.tasklistqa.presentation.route.TasksScreen

@Composable
fun NavigationScreen(modifier: Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = CreateTaskScreen,
        modifier = modifier
    ) {
        composable<TasksScreen> {
            TaskScreen()
        }
        composable<CreateTaskScreen> {
            CreateTaskScreen()
        }
    }
}