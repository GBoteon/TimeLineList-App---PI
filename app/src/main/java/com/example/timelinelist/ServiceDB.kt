package com.example.timelinelist

import com.example.timelinelist.helpers.BaseFilmeDetalhe
import com.example.timelinelist.helpers.BaseSerieDetalhe
import com.example.timelinelist.helpers.EssencialFilme
import com.example.timelinelist.helpers.EssencialSerie

interface RepositoryFilmes{

    //selects
    suspend fun getAllFilmesTask(): List<EssencialFilme>

    //inserts
    suspend fun addFilmeTask(filme: EssencialFilme)

    //updates
    suspend fun updateFilmeTask(filme: EssencialFilme)

    //deletes
    suspend fun deleteFilmeTask(filme: EssencialFilme)

}
interface RepositorySeries{

    //selects
    suspend fun getAllSeriesTask(): List<EssencialSerie>

    //inserts
    suspend fun addSerieTask(serie: EssencialSerie)

    //updates
    suspend fun updateSerieTask(serie: EssencialSerie)

    //deletes
    suspend fun deleteSerieTask(serie: EssencialSerie)


}

class RepositoryImplementationFilmes(val serieDAO: AccessFilmes): RepositoryFilmes{
    override suspend fun getAllFilmesTask(): List<EssencialFilme> {
        return serieDAO.getAllFilmes()
    }

    override suspend fun addFilmeTask(filme: EssencialFilme) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFilmeTask(filme: EssencialFilme) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFilmeTask(filme: EssencialFilme) {
        TODO("Not yet implemented")
    }

}
class RepositoryImplementationSeries(val serieDAO: AccessSeries): RepositorySeries{
    override suspend fun getAllSeriesTask(): List<EssencialSerie> {
        return serieDAO.getAllSeries()
    }

    override suspend fun addSerieTask(serie: EssencialSerie) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSerieTask(serie: EssencialSerie) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSerieTask(serie: EssencialSerie) {
        TODO("Not yet implemented")
    }

}