package com.example.mytodolist.ui.addnote

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.AddNoteFragmentBinding
import com.example.mytodolist.repository.Repository

class AddNoteFragment : Fragment() {
    private lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var binding: AddNoteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_note_fragment, container, false)
        addNoteViewModel = initViewModel()
        binding.buttonAddNote.setOnClickListener {
            onAddButtonClickListener()
        }
        return binding.root
    }

    private fun initViewModel(): AddNoteViewModel {
        val application = requireNotNull(activity).application
        val repository = Repository.getInstance(application)
        val addNoteViewModelFactory = AddNoteViewModelFactory(repository, application)
        return addNoteViewModelFactory.create(AddNoteViewModel::class.java)
    }

    private fun onAddButtonClickListener() {
        if (binding.editAddNote.text!!.isNotEmpty())
            addNoteViewModel.addNote(binding.editAddNote.text.toString())
        binding.editAddNote.hideKeyboard()
        findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
    }

    private fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
