package com.example.mytodolist.di.notedetailsfragment

import com.example.mytodolist.ui.notedetails.NoteDetailsFragment
import dagger.Component

@Component(modules = [NoteDetailsFragmentModule::class])
interface NoteDetailsComponent {
    fun injectNoteDetailsFragment(noteDetailsFragment: NoteDetailsFragment)
}