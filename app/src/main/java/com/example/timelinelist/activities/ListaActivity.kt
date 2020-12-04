package com.example.timelinelist.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager.widget.ViewPager
import com.example.timelinelist.R
import com.example.timelinelist.adapters.ViewPagerAdapter
import com.example.timelinelist.fragments.FilmesFragment
import com.example.timelinelist.fragments.SeriesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_editperfil.*
import kotlinx.android.synthetic.main.activity_lista.*
import kotlinx.android.synthetic.main.activity_pesquisa.*
import kotlinx.android.synthetic.main.filme_item.*


class ListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        setupViewPager(viewpager_filmes_series)
        tablayout_tabs.setupWithViewPager(viewpager_filmes_series)
        tablayout_tabs.getTabAt(0)?.setIcon(R.drawable.ic_movie)
        tablayout_tabs.getTabAt(1)?.setIcon(R.drawable.ic_serie)
        val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                tablayout_tabs.getTabAt(0)?.icon?.setTint(getResources().getColor(R.color.colorRed))
                tablayout_tabs.getTabAt(1)?.icon?.setTint(getResources().getColor(R.color.colorWhite))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                tablayout_tabs.getTabAt(0)?.icon?.setTint(getResources().getColor(R.color.colorRed))
                tablayout_tabs.getTabAt(1)?.icon?.setTint(getResources().getColor(R.color.colorBlack))
            }
        }
        tablayout_tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    tab.icon?.setTint(getResources().getColor(R.color.colorRed))
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    var sharedPref = getSharedPreferences("save", Context.MODE_PRIVATE)
                    var theme = sharedPref.getBoolean("value",false)
                    if (theme) {
                        tab.icon?.setTint(getResources().getColor(R.color.colorWhite))
                    } else {
                        tab.icon?.setTint(getResources().getColor(R.color.colorBlack))
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