package com.example.timelinelist.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.timelinelist.R
import com.example.timelinelist.adapters.ListaFilmePesquisaAdapter
import com.example.timelinelist.adapters.ListaObrasAdapter
import com.example.timelinelist.adapters.ListaSeriePesquisaAdapter
import com.example.timelinelist.adapters.ViewPagerAdapter
import com.example.timelinelist.fragments.FilmesFragment
import com.example.timelinelist.fragments.PesquisaFilmesFragment
import com.example.timelinelist.fragments.PesquisaSeriesFragment
import com.example.timelinelist.fragments.SeriesFragment
import com.example.timelinelist.helpers.BaseFilme
import com.example.timelinelist.models.PesquisaViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_estatisticas.*
import kotlinx.android.synthetic.main.activity_lista.*
import kotlinx.android.synthetic.main.activity_pesquisa.*
import kotlinx.android.synthetic.main.fragment_filmes.view.*
import kotlinx.android.synthetic.main.fragment_pesquisafilmes.*
import kotlinx.android.synthetic.main.fragment_pesquisafilmes.view.*
import kotlinx.android.synthetic.main.fragment_pesquisaseries.*
import javax.xml.datatype.DatatypeFactory.newInstance
import javax.xml.parsers.DocumentBuilderFactory.newInstance


class PesquisaActivity : AppCompatActivity() {
    private val viewModel: PesquisaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)

        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PesquisaFilmesFragment(), "Filmes")
        adapter.addFragment(PesquisaSeriesFragment(), "Séries")
        viewpager_filmes_series_pesquisa.setAdapter(adapter)

        tablayout_tabs_pesquisa.setupWithViewPager(viewpager_filmes_series_pesquisa)
        tablayout_tabs_pesquisa.getTabAt(0)?.setIcon(R.drawable.ic_movie)
        tablayout_tabs_pesquisa.getTabAt(1)?.setIcon(R.drawable.ic_serie)
        val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                tablayout_tabs_pesquisa.getTabAt(0)?.icon?.setTint(getResources().getColor(R.color.yellow))
                tablayout_tabs_pesquisa.getTabAt(1)?.icon?.setTint(getResources().getColor(R.color.colorWhite))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                tablayout_tabs_pesquisa.getTabAt(0)?.icon?.setTint(getResources().getColor(R.color.yellow))
                tablayout_tabs_pesquisa.getTabAt(1)?.icon?.setTint(getResources().getColor(R.color.colorBlack))
            }
        }
        tablayout_tabs_pesquisa.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon?.setTint(getResources().getColor(R.color.yellow))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
                when (mode) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        tab.icon?.setTint(getResources().getColor(R.color.colorWhite))
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        tab.icon?.setTint(getResources().getColor(R.color.colorBlack))
                    }
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        imageview_voltar_pesquisatolista.setOnClickListener{ startActivity(Intent(this, ListaActivity::class.java)) }

        edittext_busca.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideSoftKeyboard(this)

                val fragmentPesquisaFilmes = adapter.getItem(0) as PesquisaFilmesFragment
                val fragmentPesquisaSeries = adapter.getItem(1) as PesquisaSeriesFragment
                fragmentPesquisaFilmes.atualizaListaFilmes(edittext_busca.text.toString())
                fragmentPesquisaSeries.atualizaListaSeries(edittext_busca.text.toString())

                return@OnEditorActionListener true
            }
            false
        })
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

}