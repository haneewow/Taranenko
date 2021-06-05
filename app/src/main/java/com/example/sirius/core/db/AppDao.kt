package com.example.sirius.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sirius.domain.model.data.DeveloperNote

@Dao
interface AppDao {
    @Query("SELECT * FROM DeveloperNote WHERE numberPage = :number")
    fun getNoteForNumber(number: Int): DeveloperNote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: DeveloperNote)
}