package com.example.tasklistqa.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R
import com.example.tasklistqa.presentation.components.EmptyListStub
import com.example.tasklistqa.presentation.components.ErrorComponent
import com.example.tasklistqa.presentation.components.LoadingIndicator
import com.example.tasklistqa.presentation.components.dropdown_menu.DropdownMenu
import com.example.tasklistqa.presentation.components.task_card.TaskCard
import com.example.tasklistqa.presentation.viewModel.TaskListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    viewModel: TaskListViewModel,
    toCreationScreen: () -> Unit,
    toFullTaskScreen: (String) -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    val filterState by viewModel.filterState.collectAsState()


    SwipeRefresh(
        onRefresh = { viewModel.getTasks() },
        state = rememberSwipeRefreshState(screenState is TaskScreenState.Loading),
    ) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.your_tasks),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    DropdownMenu(
                        filterState = filterState,
                        onFilterChanged = { newFilter -> viewModel.updateFilter(newFilter) })
                }
            },
            bottomBar = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    FloatingActionButton(
                        shape = RoundedCornerShape(50),
                        onClick = { toCreationScreen() },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 20.dp)
                            .testTag("addButton"),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            modifier = Modifier.size(36.dp),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }
            }
        ) {
            when (val stateSnapshot = screenState) {
                is TaskScreenState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 56.dp, horizontal = 18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(stateSnapshot.content) { item ->
                            TaskCard(
                                item,
                                toFullTaskScreen,
                                onDelete = {
                                    viewModel.deleteTask(
                                        id = item.id,
                                    )
                                },
                                onCheckAction = {
                                    viewModel.markAsCompleted(
                                        id = item.id
                                    )
                                },
                                modifier = Modifier.testTag(item.name)
                            )
                        }
                    }
                }

                is TaskScreenState.Empty -> {
                    LazyColumn {
                        item {
                            EmptyListStub(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 100.dp)
                                    .testTag("emptyStub")
                            )
                        }
                    }
                }

                is TaskScreenState.Error -> {
                    ErrorComponent(
                        message = stateSnapshot.message,
                        onRetry = { viewModel.getTasks() },
                        onDismiss = { viewModel.restorePreviousState() })
                }

                TaskScreenState.Loading -> {
                    LoadingIndicator()
                }
            }
        }
    }
}


