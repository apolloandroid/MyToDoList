package com.example.mytodolist.ui.notedetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.repository.Repository
import java.lang.IllegalArgumentException

class NoteDetailsViewModelFactory(
    private val repository: Repository,
    private val context: Context,
    private val currentNoteId:Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailsViewModel::class.java)) {
            return NoteDetailsViewModel(repository, context, currentNoteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}