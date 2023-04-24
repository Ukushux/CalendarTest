package com.example.calendartest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeedDatabaseDao {
    @Query("SELECT * from my_deed_list")
    fun getAll(): List<DeedItem>
    @Query("SELECT * from my_deed_list where id = :id")
    fun getById(id: Int) : DeedItem?
    @Insert
    fun insert(item:DeedItem)
    @Update
    fun update(item:DeedItem)
    @Delete
    fun delete(item:DeedItem)
    @Query("DELETE FROM my_deed_list")
    fun deleteAllDeeds()
}