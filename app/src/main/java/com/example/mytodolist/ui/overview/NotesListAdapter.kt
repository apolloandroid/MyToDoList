package com.example.mytodolist.ui.overview

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.repository.database.Note

object NotesListAdapter : ListAdapter<Note, NotesListAdapter.NoteViewHolder>(NotesDiffCallBack()) {
    lateinit var onItemClickListener: OnNoteItemClickListener<Note>
    lateinit var onCheckDoneClickListener: OnCheckDoneClickListener<Note>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.notes_list_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.noteText.text = currentNote.noteText
        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                onNoteItemClick(position)
            }
        }

        holder.checkDoneNote.isChecked = currentNote.noteDone
        setNoteText(holder, holder.checkDoneNote, holder.noteText)
        holder.checkDoneNote.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                onCheckDoneClick(position)
            }
            setNoteText(holder, holder.checkDoneNote, holder.noteText)
        }
    }

    private fun setNoteText(holder: NoteViewHolder, checkDoneNote: CheckBox, noteTextView: TextView) {
        if (holder.checkDoneNote.isChecked) {
            holder.noteText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.noteText.setTextColor(Color.GRAY)
        } else {
            holder.noteText.paintFlags = Paint.ANTI_ALIAS_FLAG
            holder.noteText.setTextColor(Color.BLACK)
        }
    }

    fun getNoteAt(position: Int): Note = getItem(position)

    class NoteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val noteText: TextView = itemView.findViewById(R.id.note_item_text)
        val checkDoneNote: CheckBox = itemView.findViewById(R.id.check_done)
    }

   private class NotesDiffCallBack : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteText == newItem.noteText
        }
    }

    interface OnNoteItemClickListener<Note> {
        fun onNoteItemClicked(position: Int, noteItem: Note)
    }

    private fun onNoteItemClick(noteItemPosition: Int) {
        onItemClickListener.onNoteItemClicked(noteItemPosition, getItem(noteItemPosition))
    }

    interface OnCheckDoneClickListener<Note> {
        fun onCheckDoneClicked(position: Int, noteItem: Note)
    }

    private fun onCheckDoneClick(noteItemPosition: Int) {
        onCheckDoneClickListener.onCheckDoneClicked(noteItemPosition, getItem(noteItemPosition))
    }
}


