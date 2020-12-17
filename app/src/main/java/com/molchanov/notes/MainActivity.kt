package com.molchanov.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.molchanov.notes.databinding.ActivityMainBinding
import com.molchanov.notes.utils.APP_ACTIVITY
import com.molchanov.notes.utils.AppPreference

class MainActivity : AppCompatActivity() {

    // создаем тулбар
    lateinit var mToolbar : Toolbar
    //создаем контроллер
    lateinit var navController : NavController
    //подключаем связку
    //перед подключением прописываем в build.gradle в defaultConfig viewBinding.enabled = true
    private var _binding : ActivityMainBinding? = null
    //переменная со ссылкой на связку, которой уже не требуется проверка на null
        private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //инициализация этой константы дает возможность получать контекст в любом месте в приложении
        APP_ACTIVITY = this
        mToolbar= mBinding.toolbar
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setSupportActionBar(mToolbar)
        title = getString(R.string.title)
        AppPreference.getPreference(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        //присваиваем связке null, чтобы исключить утечку памяти
        _binding = null
    }
}