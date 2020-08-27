package com.example.mytodolist.ui.notes

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
import com.example.mytodolist.di.notesfragment.DaggerNotesComponent
import com.example.mytodolist.di.notesfragment.NotesFragmentModule
import com.example.mytodolist.ui.notes.NotesListAdapter.NoteViewHolder
import com.example.mytodolist.util.NoteTouchHelper
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class NotesFragment : Fragment() {
    @Inject
    lateinit var notesViewModel: NotesViewModel
    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesListAdapter: NotesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFragment()
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        binding.viewmodel = notesViewModel
        initNotesList(binding.notesList)
        return binding.root
    }

    private fun injectFragment() {
        val component = DaggerNotesComponent.builder()
            .notesFragmentModule(NotesFragmentModule(context ?: return))
            .build()
        component?.injectNotesFragment(this)
    }

    private fun initObservers() {
        notesViewModel.notes.observe(this, Observer { notes ->
            notesListAdapter.submitList(notes)
        })

        notesViewModel.onCreateNote.observe(this, Observer {
            if (it) navigateToAddNoteFragment()
        })

        notesViewModel.onCreateQuickNote.observe(this, Observer { createQuickNote() })

        notesViewModel.onViewNoteDetails.observe(this, Observer {
            navigateToNoteDetailFragment(it)
        })
    }

    private fun initNotesList(recyclerView: RecyclerView) {
        notesListAdapter = NotesListAdapter(notesViewModel)
        recyclerView.adapter = notesListAdapter
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

    private fun createQuickNote() {
        notesViewModel.createQuickNote(binding.editQuickNote.text.toString())
        binding.editQuickNote.text.clear()
    }

    private fun navigateToAddNoteFragment() {
        if (findNavController().currentDestination?.id == R.id.notesFragment) {
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
        }
    }

    private fun navigateToNoteDetailFragment(noteId: Long) {
        if (findNavController().currentDestination?.id == R.id.notesFragment) {
            findNavController().navigate(
                NotesFragmentDirections.actionNotesFragmentToNoteDetailsFragment(noteId)
            )
        }
    }
}
