package com.example.mytodolist.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.NoteDataBaseDao
import com.example.mytodolist.repository.database.NoteDatabase
import com.example.mytodolist.ui.notes.NotesViewModel
import com.example.mytodolist.ui.notes.NotesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class NotesFragmentModule(private val context: Context) {
    @Provides
    fun provideNotesViewModel(repository: Repository): NotesViewModel {
        val notesViewModelFactory = NotesViewModelFactory(repository, context)
        return notesViewModelFactory.create(NotesViewModel::class.java)
    }

    @Provides
    fun provideRepository(noteDatabaseDao: NoteDataBaseDao): Repository {
        return Repository(context, noteDatabaseDao)
    }

    @Provides
    fun provideDatabase(): NoteDatabase {
        return NoteDatabase.getInstance(context)
    }

    @Provides
    fun provideNotesDao(database: NoteDatabase): NoteDataBaseDao {
        return database.noteDataBaseDao
    }
}