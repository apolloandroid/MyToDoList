package com.example.mytodolist.ui.addnote

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.Note
import kotlinx.coroutines.*

class AddNoteViewModel(private val repository: Repository, context: Context) : ViewModel() {
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