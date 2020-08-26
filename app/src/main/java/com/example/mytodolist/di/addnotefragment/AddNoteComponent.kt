package com.example.mytodolist.di.addnotefragment

import com.example.mytodolist.ui.addnote.AddNoteFragment
import dagger.Component

@Component(modules = [AddNoteFragmentModule::class])
interface AddNoteComponent {
    fun injectAddNoteFragment(addNoteFragment: AddNoteFragment)
}