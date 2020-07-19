package com.example.mytodolist

import android.app.Application
import toothpick.Toothpick

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val scope = Toothpick.openScope(Scopes.APP)
    }

    class Scopes {
        companion object {
            val APP = "APP"
        }
    }
}