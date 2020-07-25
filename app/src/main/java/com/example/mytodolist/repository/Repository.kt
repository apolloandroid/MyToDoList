package com.example.mytodolist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mytodolist.repository.database.Note
import com.example.mytodolist.repository.database.NoteDatabase

class Repository private constructor(application: Application) {
    private val noteDatabaseDao = NoteDatabase.getInstance(application).noteDataBaseDao

    companion object {
        fun getInstance(application: Application): Repository {
            var instance: Repository? = null
            if (instance == null) {
                instance = Repository(application)
            }
            return instance
        }
    }

    suspend fun insert(note: Note) {
        noteDatabaseDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDatabaseDao.updateNote(note)
    }

    suspend fun getNote(noteId: Long): Note? =
        noteDatabaseDao.getNote(noteId)

    suspend fun deleteNote(noteId: Long) {
        noteDatabaseDao.deleteNote(noteId)
    }

    suspend fun clear() {
        noteDatabaseDao.clear()
    }

    suspend fun getAllNotes(): LiveData<List<Note>> = noteDatabaseDao.getAllNotes()
}