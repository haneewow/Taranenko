package com.example.gallery.core.db

import androidx.room.*
import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.ui.adapter.Category

@Dao
interface AppDao {
    @Query("SELECT * FROM DeveloperNote WHERE numberPage = :number AND category = :category")
    fun getNoteForNumber(number: Int, category: Category): DeveloperNote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: DeveloperNote)

    @Query("DELETE FROM DeveloperNote WHERE category =:category")
    fun deleteByCategory(category: Category)
}