package com.mrunal.noteapplicationmvvm.activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrunal.noteapplicationmvvm.R
import com.mrunal.noteapplicationmvvm.adapter.NoteAdapter
import com.mrunal.noteapplicationmvvm.db.entity.Note
import com.mrunal.noteapplicationmvvm.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ADD_NOTE_REQUEST = 1
    private val adapter = NoteAdapter()
    private lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        buttonAddNote.setOnClickListener { it ->
            startActivityForResult(Intent(this, AddNoteActivity::class.java),ADD_NOTE_REQUEST)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer {
            adapter.setNotes(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val newNote: Note = Note(data!!.getStringExtra(AddNoteActivity.EXTRA_TITLE), data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION))
            noteViewModel.insert(newNote)
            Toast.makeText(this, "Note saved!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_LONG).show()
        }
    }
}
