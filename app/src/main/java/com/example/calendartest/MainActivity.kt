@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.calendartest

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.calendartest.JSON.jsonValues
import com.example.calendartest.database.DeedDatabase
import com.example.calendartest.database.DeedDatabaseDao
import com.example.calendartest.database.DeedItem
import com.example.calendartest.database.DeedViewModel
import com.example.calendartest.database.DeedViewModelFactory
import com.example.calendartest.ui.theme.CalendarTestTheme
import com.mabn.calendarlibrary.ExpandableCalendar
import com.mabn.calendarlibrary.core.calendarDefaultTheme
import org.json.JSONArray
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


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

data class Mydeed(val id: Int = 0, val date_start: Long = 0, val date_finish: Long = 0, val name: String = "", val description: String = "")

fun JSONArray.asSeq() =
    sequence {
        val jsonArray = this@asSeq
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            yield(jsonObject)
        }
    }

fun getDeedsJson(deeds: String) = JSONArray(deeds).asSeq().map {
    Mydeed(
        id = it.getInt("id"),
        date_start = it.getLong("date_start"),
        date_finish = it.getLong("date_finish"),
        name = it.getString("name"),
        description = it.getString("description")

    )
}

val Long.localDateTime: LocalDateTime
    get() = LocalDateTime.ofInstant(
        Timestamp(this * 1000L).toInstant(), ZoneId.systemDefault()
    )

fun fillDeeds(dateTime: LocalDateTime, deedList: List<Mydeed>): List<Mydeed> {
    val currentDay = dateTime.toLocalDate().atStartOfDay()
    val emptyDeed = Mydeed(name = "-")
    fun fill(deedList:  Map<Mydeed, LocalDateTime>) =
        (0L until 24L)
            .map { currentDay.plusHours(it)..currentDay.plusHours(it + 1L) }
            .associateWith { deedList.firstNotNullOfOrNull { kv -> if (it.contains(kv.value)) kv else null } }
            .map { (k, v) ->
                v?.key?.copy(date_start = v.key.date_start * 1000L) ?: emptyDeed.copy(
                    date_start = k.start.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000L
                )
            }
    val deedsStart =
        deedList
            .associateWith { it.date_start.localDateTime }
    val deedsEnd =
        deedList
            .map { it.copy(name = it.name + " " + "(Конец)") }
            .associateWith { it.date_finish.localDateTime }
    val deeds = (deedsEnd + deedsStart).filter { it.value.toLocalDate().atStartOfDay() == currentDay }
    return fill(deeds)
}



@Composable
fun Calendar(currentDate: MutableState<LocalDate>) {
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
    text: String,
    modifier: Modifier = Modifier
){
    Button(modifier = modifier, onClick = {  }) {
        Text(
            text = text
        )
    }
}

@Composable
fun DeedsList(
    currentDate: MutableState<LocalDate>,
    modifier: Modifier = Modifier
    //list: List<DeedItem>,
    //mDeedViewModel: DeedViewModel

){
    val deeds = fillDeeds(currentDate.value.atStartOfDay(), getDeedsJson(jsonValues).toList())
    LazyColumn(){
        deeds.map {deed ->
            item {
                Deed(text = deed.name)
            }
        }
    }
    /*val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ){
        items(list) {deed ->
            val name = rememberSaveable { mutableStateOf(deed.isDone) }

            ListItem(headlineText = {Deed(text = deed.itemName)})
        }
    }*/
}

@Composable
fun AddDeed(modifier: Modifier = Modifier){
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            context.startActivity(Intent(context, AddDeedActivity::class.java))
             },
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
    val currentDate = remember { mutableStateOf(LocalDate.now()) }

    //val context = LocalContext.current
    //val mDeedViewModel: DeedViewModel = viewModel(
        //factory = DeedViewModelFactory(context.applicationContext as Application)
    //)
    //val items = mDeedViewModel.readAllData.observeAsState(listOf()).value
    Column(modifier) {
        Calendar(currentDate)
        DeedsList(currentDate)
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

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    CalendarTestTheme {
        //Calendar()
    }
}

@Preview
@Composable
fun DeedPreview(){
    CalendarTestTheme {
        Deed(text = "Hello")
    }
}

@Preview(showBackground = true)
@Composable
fun DeedsListPreview() {
    CalendarTestTheme {
        //DeedsList()
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
fun HomeScreenPreview(){
    CalendarTestTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarTestPreview(){
    CalendarTestTheme {
        CalendarTest()
    }
}
