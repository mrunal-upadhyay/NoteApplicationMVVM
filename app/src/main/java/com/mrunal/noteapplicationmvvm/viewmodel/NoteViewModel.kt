package com.mrunal.noteapplicationmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mrunal.noteapplicationmvvm.NoteRepository
import com.mrunal.noteapplicationmvvm.db.entity.Note

class NoteViewModel(application:Application) : AndroidViewModel(application) {

    private var repository:NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes():LiveData<List<Note>> {
        return repository.getAllNotes()
    }
}