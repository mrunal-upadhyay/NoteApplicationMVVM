package com.mrunal.noteapplicationmvvm.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mrunal.noteapplicationmvvm.db.dao.NoteDao
import com.mrunal.noteapplicationmvvm.db.entity.Note

@Database(entities = [Note::class],version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {
                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            NoteDatabase::class.java, "notes_database"
                    )
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }

    class PopulateDbAsyncTask(db:NoteDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = db?.noteDao()

        override fun doInBackground(vararg params: Unit?) {
            noteDao?.insert(Note("Title 1", "description 1"))
            noteDao?.insert(Note("Title 2", "description 2"))
            noteDao?.insert(Note("Title 3", "description 3"))
        }

    }

}
