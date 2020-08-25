package com.example.mytodolist.ui.notedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentNoteDetailsBinding
import com.example.mytodolist.repository.Repository

class NoteDetailsFragment : Fragment() {
    private lateinit var noteDetailsViewModel: NoteDetailsViewModel
    private lateinit var binding: FragmentNoteDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentNoteId = NoteDetailsFragmentArgs.fromBundle(arguments!!).currentNoteId
//        noteDetailsViewModel = initViewModel(currentNoteId)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false)
        binding.buttonEditNote.setOnClickListener {
            onEditNoteButtonListener(binding.editNoteText.text.toString())
        }
        showCurrentNoteText(currentNoteId)
        noteDetailsViewModel.currentNote.observe(viewLifecycleOwner, Observer { currentNote ->
            binding.editNoteText.text?.insert(0, currentNote.noteText)
        })
        return binding.root
    }

//    private fun initViewModel(currentNoteId: Long): NoteDetailsViewModel {
//        val application = requireNotNull(activity).application
//        val repository = Repository.getInstance(application)
//        val editNoteViewModelFactory =
//            NoteDetailsViewModelFactory(repository, application, currentNoteId)
//        return editNoteViewModelFactory.create(NoteDetailsViewModel::class.java)
//    }

    private fun showCurrentNoteText(currentNoteId: Long) {
        noteDetailsViewModel.showCurrentNoteText(currentNoteId)
    }

    private fun onEditNoteButtonListener(currentNoteText: String) {
        noteDetailsViewModel.updateNote(currentNoteText)
        findNavController().navigate(
            NoteDetailsFragmentDirections.actionNoteDetailsFragmentToNotesFragment(
                0
            )
        )
    }
}
