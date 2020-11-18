package com.example.timelinelist.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.timelinelist.R
import com.example.timelinelist.adapters.ViewPagerAdapter
import com.example.timelinelist.fragments.FilmesFragment
import com.example.timelinelist.fragments.SeriesFragment
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