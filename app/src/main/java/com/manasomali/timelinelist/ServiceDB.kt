package com.manasomali.timelinelist

import com.manasomali.timelinelist.helpers.EssencialFilme
import com.manasomali.timelinelist.helpers.EssencialSerie

interface RepositoryFilmes{

    //selects
    suspend fun getAllFilmesTask(): List<EssencialFilme>

    //inserts
    suspend fun addFilmeTask(filme: EssencialFilme)

    //updates
    suspend fun updateFilmeTask(filme: EssencialFilme)

    //deletes
    suspend fun deleteFilmeTask(id: Int)

}
interface RepositorySeries{

    //selects
    suspend fun getAllSeriesTask(): List<EssencialSerie>

    //inserts
    suspend fun addSerieTask(serie: EssencialSerie)

    //updates
    suspend fun updateSerieTask(serie: EssencialSerie)

    //deletes
    suspend fun deleteSerieTask(id: Int)


}

class RepositoryImplementationFilmes(val serieDAO: AccessFilmes): RepositoryFilmes{
    override suspend fun getAllFilmesTask(): List<EssencialFilme> {
        return serieDAO.getAllFilmes()
    }

    override suspend fun addFilmeTask(filme: EssencialFilme) {
        return serieDAO.addFilme(filme)
    }

    override suspend fun updateFilmeTask(filme: EssencialFilme) {
        return serieDAO.updateFilme(filme)
    }

    override suspend fun deleteFilmeTask(id: Int) {
        return serieDAO.deleteFilme(id)
    }

}
class RepositoryImplementationSeries(val serieDAO: AccessSeries): RepositorySeries{
    override suspend fun getAllSeriesTask(): List<EssencialSerie> {
        return serieDAO.getAllSeries()
    }

    override suspend fun addSerieTask(serie: EssencialSerie) {
        return serieDAO.addSeries(serie)
    }

    override suspend fun updateSerieTask(serie: EssencialSerie) {
        return serieDAO.updateSeries(serie)
    }

    override suspend fun deleteSerieTask(id: Int) {
        return serieDAO.deleteSeries(id)
    }

}