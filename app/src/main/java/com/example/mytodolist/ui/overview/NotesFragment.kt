package com.example.mytodolist.ui.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentNotesBinding
import com.example.mytodolist.ui.overview.NotesListAdapter.NoteViewHolder
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.util.NoteTouchHelper
import com.google.android.material.snackbar.Snackbar

class NotesFragment : Fragment(){
    private val notesViewModel: NotesViewModel by lazy { initViewModel() }
    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesListAdapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        binding.buttonCreateNote.setOnClickListener { onCreateNoteListener() }
        binding.buttonFastCreateNote.setOnClickListener { onCreateQuickNoteListener() }
        initNotesList(binding.notesList)
        notesViewModel.notes.observe(this, Observer { notes ->
            notesListAdapter.submitList(notes)
        })

        notesViewModel.viewNoteDetails.observe(this, Observer {
            navigateToNoteDetailFragment(it)
        })
        return binding.root
    }

    private fun initViewModel(): NotesViewModel {
        val application = requireNotNull(activity).application
        val repository = Repository.getInstance(application)
        val overviewViewModelFactory = NotesViewModelFactory(repository, application)
        return overviewViewModelFactory.create(NotesViewModel::class.java)
    }

    private fun initNotesList(recyclerView: RecyclerView) {
        notesListAdapter = NotesListAdapter(notesViewModel)
        recyclerView.adapter = notesListAdapter
//        notesListAdapter.onCheckDoneClickListener = this
        val noteTouchHelper = ItemTouchHelper(initSwipeHelper())
        recyclerView.setHasFixedSize(true)
        noteTouchHelper.attachToRecyclerView(binding.notesList)
    }

    private fun initSwipeHelper(): NoteTouchHelper = object : NoteTouchHelper() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val holder = viewHolder as NoteViewHolder
            var currentNote = notesListAdapter.getNoteAt(holder.adapterPosition)
            notesViewModel.onSwipeNote(notesListAdapter.getNoteAt(holder.adapterPosition))
            Snackbar.make(binding.root, "Deleted", Snackbar.LENGTH_LONG).setAction("UNDO") {
                notesViewModel.restoreDeletedNote(currentNote)
            }.show()
        }
    }

    private fun onCreateNoteListener() {
        findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
    }

    private fun onCreateQuickNoteListener() {
        if (binding.editNoteOverview.text!!.isNotEmpty())
            notesViewModel.createQuickNote(binding.editNoteOverview.text.toString())
        binding.editNoteOverview.text.clear()
    }


//    override fun onCheckDoneClicked(position: Int, noteItem: Note) {
//        overviewViewModel.onCheckDoneClick(noteItem)
//    }

    private fun navigateToNoteDetailFragment(noteId: Long) {
        findNavController().navigate(
            NotesFragmentDirections.actionNotesFragmentToNoteDetailsFragment(noteId)
        )
    }
}
