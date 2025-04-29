package com.example.tasklistqa.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tasklistqa.presentation.route.CreateTaskScreen
import com.example.tasklistqa.presentation.route.FullTaskScreen
import com.example.tasklistqa.presentation.route.TasksScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationScreen(modifier: Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = TasksScreen,
        modifier = modifier
    ) {
        composable<TasksScreen> {
            TaskScreen(
                viewModel = koinViewModel(),
                toCreationScreen = { navController.navigate(CreateTaskScreen) },
                toFullTaskScreen = { id -> navController.navigate(FullTaskScreen.createRoute(id)) })
        }
        composable<CreateTaskScreen> {
            CreateTaskScreen(
                onBackClick = { navController.navigateUp() },
                viewModel = koinViewModel()
            )
        }
        composable(
            route = "full_task/{Id}",
            arguments = listOf(
                navArgument("Id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val taskId = requireNotNull(backStackEntry.arguments?.getString("Id"))

            FullTaskScreen(
                onBackAction = { navController.navigateUp() },
                viewModel = koinViewModel(parameters = { parametersOf(taskId) })
            )
        }
    }
}