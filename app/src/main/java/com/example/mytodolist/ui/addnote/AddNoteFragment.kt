package com.example.mytodolist.ui.addnote

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.AddNoteFragmentBinding
import com.example.mytodolist.di.addnotefragment.AddNoteFragmentModule
import com.example.mytodolist.di.addnotefragment.DaggerAddNoteComponent
import javax.inject.Inject


class AddNoteFragment : Fragment() {
    @Inject
    lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var binding: AddNoteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_note_fragment, container, false)
        injectFragment()
        addNoteViewModel.onAddNote.observe(this, Observer {
            if (it) navigateToNotesFragment()
        })
        binding.buttonAddNote.setOnClickListener {
            onAddButtonClickListener()
        }
        return binding.root
    }

    private fun injectFragment() {
        val component = DaggerAddNoteComponent.builder()
            .addNoteFragmentModule(AddNoteFragmentModule(context ?: return))
            .build()
        component?.injectAddNoteFragment(this)
    }

    private fun onAddButtonClickListener() {
        if (binding.editAddNote.text!!.isNotEmpty())
            addNoteViewModel.addNote(binding.editAddNote.text.toString())
    }

    private fun navigateToNotesFragment() {
        binding.editAddNote.hideKeyboard()
        if (findNavController().currentDestination?.id == R.id.addNoteFragment) {
            findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
        }
    }

    private fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
