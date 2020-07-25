package com.example.mytodolist.repository.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDataBaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun updateNote(note: Note)

    @Query("SELECT * FROM notes where noteId = :noteId")
    fun getNote(noteId: Long): Note?

    @Query("DELETE FROM notes where noteId = :noteId")
    fun deleteNote(noteId: Long)

    @Query("DELETE FROM notes")
    fun clear()

    @Query("SELECT * FROM notes ORDER BY noteId DESC ")
    fun getAllNotes(): LiveData<List<Note>>
}