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
) : AndroidViewModel(application) {
    private val overviewViewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + overviewViewModelJob)

    var notes: LiveData<List<Note>> = getAllNotes()
    private val newNote = Note()

    fun onSwipeLeft(note: Note) {
        uiScope.launch {
            repository.deleteNote(note.noteId)
        }
    }

    fun fastAddNote(noteText: String) {
        newNote.noteText = noteText
        uiScope.launch {
            repository.insertNote(newNote)
        }
    }

    fun restoreNote(deletedNote: Note) {
        uiScope.launch {
            repository.insertNote(deletedNote)
        }
    }

    fun onCheckDoneClick(note: Note) {
        note.noteDone = !note.noteDone
        uiScope.launch {
            repository.updateNote(note)
        }
    }

    fun onMove(fromNote: Note, toNote: Note) {
        uiScope.launch {
            repository.updateNote(fromNote)
            repository.updateNote(toNote)

        }
    }

    private fun getAllNotes(): LiveData<List<Note>> {
        return runBlocking { repository.getAllNotes() }
    }
}