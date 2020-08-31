package com.example.mytodolist.ui.notedetails

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
import com.example.mytodolist.databinding.FragmentNoteDetailsBinding
import com.example.mytodolist.di.notedetailsfragment.DaggerNoteDetailsComponent
import com.example.mytodolist.di.notedetailsfragment.NoteDetailsFragmentModule
import javax.inject.Inject

class NoteDetailsFragment : Fragment() {
    @Inject
    lateinit var noteDetailsViewModel: NoteDetailsViewModel
    private lateinit var binding: FragmentNoteDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false)

        val currentNoteId = NoteDetailsFragmentArgs.fromBundle(arguments!!).currentNoteId

        injectFragment(currentNoteId)

        initObservers()

        binding.buttonEditNote.setOnClickListener {
            onEditNoteButtonListener(binding.editNoteText.text.toString())
        }

        return binding.root
    }

    private fun injectFragment(currentNoteId: Long) {
        val component = DaggerNoteDetailsComponent.builder()
            .noteDetailsFragmentModule(NoteDetailsFragmentModule(context ?: return, currentNoteId))
            .build()
        component.injectNoteDetailsFragment(this)
    }

    private fun initObservers() {
        noteDetailsViewModel.currentNote.observe(viewLifecycleOwner, Observer { currentNote ->
            binding.editNoteText.text?.insert(0, currentNote.noteText)
        })
        noteDetailsViewModel.onNoteUpdated.observe(viewLifecycleOwner, Observer {
            if (it) navigateToNoteFragment()
        })
    }

    private fun onEditNoteButtonListener(currentNoteText: String) {
        noteDetailsViewModel.updateNote(currentNoteText)
        binding.editNoteText.hideKeyboard()
    }

    private fun navigateToNoteFragment() {
        if (findNavController().currentDestination?.id == R.id.noteDetailsFragment) {
            findNavController().navigate(R.id.action_noteDetailsFragment_to_notesFragment)
        }
    }

    private fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
