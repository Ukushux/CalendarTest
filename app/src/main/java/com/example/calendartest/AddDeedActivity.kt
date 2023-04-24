@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.calendartest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendartest.ui.theme.CalendarTestTheme

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

private val TimeData = listOf(
    R.string.t00,
    R.string.t01,
    R.string.t02,
    R.string.t03,
    R.string.t04,
    R.string.t05,
    R.string.t06,
    R.string.t07,
    R.string.t08,
    R.string.t09,
    R.string.t10,
    R.string.t11,
    R.string.t12,
    R.string.t13,
    R.string.t14,
    R.string.t15,
    R.string.t16,
    R.string.t17,
    R.string.t18,
    R.string.t19,
    R.string.t20,
    R.string.t21,
    R.string.t22,
    R.string.t23,
    R.string.t24,
).map { StringTime(it) }

@Composable
fun DeedField(
    //@StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text1 by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }
        var text3 by remember { mutableStateOf("") }
        var text4 by remember { mutableStateOf("") }

        TextField(
            value  = text1,
            onValueChange = { text1 = it },
            modifier = modifier)

        TextField(
            value = text2,
            onValueChange = {text2 = it},
            modifier = modifier)
        TextField(
            value  = text3,
            onValueChange = { text3 = it },
            modifier = modifier)

        TextField(
            value = text4,
            onValueChange = {text4 = it},
            modifier = modifier)
    }
}

@Composable
fun AddCancelBar(modifier: Modifier = Modifier){
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(120.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { context.startActivity(Intent(context, MainActivity::class.java)) }) {
            
        }
        Button(onClick = { /*TODO*/ }) {
            
        }
    }
}

@Composable
fun DeedScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DeedField()
        AddCancelBar()
    }
}

@Composable
fun SecondScreen(modifier: Modifier = Modifier){
    CalendarTestTheme {
        Scaffold()
        {                padding ->
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
fun DeedScreenPreview(){
    CalendarTestTheme {
        DeedScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview(){
    CalendarTestTheme {
        SecondScreen()
    }
}