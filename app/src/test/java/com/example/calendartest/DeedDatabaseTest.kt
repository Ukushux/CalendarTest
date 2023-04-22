package com.example.calendartest

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.calendartest.database.DeedDatabase
import com.example.calendartest.database.DeedDatabaseDao
import com.example.calendartest.database.DeedItem
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DeedDatabaseTest {

    private lateinit var deedDao: DeedDatabaseDao
    private lateinit var db: DeedDatabase

    @Before
    fun createDb() {
        val context =
            InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, DeedDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        deedDao = db.deedDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetDeed() = runBlocking {
        val deedItem = DeedItem(itemId = 1, itemName = "Dummy Item", isDone = false)
        deedDao.insert(deedItem)
        val oneItem = deedDao.getById(1)
        assertEquals(oneItem?.itemId, 1)
    }
}