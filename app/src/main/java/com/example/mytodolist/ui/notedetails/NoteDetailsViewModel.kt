package com.example.mytodolist.ui.notedetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.Note
import kotlinx.coroutines.*


class NoteDetailsViewModel(
    private val repository: Repository, application: Application, private val currentNoteId: Long
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private var _currentNote = MutableLiveData<Note>()
    val currentNote: LiveData<Note>
        get() = _currentNote

    fun showCurrentNoteText(currentNoteId: Long) {
        viewModelScope.launch {
            _currentNote.postValue(repository.getNote(currentNoteId))
        }
    }

    fun updateNote(currentNoteText: String) {
        if (currentNoteText == "") return
        _currentNote.value?.noteText = currentNoteText
        viewModelScope.launch {
            repository.updateNote(_currentNote.value as Note)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
