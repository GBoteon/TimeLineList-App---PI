package com.example.timelinelist.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.timelinelist.R
import com.example.timelinelist.adapters.ViewPagerAdapter
import com.example.timelinelist.fragments.FilmesFragment
import com.example.timelinelist.fragments.SeriesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_lista.*


@Suppress("DEPRECATION")
class ListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        setupViewPager(viewpager_filmes_series)
        tablayout_tabs.setupWithViewPager(viewpager_filmes_series)
        tablayout_tabs.getTabAt(0)?.setIcon(R.drawable.ic_movie)
        tablayout_tabs.getTabAt(1)?.setIcon(R.drawable.ic_serie)
        val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                tablayout_tabs.getTabAt(0)?.icon?.setTint(resources.getColor(R.color.colorRed))
                tablayout_tabs.getTabAt(1)?.icon?.setTint(resources.getColor(R.color.colorWhite))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                tablayout_tabs.getTabAt(0)?.icon?.setTint(resources.getColor(R.color.colorRed))
                tablayout_tabs.getTabAt(1)?.icon?.setTint(resources.getColor(R.color.colorBlack))
            }
        }
        tablayout_tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon?.setTint(resources.getColor(R.color.colorRed))
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (mode) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        tab?.icon?.setTint(resources.getColor(R.color.colorWhite))
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        tab?.icon?.setTint(resources.getColor(R.color.colorBlack))
                    }
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        toolbar_button_right.setOnClickListener { startActivity(
            Intent(
                this,
                PerfilActivity::class.java
            )
        ) }
        floatingactionbutton.setOnClickListener { startActivity(
            Intent(
                this,
                PesquisaActivity::class.java
            )
        ) }

    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(FilmesFragment(), "Filmes")
        adapter.addFragment(SeriesFragment(), "Séries")
        viewpager.setAdapter(adapter)
    }

}