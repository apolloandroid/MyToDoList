package com.example.mytodolist.editnote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.NoteDataBaseDao
import java.lang.IllegalArgumentException

class EditNoteViewModelFactory(
    private val repository: Repository,
    private val application: Application,
    private val currentNoteId:Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(repository, application, currentNoteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}