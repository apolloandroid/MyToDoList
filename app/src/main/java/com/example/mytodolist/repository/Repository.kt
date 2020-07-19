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

    fun insert(note: Note) {
        noteDatabaseDao.insert(note)
    }

    fun update(note: Note) {
        noteDatabaseDao.update(note)
    }

    fun get(noteId: Long): Note? =
        noteDatabaseDao.get(noteId)

    fun delete(noteId: Long) {
        noteDatabaseDao.delete(noteId)
    }

    fun clear() {
        noteDatabaseDao.clear()
    }

    fun getAllNotes(): LiveData<List<Note>> = noteDatabaseDao.getAllNotes()
}