package com.example.mytodolist.ui.addnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.Note
import kotlinx.coroutines.*

class AddNoteViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
    private val newNote = Note()
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun addNote(noteText: String) {
        newNote.noteText = noteText
        viewModelScope.launch {
            repository.insertNote(newNote)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}