package com.example.appmusica

data class Song(
    val title: String,
    val audioResId: Int,
    val imageRedId: Int
){}

class AppConstant {

    companion object {

        const val LOG_MAIN_ACTIVITY = "MainActivityReproductor"
        const val MEDIA_PLAYER_POSITION = "mpPosition"
        const val CURRENT_SONG_INDEX = "indiceCancion"

        val songs = listOf(
            Song("Pretty please Remix - Dua Lipa", R.raw.pp_remix, R.drawable.pretty_please),
            Song("Summertime Sadness Remix - Lana del Rey", R.raw.lr_ss, R.drawable.lr_ss),
            Song("In the end Remix - Likin Park", R.raw.lp_in_the_end_remix, R.drawable.lp_in_the_emd_remix)
        )
    }
}