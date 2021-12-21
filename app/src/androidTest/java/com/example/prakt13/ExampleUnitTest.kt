package com.example.prakt13

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {
    private lateinit var animeDao: DBDao
    private lateinit var db: DataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DataBase::class.java).build()
        animeDao = db.DBDao()
    }
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    @Test
    fun insert_is_correct() {
        val previousSize = animeDao.getAll().size
        animeDao.insert(DataBaseModel("One Punch Man", "Saitama", "some quote"))
        val nowSize = animeDao.getAll().size
        assert(previousSize < nowSize)
    }
    @Test
    fun delete_is_correct() {
        animeDao.insert(DataBaseModel("One Punch Man", "Saitama", "some quote"))
        val previousSize = animeDao.getAll().size
        animeDao.delete(animeDao.getAll()[0])
        val nowSize = animeDao.getAll().size
        assert(previousSize > nowSize)
    }
    @Test
    fun deleteAll_is_correct() {
        animeDao.insert(DataBaseModel("One Punch Man", "Saitama", "some quote"))
        animeDao.deleteAll()
        val nowSize = animeDao.getAll().size
        assert(nowSize == 0)
    }
}