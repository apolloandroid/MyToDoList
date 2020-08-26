package com.example.mytodolist.ui.addnote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.Note
import kotlinx.coroutines.*

class AddNoteViewModel(private val repository: Repository, context: Context) : ViewModel() {
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private val newNote = Note()

    private var __onNoteAdded = MutableLiveData<Boolean>()
    val onAddNote: LiveData<Boolean>
        get() = __onNoteAdded


    fun addNote(noteText: String) {
        newNote.noteText = noteText
        viewModelScope.launch {
            repository.insertNote(newNote)
        }
        __onNoteAdded.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}