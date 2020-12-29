package com.example.timelinelist

import com.example.timelinelist.helpers.BaseFilmeDetalhe
import com.example.timelinelist.helpers.BaseSerieDetalhe

interface RepositoryFilmes{

    //selects
    suspend fun getAllFilmesTask(): List<BaseFilmeDetalhe>

    //inserts
    suspend fun addFilmeTask(filme: BaseFilmeDetalhe)

    //updates
    suspend fun updateFilmeTask(filme: BaseFilmeDetalhe)

    //deletes
    suspend fun deleteFilmeTask(filme: BaseFilmeDetalhe)

    //select
    suspend fun selectFilmeTask(filme: BaseFilmeDetalhe)

}
interface RepositorySeries{

    //selects
    suspend fun getAllSeriesTask(): List<BaseSerieDetalhe>

    //inserts
    suspend fun addSerieTask(serie: BaseSerieDetalhe)

    //updates
    suspend fun updateSerieTask(serie: BaseSerieDetalhe)

    //deletes
    suspend fun deleteSerieTask(serie: BaseSerieDetalhe)

    //select
    suspend fun selectSerieTask(serie: BaseSerieDetalhe)

}

class RepositoryImplementationFilmes(val serieDAO: AccessFilmes): RepositoryFilmes{
    override suspend fun getAllFilmesTask(): List<BaseFilmeDetalhe> {
        TODO("Not yet implemented")
    }

    override suspend fun addFilmeTask(filme: BaseFilmeDetalhe) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFilmeTask(filme: BaseFilmeDetalhe) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFilmeTask(filme: BaseFilmeDetalhe) {
        TODO("Not yet implemented")
    }

    override suspend fun selectFilmeTask(filme: BaseFilmeDetalhe) {
        TODO("Not yet implemented")
    }

}
class RepositoryImplementationSeries(val serieDAO: AccessSeries): RepositorySeries{
    override suspend fun getAllSeriesTask(): List<BaseSerieDetalhe> {
        TODO("Not yet implemented")
    }

    override suspend fun addSerieTask(serie: BaseSerieDetalhe) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSerieTask(serie: BaseSerieDetalhe) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSerieTask(serie: BaseSerieDetalhe) {
        TODO("Not yet implemented")
    }

    override suspend fun selectSerieTask(serie: BaseSerieDetalhe) {
        TODO("Not yet implemented")
    }

}