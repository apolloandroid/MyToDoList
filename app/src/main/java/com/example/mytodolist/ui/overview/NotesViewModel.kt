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
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    var notes: LiveData<List<Note>> = getAllNotes()
    private val newNote = Note()

    private var _onViewNoteDetails = MutableLiveData<Long>()
    val onViewNoteDetails: LiveData<Long>
        get() = _onViewNoteDetails

    private var _onCreateNote = MutableLiveData<Boolean>()
    val onCreateNote: LiveData<Boolean>
        get() = _onCreateNote

    private var _onCreateQuickNote = MutableLiveData<Boolean>()
    val onCreateQuickNote: LiveData<Boolean>
        get() = _onCreateQuickNote

    fun createNote() {
        _onCreateNote.value = true
    }

    fun createQuickNote(noteText: String) {
        if (noteText.isNotEmpty()) {
            newNote.noteText = noteText
            viewModelScope.launch {
                repository.insertNote(newNote)
            }
        }
    }

    fun onCreateQuickNoteListener() {
        _onCreateQuickNote.value = true
    }

    fun onSwipeNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note.noteId)
        }
    }

    fun viewNoteDetails(noteId: Long) {
        _onViewNoteDetails.value = noteId
    }

    fun restoreDeletedNote(deletedNote: Note) {
        viewModelScope.launch {
            repository.insertNote(deletedNote)
        }
    }

    fun onCheckDoneClick(note: Note, done: Boolean) {
        if (done) {

        }
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    private fun getAllNotes(): LiveData<List<Note>> {
        return runBlocking { repository.getAllNotes() }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}