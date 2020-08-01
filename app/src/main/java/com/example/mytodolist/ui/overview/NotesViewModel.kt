package com.example.mytodolist.ui.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.Note
import kotlinx.coroutines.*


class NotesViewModel constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {
    private val overviewViewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + overviewViewModelJob)

    var notes: LiveData<List<Note>> = getAllNotes()
    private val newNote = Note()

    private var _viewNoteDetailsEvent = MutableLiveData<Long>()
    val viewNoteDetails: LiveData<Long>
        get() = _viewNoteDetailsEvent

    fun onSwipeNote(note: Note) {
        uiScope.launch {
            repository.deleteNote(note.noteId)
        }
    }

    fun viewNoteDetails(noteId: Long) {
        _viewNoteDetailsEvent.value = noteId
    }

    fun createQuickNote(noteText: String) {
        newNote.noteText = noteText
        uiScope.launch {
            repository.insertNote(newNote)
        }
    }

    fun restoreDeletedNote(deletedNote: Note) {
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

    private fun getAllNotes(): LiveData<List<Note>> {
        return runBlocking { repository.getAllNotes() }
    }
}