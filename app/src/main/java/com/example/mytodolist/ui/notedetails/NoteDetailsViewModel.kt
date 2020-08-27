package com.example.mytodolist.ui.notedetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.Note
import kotlinx.coroutines.*


class NoteDetailsViewModel(
    private val repository: Repository, context: Context, private val currentNoteId: Long
) : ViewModel() {
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private var _currentNote = MutableLiveData<Note>()
    val currentNote: LiveData<Note>
        get() = _currentNote

    private var _onNoteUpdated = MutableLiveData<Boolean>()
    val onNoteUpdated: LiveData<Boolean>
        get() = _onNoteUpdated

    init {
        showCurrentNoteText()
    }

    private fun showCurrentNoteText() {
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
        _onNoteUpdated.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
