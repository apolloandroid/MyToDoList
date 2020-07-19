package com.example.mytodolist.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,
//    @ColumnInfo(name = "note_title")
//    var noteTitle: String = "",
    @ColumnInfo(name = "note_text")
    var noteText: String = "",

    @ColumnInfo(name = "note_done")
    var noteDone: Boolean = false
)