package com.example.calendartest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_deed_list")
data class DeedItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "date_start")
    val date_start: Long = 0L,
    @ColumnInfo(name = "date_finish")
    val date_finish: Long = 0L,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,

)