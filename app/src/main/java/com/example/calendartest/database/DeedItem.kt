package com.example.calendartest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_deed_list")
data class DeedItem(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,
    @ColumnInfo(name = "item_name")
    val itemName: String,
    @ColumnInfo(name = "is_completed")
    var isDone: Boolean = false
)