package com.example.timelinelist.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.viewmodels.FilmesFragmentViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.timelinelist.activities.DetalheFilmeActivity
import com.example.timelinelist.activities.PesquisaActivity
import com.example.timelinelist.adapters.ListaFilmesAdapter
import kotlinx.android.synthetic.main.fragment_filmes.view.*

class FilmesFragment : Fragment(), ListaFilmesAdapter.OnFilmeClickListener {

    private val viewModel: FilmesFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_filmes, container, false)

        viewModel.getFilmes()


        viewModel.listaFilme.observe(viewLifecycleOwner) {
            var adapter =  ListaFilmesAdapter(it, this)
            view.recyclerview_filmes.adapter = adapter
            view.recyclerview_filmes.layoutManager = LinearLayoutManager(context)
            view.recyclerview_filmes.setHasFixedSize(true)
            view.searchview_pesquisa_filmes.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
        }

        return view

    }
    override fun filmeClick(position: Int) {
        if(!context?.let { isOnline(it) }!!) {
            Toast.makeText(context, "Sem conexão com internet", Toast.LENGTH_LONG).show()
        } else {
            var idClick = viewModel.listaFilme.value?.get(position)?.id as Int
            viewModel.getFilmesFromId(idClick)
            viewModel.filmeDetalhe.observe(viewLifecycleOwner) {
                val intent = Intent(context, DetalheFilmeActivity::class.java)
                intent.putExtra("filmeClick", it)
                startActivity(intent)
            }
        }
    }
    private fun isOnline(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }
}