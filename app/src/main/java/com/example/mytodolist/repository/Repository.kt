package com.example.mytodolist.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.mytodolist.repository.database.NoteDatabase

class Repository private constructor(context: Context) {
    private val noteDatabaseDao = NoteDatabase.getInstance(context).noteDataBaseDao

    companion object {
        fun getInstance(context: Context): Repository {
            var instance: Repository? = null
            if (instance == null) {
                instance = Repository(context)
            }
            return instance
        }
    }

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