package com.example.mytodolist.ui.overview

import android.graphics.Color
import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:doneNote")
fun setStyle(textView: TextView, isDone: Boolean) {
    if (isDone) {
        textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        textView.setTextColor(Color.GRAY)
    } else {
        textView.paintFlags = Paint.ANTI_ALIAS_FLAG
        textView.setTextColor(Color.BLACK)
    }
}