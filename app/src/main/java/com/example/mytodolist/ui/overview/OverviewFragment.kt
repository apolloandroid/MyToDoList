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
import com.example.mytodolist.databinding.FragmentOverviewBinding
import com.example.mytodolist.ui.overview.NotesListAdapter.NoteViewHolder
import com.example.mytodolist.ui.overview.NotesListAdapter.OnCheckDoneClickListener
import com.example.mytodolist.repository.Repository
import com.example.mytodolist.repository.database.Note
import com.example.mytodolist.util.NoteTouchHelper
import com.google.android.material.snackbar.Snackbar

class OverviewFragment : Fragment(), NotesListAdapter.OnNoteItemClickListener<Note>,
    OnCheckDoneClickListener<Note> {
    private val overviewViewModel: OverviewViewModel by lazy { initViewModel() }
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        binding.buttonCreateNote.setOnClickListener { onCreateNoteListener() }
        binding.buttonFastCreateNote.setOnClickListener { onCreateQuickNoteListener() }
        initNotesList(binding.notesList)
        overviewViewModel.notes.observe(viewLifecycleOwner, Observer { notes ->
            NotesListAdapter.submitList(notes)
        })
        return binding.root
    }

    private fun initViewModel(): OverviewViewModel {
        val application = requireNotNull(activity).application
        val repository = Repository.getInstance(application)
        val overviewViewModelFactory = OverviewViewModelFactory(repository, application)
        return overviewViewModelFactory.create(OverviewViewModel::class.java)
    }

    private fun initNotesList(recyclerView: RecyclerView) {
        recyclerView.adapter = NotesListAdapter
        NotesListAdapter.onItemClickListener = this
        NotesListAdapter.onCheckDoneClickListener = this
        val noteTouchHelper = ItemTouchHelper(initSwipeHelper())
        recyclerView.setHasFixedSize(true)
        noteTouchHelper.attachToRecyclerView(binding.notesList)
    }

    private fun initSwipeHelper(): NoteTouchHelper = object : NoteTouchHelper() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val holder = viewHolder as NoteViewHolder
            var currentNote = NotesListAdapter.getNoteAt(holder.adapterPosition)
            overviewViewModel.onSwipeNote(NotesListAdapter.getNoteAt(holder.adapterPosition))
            Snackbar.make(binding.root, "Deleted", Snackbar.LENGTH_LONG).setAction("UNDO") {
                overviewViewModel.restoreDeletedNote(currentNote)
            }.show()
        }
    }

    private fun onCreateNoteListener() {
        findNavController().navigate(R.id.action_overviewFragment_to_addNoteFragment)
    }

    private fun onCreateQuickNoteListener() {
        if (binding.editNoteOverview.text!!.isNotEmpty())
            overviewViewModel.createQuickNote(binding.editNoteOverview.text.toString())
        binding.editNoteOverview.text.clear()
    }

    override fun onNoteItemClicked(position: Int, noteItem: Note) {
        findNavController().navigate(
            OverviewFragmentDirections.actionOverviewFragmentToEditNoteFragment(noteItem.noteId)
        )
    }

    override fun onCheckDoneClicked(position: Int, noteItem: Note) {
        overviewViewModel.onCheckDoneClick(noteItem)
    }
}
