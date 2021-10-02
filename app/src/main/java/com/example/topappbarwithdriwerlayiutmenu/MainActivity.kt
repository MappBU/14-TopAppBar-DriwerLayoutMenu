package com.example.topappbarwithdriwerlayiutmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.topappbarwithdriwerlayiutmenu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    // Эта хуйня нужна чтобы связать Драйвер с АктионБаром
    private var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Этот метод назначает Toolbar выполнять функции ActonBar.
        // ActonBar - управляющий элемент системы, например туда выводятся меню, заголовки, кнопки управления навигацией и тп.
        setSupportActionBar(binding?.topAppBar)

        // Связывает DriwerLayout с данным активити.
        toggle = ActionBarDrawerToggle(this, binding?.drawerLayout, R.string.open, R.string.close)

        // Метож определяет нажатия на иконки(кнопки) TopAppBar
        binding?.topAppBar?.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Говорим: Когда itemId - то...
            when (menuItem.itemId) {
                R.id.favoritesTopAppBar -> {
                    // Подгружает в нашу разметку XML, во FrameLayout - Фрагмент: Favorites
                    supportFragmentManager.beginTransaction().replace(R.id.content, Favorites())
                        .commit()
                    true
                }
                R.id.settingsTopAppBar -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, Settings())
                        .commit()
                    true
                }
                R.id.uploadTopAppBar -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, Upload())
                        .commit()
                    true
                }
                // Если нет действия по элементу TopAppBar, то во when передается false и условие не срабатывает просто.
                else -> false
            }
        }
        // Прослушиватель табов в DriwerLayout
        binding?.navigationView?.setNavigationItemSelectedListener { menuItem ->
            // Когда нажат айтем - то...
            when (menuItem.itemId) {
                R.id.homeMenu -> Toast.makeText(this@MainActivity, "Item 1", Toast.LENGTH_SHORT).show()
                R.id.shopMenu -> Toast.makeText(this@MainActivity, "Item 2", Toast.LENGTH_SHORT).show()
            }
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
            true
        }
    }

    // Когда меню на TopAppBare открывается впервые, сразу вызывается метод onCreateOptionsMenu()
    // Вызывается системой только один раз при создании меню.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Подгружает наше созданное меню top_app_bar.xml на сам TopAppBar
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    // Этот метод назначает действие на кнопку home(Гамбургер) на TopAppBare (Она почему то только здесь работает)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (toggle?.onOptionsItemSelected(item) == true) {return true}
                return super.onOptionsItemSelected(item)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}