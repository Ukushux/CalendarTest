@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.calendartest

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.calendartest.database.DeedDatabase
import com.example.calendartest.database.DeedDatabaseDao
import com.example.calendartest.ui.theme.CalendarTestTheme
import com.mabn.calendarlibrary.ExpandableCalendar
import com.mabn.calendarlibrary.core.calendarDefaultTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalendarTest()
                }
            }
        }
    }
}

@Composable
fun Calendar() {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        ExpandableCalendar(
            theme = calendarDefaultTheme.copy(
                dayShape = CircleShape,
                backgroundColor = Color.Black,
                selectedDayBackgroundColor = Color.White,
                dayValueTextColor = Color.White,
                selectedDayValueTextColor = Color.Black,
                headerTextColor = Color.White,
                weekDaysTextColor = Color.White
            ), onDayClick = {
                currentDate.value = it
            })
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("Selected date: ${currentDate.value}")
        }
    }
}

@Composable
fun Deed(
    @StringRes text: Int,
    modifier: Modifier = Modifier
){
    Button(modifier = modifier, onClick = { deleteDb() }) {
        Text(
            text = stringResource(text)
        )
    }
}

@Composable
fun DeedsList(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ){
        items(exampleDeeds){item ->
            Deed(item.text)

        }
    }
}

@Composable
fun AddDeed(modifier: Modifier = Modifier){
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            try {
            createDb()
            }
            catch (e: Exception){
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show()
        } },
        modifier=modifier) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
            tint = Color.Black,
        )
    }
}


@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    Column(modifier) {
        Calendar()
        DeedsList()
    }
}

@Composable
fun CalendarTest(modifier: Modifier = Modifier) {
    CalendarTestTheme {
        Scaffold(
            floatingActionButton = { AddDeed(modifier = modifier.padding(50.dp)) },
            floatingActionButtonPosition = FabPosition.End
        ) {    padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

fun createDb() {
    val context =
        InstrumentationRegistry.getInstrumentation().targetContext

    db = Room.inMemoryDatabaseBuilder(context, DeedDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    deedDao = db.deedDao()
}

fun deleteDb() {
    db.close()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalendarTestTheme {
        Calendar()
    }
}

@Preview
@Composable
fun DeedPreview(){
    CalendarTestTheme {
        Deed(text = R.string.Deed1)
    }
}

@Preview(showBackground = true)
@Composable
fun DeedsListPreview() {
    CalendarTestTheme {
        DeedsList()
    }
}

@Preview
@Composable
fun AddDeedPreview(){
    CalendarTestTheme {
        AddDeed()
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenContentPreview(){
    CalendarTestTheme { HomeScreen() }
}

@Preview(showBackground = true)
@Composable
fun CalendarTestPreview(){
    CalendarTestTheme {
        CalendarTest()
    }
}

private val exampleDeeds = listOf(
    R.string.Deed1,
    R.string.Deed2,
    R.string.Deed3,
    R.string.Deed4,
    R.string.Deed5,
    R.string.Deed6
).map { DeedString(it) }

private data class DeedString(
    @StringRes val text: Int
)

private lateinit var deedDao: DeedDatabaseDao
private lateinit var db: DeedDatabase