package com.example.mytodolist.ui.addnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.Note
import kotlinx.coroutines.*

class AddNoteViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
    private val newNote = Note()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun addNote(noteText: String) {
        this.newNote.noteText = noteText
        uiScope.launch {
            insertNewNote()
        }
    }

    private suspend fun insertNewNote() {
        withContext(Dispatchers.IO) {
            repository.insertNote(newNote)
        }
    }
}