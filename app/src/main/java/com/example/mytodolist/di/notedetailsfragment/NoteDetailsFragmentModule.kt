package com.example.mytodolist.di.notedetailsfragment

import android.content.Context
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.NoteDataBaseDao
import com.example.mytodolist.repository.database.NoteDatabase
import com.example.mytodolist.ui.notedetails.NoteDetailsViewModel
import com.example.mytodolist.ui.notedetails.NoteDetailsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class NoteDetailsFragmentModule(private val context: Context, private val currentNoteId:Long) {
    @Provides
    fun provideAddNoteViewModel(repository: Repository): NoteDetailsViewModel {
        val noteDetailsViewModelFactory = NoteDetailsViewModelFactory(repository, context, currentNoteId)
        return noteDetailsViewModelFactory.create(NoteDetailsViewModel::class.java)
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