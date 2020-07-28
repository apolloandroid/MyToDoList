package com.example.mytodolist.repository.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDataBaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM notes where noteId = :noteId")
    fun get(noteId: Long): Note?

    @Query("DELETE FROM notes where noteId = :noteId")
    fun delete(noteId: Long)

    @Query("SELECT * FROM notes ORDER BY noteId DESC")
    fun getAll(): LiveData<List<Note>>
}