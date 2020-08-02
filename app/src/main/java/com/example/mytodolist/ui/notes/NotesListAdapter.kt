package com.example.mytodolist.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.databinding.NotesListItemBinding
import com.example.mytodolist.repository.Note

class NotesListAdapter(private val notesViewModel: NotesViewModel) :
    ListAdapter<Note, NotesListAdapter.NoteViewHolder>(NotesDiffCallBack()) {

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(notesViewModel, currentNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    fun getNoteAt(position: Int): Note = getItem(position)

    class NoteViewHolder private constructor(private val binding: NotesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val noteText: TextView = itemView.findViewById(R.id.note_item_text)

        fun bind(viewModel: NotesViewModel, currentNote: Note) {
            binding.note = currentNote
            binding.viewmodel = viewModel
        }

        companion object {
            fun from(parent: ViewGroup): NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NotesListItemBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding)
            }
        }
    }

    private class NotesDiffCallBack : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteText == newItem.noteText
        }
    }

}





