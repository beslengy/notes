package com.molchanov.notes.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.molchanov.notes.R
import com.molchanov.notes.databinding.FragmentStartBinding
import com.molchanov.notes.utils.*


class StartFragment : Fragment() {

    private var _binding : FragmentStartBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: StartFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)
            return mBinding.root

    }

    override fun onStart() {
        super.onStart()

        mViewModel = ViewModelProvider(this).get(StartFragmentViewModel::class.java)

        if (AppPreference.getInitUser()) {
            mViewModel.initDatabase(AppPreference.getTypeDB()) {
                APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
            }
        } else {
            initialization()
        }

    }
    private fun initialization() {

        mBinding.btnRoom.setOnClickListener{
            mViewModel.initDatabase(TYPE_ROOM) {
                AppPreference.setInitUser(true)
                AppPreference.setTypeDB(TYPE_ROOM)
                APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
            }
        }
        //Подключаем слушатель к кнопке файербэйс
        mBinding.btnFirebase.setOnClickListener{
            //При нажатии переводим поля ввода почты, пароля и кнопку логин в режим видимости
               mBinding.let {
                   it.etInputEmail.visibility = View.VISIBLE
                   it.etInputPassword.visibility = View.VISIBLE
                   it.btnLogin.apply{
                       visibility = View.VISIBLE
                       //подключаем слушатель на кнопку логин
                       setOnClickListener {
                           //создаем переменные с текстом из ввода почты и пароля и проверяем
                           //не пустые ли они. Если пустые - выводим тост
                           val inputEmail = mBinding.etInputEmail.text.toString()
                           val inputPassword = mBinding.etInputPassword.text.toString()
                           if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                               EMAIL = inputEmail
                               PASSWORD = inputPassword
                               mViewModel.initDatabase(TYPE_FIREBASE) {
                                   AppPreference.setInitUser(true)
                                   AppPreference.setTypeDB(TYPE_FIREBASE)
                                   APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
                               }
                           } else {
                               showToast(context.getString(R.string.toast_wrong_login_data))
                           }
                       }
                   }

               }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}