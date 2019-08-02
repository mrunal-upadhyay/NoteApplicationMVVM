package com.mrunal.noteapplicationmvvm

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.mrunal.noteapplicationmvvm.db.NoteDatabase
import com.mrunal.noteapplicationmvvm.db.dao.NoteDao
import com.mrunal.noteapplicationmvvm.db.entity.Note

class NoteRepository(application: Application) {

    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Note>>

    init {
        val database: NoteDatabase = NoteDatabase.getInstance(
                application.applicationContext
        )!!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note:Note){
        val insertNoteAsyncTask = InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        val deleteAllNotesAsyncTask = DeleteAllNotesAsyncTask(noteDao).execute()
    }

    fun getAllNotes():LiveData<List<Note>> {
        return allNotes
    }

    class DeleteAllNotesAsyncTask(noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>() {

        private val noteDao = noteDao
        override fun doInBackground(vararg params: Unit) {
            noteDao.deleteAllNotes()
        }

    }

    class InsertNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {

        private val noteDao = noteDao
        override fun doInBackground(vararg params: Note) {
            noteDao.insert(params[0])
        }

    }


}