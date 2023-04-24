package com.example.calendartest.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calendartest.JSON.jsonValues
import org.json.JSONArray

class DeedRepository(private val deedDatabaseDao: DeedDatabaseDao) {
    private fun JSONArray.asSeq() =
        sequence {
            val jsonArray = this@asSeq
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                yield(jsonObject)
            }
        }

    private fun getDeedsJson(deeds: String) = JSONArray(deeds).asSeq().map {
        DeedItem(
            id = it.getInt("id"),
            date_start = it.getLong("date_start"),
            date_finish = it.getLong("date_finish"),
            name = it.getString("name"),
            description = it.getString("description")
        )
    }

    suspend fun getDeeds(): List<DeedItem> {
        val all = deedDatabaseDao.getAll()
        return all.ifEmpty {
            val json = getDeedsJson(jsonValues)
            val data = json.toList()
            data.forEach {
                deedDatabaseDao.insert(it)
            }
            data
        }
    }

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
