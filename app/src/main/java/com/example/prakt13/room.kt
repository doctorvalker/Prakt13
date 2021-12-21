package com.example.prakt13

import androidx.room.*

@Entity
data class DataBaseModel(
    @ColumnInfo(name="anime") val anime: String,
    @ColumnInfo(name="character") val character: String,
    @ColumnInfo(name="quote") val quote: String
) {
    @PrimaryKey (autoGenerate = true) var id: Int = 0
}

@Dao
interface DBDao {

    @Insert
    fun insert(vararg dataBaseModel: DataBaseModel)

    @Query("SELECT * FROM DataBaseModel")
    fun getAll(): List<DataBaseModel>

    @Query ("DELETE FROM DataBaseModel")
    fun deleteAll()

    @Delete
    fun delete(dataBaseModel: DataBaseModel )
}

@Database(entities = [DataBaseModel::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun DBDao() : DBDao
}