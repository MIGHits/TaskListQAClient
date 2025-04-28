package com.example.tasklistqa.presentation

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R
import com.example.tasklistqa.data.models.ShortTaskModel
import com.example.tasklistqa.data.models.TaskPriority
import com.example.tasklistqa.data.models.TaskStatus
import com.example.tasklistqa.presentation.components.EmptyListStub
import com.example.tasklistqa.presentation.components.dropdown_menu.DropdownMenu
import com.example.tasklistqa.presentation.components.task_card.TaskCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    toCreationScreen: () -> Unit,
    toFullTaskScreen: () -> Unit
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
                DropdownMenu()
            }
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                FloatingActionButton(
                    shape = RoundedCornerShape(50),
                    onClick = { toCreationScreen() },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 20.dp),
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
        val itemList = listOf<ShortTaskModel>(
            ShortTaskModel(
                id = "213ad12",
                name = "Качалка",
                status = TaskStatus.ACTIVE,
                deadline = "2025-04-13",
                priority = TaskPriority.HIGH
            )
        )
        if (!itemList.isEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 56.dp, horizontal = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(itemList) { item ->
                    TaskCard(item, toFullTaskScreen)
                }
            }
        } else {
            EmptyListStub(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)
            )
        }
    }
}


