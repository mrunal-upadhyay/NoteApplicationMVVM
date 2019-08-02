package com.mrunal.noteapplicationmvvm.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mrunal.noteapplicationmvvm.R
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity:AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "com.mrunal.noteapplicationmvvm.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.mrunal.noteapplicationmvvm.EXTRA_DESCRIPTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveNote() {
        if(edit_text_view_title.text.trim().isBlank() || edit_text_view_description.text.trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty note!", Toast.LENGTH_LONG).show()
            return
        }
        val intent = Intent().apply {
            putExtra(EXTRA_TITLE, edit_text_view_title.text.toString())
            putExtra(EXTRA_DESCRIPTION, edit_text_view_description.text.toString())
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}