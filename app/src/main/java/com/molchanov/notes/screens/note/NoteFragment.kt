package com.molchanov.notes.screens.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.molchanov.notes.R
import com.molchanov.notes.databinding.FragmentNoteBinding
import com.molchanov.notes.model.AppNote
import com.molchanov.notes.utils.APP_ACTIVITY

class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel : NoteFragmentViewModel
    private lateinit var mCurrentNote : AppNote


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        mCurrentNote = arguments?.getSerializable("note") as AppNote
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        //добавляем возможность удаления заметки. Для этого оверрайдим два метода ниже
        setHasOptionsMenu(true)
        mBinding.tvNoteText.text = mCurrentNote.text
        mBinding.tvNoteName.text = mCurrentNote.name
        mViewModel = ViewModelProvider(this).get(NoteFragmentViewModel::class.java)

    }

    //Создаем само меню
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_action_menu, menu)
    }

    //Обрабатываем нажатие на кнопку меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete -> {
                mViewModel.delete(mCurrentNote) {
                    APP_ACTIVITY.navController.navigate(R.id.action_noteFragment_to_mainFragment)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}