package com.molchanov.notes.screens.add_new_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.molchanov.notes.R
import com.molchanov.notes.databinding.FragmentAddNewNoteBinding
import com.molchanov.notes.model.AppNote
import com.molchanov.notes.utils.APP_ACTIVITY
import com.molchanov.notes.utils.showToast

class AddNewNoteFragment : Fragment() {

    private var _binding : FragmentAddNewNoteBinding? = null
    private val mBinding get() = _binding!!
    lateinit var mViewModel : AddNewNoteFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNewNoteBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mViewModel = ViewModelProvider(this).get(AddNewNoteFragmentViewModel::class.java)
        mBinding.btnAddNote.setOnClickListener{
            val name = mBinding.etInputNameNote.text.toString()
            val text = mBinding.etInputTextNote.text.toString()
            if (name.isEmpty()) {
                showToast(getString(R.string.toast_enter_name))
            } else {
                mViewModel.insert(AppNote(name = name, text = text)) {
                    APP_ACTIVITY.navController.navigate(R.id.action_addNewNoteFragment_to_mainFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}