package com.molchanov.notes.screens.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.molchanov.notes.database.firebase.AppFirebaseRepository
import com.molchanov.notes.database.room.AppRoomDatabase
import com.molchanov.notes.database.room.AppRoomRepository
import com.molchanov.notes.utils.REPOSITORY
import com.molchanov.notes.utils.TYPE_FIREBASE
import com.molchanov.notes.utils.TYPE_ROOM
import com.molchanov.notes.utils.showToast

class StartFragmentViewModel (application: Application) : AndroidViewModel(application) {

    private val mContext = application

    fun  initDatabase(type : String, onSuccess: () -> Unit) {
        when(type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(mContext).getAppRoomDao()
                REPOSITORY = AppRoomRepository(dao)
                onSuccess()
            }
            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase({onSuccess()}, { showToast(it)})
            }
        }
    }
}