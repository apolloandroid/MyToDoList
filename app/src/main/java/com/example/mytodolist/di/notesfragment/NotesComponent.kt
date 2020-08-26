package com.example.mytodolist.di.notesfragment

import com.example.mytodolist.ui.notes.NotesFragment
import dagger.Component

@Component(modules = [NotesFragmentModule::class])
interface NotesComponent {
    fun injectNotesFragment(notesFragment: NotesFragment)
}