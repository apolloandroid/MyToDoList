package com.example.mytodolist.di.addnotefragment

import android.content.Context
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.NoteDataBaseDao
import com.example.mytodolist.repository.database.NoteDatabase
import com.example.mytodolist.ui.addnote.AddNoteViewModel
import com.example.mytodolist.ui.addnote.AddNoteViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AddNoteFragmentModule(private val context: Context) {
    @Provides
    fun provideAddNoteViewModel(repository: Repository): AddNoteViewModel {
        val addNoteViewModelFactory = AddNoteViewModelFactory(repository, context)
        return addNoteViewModelFactory.create(AddNoteViewModel::class.java)
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