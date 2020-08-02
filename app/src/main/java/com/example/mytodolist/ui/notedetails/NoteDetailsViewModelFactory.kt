package com.example.mytodolist.ui.notedetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.repository.Repository
import java.lang.IllegalArgumentException

class NoteDetailsViewModelFactory(
    private val repository: Repository,
    private val application: Application,
    private val currentNoteId:Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailsViewModel::class.java)) {
            return NoteDetailsViewModel(repository, application, currentNoteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}