package com.example.mytodolist.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val NOTE_DATABASE_NAME = "Notes"

@Database(entities = [Note::class], version = 9, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDataBaseDao: NoteDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext, NoteDatabase::class.java,
                            NOTE_DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}