package com.example.mytodolist.ui.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.Note
import kotlinx.coroutines.*


class OverviewViewModel constructor(
    private val repository: Repository,
    application: Application
) :
    AndroidViewModel(application) {
    private val overviewViewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + overviewViewModelJob)
    val notes: LiveData<List<Note>> = repository.getAllNotes()
    private val newNote = Note()

    fun onSwipeLeft(note: Note) {
        uiScope.launch {
            deleteNote(note)
        }
    }

    fun fastAddNote(noteText: String) {
        this.newNote.noteText = noteText
        uiScope.launch {
            insertNewNote()
        }
    }

    fun restoreNote(deletedNote: Note) {
        uiScope.launch {
            restoreDeletedNote(deletedNote)
        }
    }

    fun onCheckDoneClick(note: Note) {
        note.noteDone = !note.noteDone
        uiScope.launch {
            updateNote(note)
        }
    }

    private suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            repository.delete(note.noteId)
        }
    }

    private suspend fun insertNewNote() {
        withContext(Dispatchers.IO) {
            repository.insert(newNote)
        }
    }

    private suspend fun restoreDeletedNote(deletedNote: Note) {
        withContext(Dispatchers.IO) {
            repository.insert(deletedNote)
        }
    }

    private suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            repository.update(note)
        }
    }
}