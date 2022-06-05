package ru.omsu.eservice.data.device.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface EducationCardDao {

    @Query("SELECT * FROM education_card_table")
    fun getAll(): List<EducationCardData>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(educationCardData: List<EducationCardData>)


    @Query("DELETE FROM education_card_table")
    fun clear()
}