package com.example.mytodolist.ui.editnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentEditNoteBinding
import com.example.mytodolist.repository.Repository

class EditNoteFragment : Fragment() {
    private lateinit var editNoteViewModel: EditNoteViewModel
    private lateinit var binding: FragmentEditNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentNoteId = EditNoteFragmentArgs.fromBundle(arguments!!).currentNoteId
        editNoteViewModel = initViewModel(currentNoteId)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_note, container, false)
        binding.buttonEditNote.setOnClickListener {
            onEditNoteButtonListener(binding.editNoteText.text.toString())
        }
        showCurrentNoteText(currentNoteId)
        editNoteViewModel.currentNote.observe(viewLifecycleOwner, Observer { currentNote ->
            binding.editNoteText.text?.insert(0, currentNote.noteText)
        })
        return binding.root
    }

    private fun initViewModel(currentNoteId: Long): EditNoteViewModel {
        val application = requireNotNull(activity).application
        val repository = Repository.getInstance(application)
        val editNoteViewModelFactory =
            EditNoteViewModelFactory(repository, application, currentNoteId)
        return editNoteViewModelFactory.create(EditNoteViewModel::class.java)
    }

    private fun showCurrentNoteText(currentNoteId: Long) {
        editNoteViewModel.showCurrentNoteText(currentNoteId)
    }

    private fun onEditNoteButtonListener(currentNoteText: String) {
        editNoteViewModel.updateNote(currentNoteText)
        findNavController().navigate(
            EditNoteFragmentDirections.actionEditNoteFragmentToOverviewFragment(
                0
            )
        )
    }
}
