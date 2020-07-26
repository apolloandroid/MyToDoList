package com.example.mytodolist.ui.overview

interface NoteTouchHelperAdapter {
    fun onNoteMove(fromPosition:Int, toPosition:Int)
    fun onNoteSwipe( position:Int)
}