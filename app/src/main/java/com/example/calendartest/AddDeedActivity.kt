@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.calendartest

import android.app.Application
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calendartest.database.DeedItem
import com.example.calendartest.database.DeedViewModel
import com.example.calendartest.database.DeedViewModelFactory
import com.example.calendartest.ui.theme.CalendarTestTheme
import java.sql.Timestamp
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class AddDeedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SecondScreen()
                }
            }
        }
    }
}

private data class StringTime(
    @StringRes val text: Int
)

private fun getTimeData() {
    (0 until 24).map {

    }
}


@Composable
fun DateSelect() {
    val expanded = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded.value = true }) {
            Icon(Icons.Outlined.Edit, contentDescription = "Localized description")
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = { /* Handle edit! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = null
                    )
                })
            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = { /* Handle settings! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = null
                    )
                })
            Divider()
            DropdownMenuItem(
                text = { Text("Send Feedback") },
                onClick = { /* Handle send feedback! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = null
                    )
                },
                trailingIcon = { Text("F11", textAlign = TextAlign.Center) })
        }

    }
}

@Composable
fun DeedField(
    //@StringRes text: Int,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val mDeedViewModel: DeedViewModel = viewModel(
        factory = DeedViewModelFactory(context.applicationContext as Application)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var year1 by remember { mutableStateOf("2023") }
        var month1 by remember { mutableStateOf("4") }
        var day1 by remember { mutableStateOf("24") }
        var hour1 by remember { mutableStateOf("5") }
        var minute1 by remember { mutableStateOf("30") }

        var year2 by remember { mutableStateOf("2023") }
        var month2 by remember { mutableStateOf("4") }
        var day2 by remember { mutableStateOf("24") }
        var hour2 by remember { mutableStateOf("6") }
        var minute2 by remember { mutableStateOf("40") }
        var name by remember { mutableStateOf("DD") }
        var description by remember { mutableStateOf("SS") }

        TextField(
            value = year1,
            label = { Text(text = "год") },
            onValueChange = { year1 = it },
            modifier = modifier
        )

        TextField(
            value = month1,
            label = { Text(text = "месяц") },
            onValueChange = { month1 = it },
            modifier = modifier
        )

        TextField(
            value = day1,
            label = { Text(text = "День") },
            onValueChange = { day1 = it },
            modifier = modifier
        )
        TextField(
            value = hour1,
            label = { Text(text = "Час") },
            onValueChange = { hour1 = it },
            modifier = modifier
        )

        TextField(
            value = minute1,
            label = { Text(text = "минута") },
            onValueChange = { minute1 = it },
            modifier = modifier
        )

        Divider(thickness = 20.dp, color = Color.White)

        TextField(
            value = year2,
            label = { Text(text = "год") },
            onValueChange = { year2 = it },
            modifier = modifier
        )


        TextField(
            value = month2,
            label = { Text(text = "Час") },
            onValueChange = { month2 = it },
            modifier = modifier
        )

        TextField(
            value = day2,
            label = { Text(text = "День") },
            onValueChange = { day2 = it },
            modifier = modifier
        )

        TextField(
            value = hour2,
            label = { Text(text = "минута") },
            onValueChange = { hour2 = it },
            modifier = modifier
        )
        TextField(
            value = minute2,
            label = { Text(text = "минута") },
            onValueChange = { minute2 = it },
            modifier = modifier
        )

        Divider(thickness = 20.dp, color = Color.White)
        TextField(
            value = name,
            label = { Text(text = "Название") },
            onValueChange = { name = it },
            modifier = modifier
        )

        TextField(
            value = description,
            label = { Text(text = "Описание") },
            onValueChange = { description = it },
            modifier = modifier
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(120.dp),
            horizontalArrangement = Arrangement.Center,

            ) {
            Button(
                onClick = { context.startActivity(Intent(context, MainActivity::class.java)) }) {
                Text(text = "Назад")
            }
            Spacer(modifier = modifier.width(30.dp))
            Button(onClick = {
                val y1 = year1.toInt()
                val m1 = month1.toInt()
                val d1 = day1.toInt()
                val h1 = hour1.toInt()
                val min1 = minute1.toInt()

                val y2 = year2.toInt()
                val m2 = month2.toInt()
                val d2 = day2.toInt()
                val h2 = hour2.toInt()
                val min2 = minute2.toInt()

                val datetime1 = LocalDateTime.of(y1, m1, d1, h1, m1)
                val datetime2 = LocalDateTime.of(y2, m2, d2, h2, m2)

                val item = DeedItem(
                    0,
                    datetime1.atZone(ZoneId.systemDefault()).toEpochSecond(),
                    datetime2.atZone(ZoneId.systemDefault()).toEpochSecond(),
                    name = name,
                    description=description
                )

                Toast.makeText(context, Timestamp(datetime1.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000L).toString(),Toast.LENGTH_LONG).show()
                mDeedViewModel.addDeed(item)

            }) {
                Text(text = "Сохранить дело")
            }

        }
    }
}

@Composable
fun AddCancelBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(120.dp),
        horizontalArrangement = Arrangement.Center,

        ) {
        Button(
            onClick = { context.startActivity(Intent(context, MainActivity::class.java)) }) {
            Text(text = "Назад")
        }
        Spacer(modifier = modifier.width(30.dp))
        Button(onClick = {
        }) {
            Text(text = "Сохранить дело")
        }
    }
}

@Composable
fun DeedScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            DeedField()
        }
    }
}

@Composable
fun SecondScreen(modifier: Modifier = Modifier) {
    CalendarTestTheme {
        Scaffold()
        { padding ->
            DeedScreen(Modifier.padding(padding))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeedFieldPreview() {
    CalendarTestTheme {
        DeedField()
    }
}

@Preview(showBackground = true)
@Composable
fun AddCancelBarPreview() {
    CalendarTestTheme {
        AddCancelBar()
    }
}

@Preview(showBackground = true)
@Composable
fun DeedScreenPreview() {
    CalendarTestTheme {
        DeedScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    CalendarTestTheme {
        SecondScreen()
    }
}