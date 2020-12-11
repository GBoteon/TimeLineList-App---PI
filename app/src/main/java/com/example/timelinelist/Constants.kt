package com.example.timelinelist

object Constants {
    const val API_KEY = "be06be958cb361e052a8f904860bd8bf"
    const val API_URL = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500/"
    const val LANG = "pt-BR"
    const val APP_KEY = "APP"
    const val UIID_KEY = "UIID"
    const val EMPTY_STRING = ""
    const val PREFS_NAME = "theme_prefs"
    const val KEY_THEME = "prefs.theme"
    const val THEME_UNDEFINED = -1
    const val THEME_LIGHT = 0
    const val THEME_DARK = 1
    val GENEROS_FILME = mapOf(28 to "Ação", 12 to "Aventura", 16 to "Animação", 35 to "Comédia", 80 to "Crime", 99 to "Documentário", 18 to "Drama", 10751 to "Família", 14 to "Fantasia", 36 to "História", 27 to "Terror", 10402 to "Música", 9648 to "Mistério", 10749 to "Romance", 878 to "Ficção científica", 10770 to "Cinema TV", 53 to "Thriller", 10752 to "Guerra", 37 to "Faroeste")
    val GENEROS_SERIE = mapOf(10759 to "Action & Adventure", 16 to "Animação", 35 to "Comédia", 80 to "Crime", 99 to "Documentário", 18 to "Drama", 10751 to "Família", 10762 to "Kids", 9648 to "Mistério", 10763 to "News", 10764 to "Reality", 10765 to "Sci-Fi & Fantasy", 10766 to "Soap", 10767 to "Talk", 10768 to "War & Politics", 37 to "Faroeste")
    val STATUS_FILME = mapOf("Rumored" to "Rumor", "Planned" to "Planejado", "In Production" to "Em Produção", "Post Production" to "Pós-Produção", "Released" to "Lançado", "Canceled" to "Cancelado")
    val STATUS_SERIE = mapOf("Returning Series" to "Lançando", "Planned" to "Planejado", "In Production" to "Em Produção", "Ended" to "Finalizada", "Canceled" to "Cancelada", "Pilot" to "Piloto")
}
