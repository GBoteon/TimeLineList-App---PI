package com.example.timelinelist

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_lista.*

class ListaActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        setupViewPager(viewpager_filmes_series)
        tablayout_tabs.setupWithViewPager(viewpager_filmes_series)

        toolbar_button_right.setOnClickListener { startActivity(Intent(this, PerfilActivity::class.java)) }

    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(FilmesFragment(), "Filmes")
        adapter.addFragment(SeriesFragment(), "Séries")

        viewpager.setAdapter(adapter)
    }
}