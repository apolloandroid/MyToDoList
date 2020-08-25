package com.example.mytodolist.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.mytodolist.repository.database.Note
import com.example.mytodolist.repository.database.NoteDataBaseDao
import com.example.mytodolist.repository.database.NoteDatabase
import javax.inject.Inject

class Repository @Inject constructor(
    context: Context,
    private val noteDatabaseDao: NoteDataBaseDao
) {

    suspend fun insertNote(note: Note) {
        noteDatabaseDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDatabaseDao.update(note)
    }

    suspend fun getNote(noteId: Long): Note? =
        noteDatabaseDao.get(noteId)

    suspend fun deleteNote(noteId: Long) {
        noteDatabaseDao.delete(noteId)
    }

    suspend fun getAllNotes(): LiveData<List<Note>> = noteDatabaseDao.getAll()
}