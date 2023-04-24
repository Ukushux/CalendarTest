package com.example.calendartest.database

import androidx.lifecycle.LiveData
class DeedRepository(private val deedDatabaseDao: DeedDatabaseDao) {
    val readAllData : LiveData<List<DeedItem>> =  deedDatabaseDao.getAll()
    suspend fun addDeed(deedItem: DeedItem) {
        deedDatabaseDao.insert(deedItem)
    }
    suspend fun updateDeed(deedItem: DeedItem) {
        deedDatabaseDao.update(deedItem)
    }
    suspend fun deleteDeed(deedItem: DeedItem) {
        deedDatabaseDao.delete(deedItem)
    }
    suspend fun deleteAllDeeds() {
        deedDatabaseDao.deleteAllDeeds()
    }
}
