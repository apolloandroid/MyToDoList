package com.example.mytodolist.di

import com.example.mytodolist.ui.notes.NotesFragment
import dagger.Component

@Component(modules = [NotesFragmentModule::class])
interface AppComponent {
    fun injectNotesFragment(notesFragment: NotesFragment)
}