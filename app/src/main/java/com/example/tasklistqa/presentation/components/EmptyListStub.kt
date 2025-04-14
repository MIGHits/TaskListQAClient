package com.example.tasklistqa.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tasklistqa.R

@Composable
fun EmptyListStub(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.empty_stub),
            contentDescription = null
        )
        Text(
            text = "Что вы хотите сделать сегодня?",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}