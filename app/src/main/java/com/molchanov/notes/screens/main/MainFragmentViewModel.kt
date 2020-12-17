package com.molchanov.notes.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.molchanov.notes.utils.REPOSITORY

class MainFragmentViewModel (application : Application) : AndroidViewModel(application) {
    val allNotes = REPOSITORY.allNotes

    fun signOut() {
        REPOSITORY.signOut()
    }
}