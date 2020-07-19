package com.example.mytodolist.editnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.Note
import com.example.mytodolist.repository.database.NoteDataBaseDao
import kotlinx.coroutines.*


class EditNoteViewModel(
    private val repository: Repository, application: Application, private val currentNoteId: Long
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _currentNote = MutableLiveData<Note>()
    val currentNote: LiveData<Note>
        get() = _currentNote

    fun editNote(currentNoteId: Long) {
        uiScope.launch {
            initCurrentNote(currentNoteId)
        }
    }

    fun refreshNote(currentNoteText: String) {
        if (currentNoteText == "") return
        _currentNote.value?.noteText = currentNoteText
        uiScope.launch {
            updateNote(_currentNote.value as Note)
        }
    }

    private suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            repository.update(note)
        }
    }

    private suspend fun initCurrentNote(noteId: Long) {
        withContext(Dispatchers.IO) {
            _currentNote.postValue(repository.get(noteId))
        }
    }
}
