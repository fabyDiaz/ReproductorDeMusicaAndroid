package com.example.appmusica

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusica.AppConstant.Companion.LOG_MAIN_ACTIVITY
import com.example.appmusica.AppConstant.Companion.MEDIA_PLAYER_POSITION
import com.example.appmusica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private var position: Int = 0
    private lateinit var currentSong: Song
    private var currentSongIndex: Int = 0
    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(LOG_MAIN_ACTIVITY, "onCreate()")
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentSong = AppConstant.songs[currentSongIndex]


        savedInstanceState?.let {
            position = it.getInt(MEDIA_PLAYER_POSITION)
        }


        updateUISongs()
        binding.playPauseButton.setOnClickListener { playOrPauseMusic() }

        binding.playNextButton.setOnClickListener { playNextSong() }
        binding.playPreviousButton.setOnClickListener{ playPreviousSong() }


    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_MAIN_ACTIVITY, "onStart()")
        mediaPlayer = MediaPlayer.create(this, R.raw.pp_remix)
        mediaPlayer?.seekTo(position)
        if (isPlaying)
            mediaPlayer?.start()

    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_MAIN_ACTIVITY, "onResume()")
        mediaPlayer?.seekTo(position)
        if (isPlaying) {
            mediaPlayer?.start()
            isPlaying = true
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_MAIN_ACTIVITY, "onPause()")
        if (mediaPlayer != null)
            position = mediaPlayer!!.currentPosition

        mediaPlayer?.pause()
        isPlaying = false

    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_MAIN_ACTIVITY, "onStop()")
        if (mediaPlayer != null)
            position = mediaPlayer!!.currentPosition
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_MAIN_ACTIVITY, "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_MAIN_ACTIVITY, "onDestroy()")
        //mediaPlayer?.release()
       // mediaPlayer = null
    }

    /**
     * *****************CICLO DE VIDA ****************************
     */

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MEDIA_PLAYER_POSITION, position)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore the current position from the saved state
        position = savedInstanceState.getInt(MEDIA_PLAYER_POSITION)
    }

    private fun updateUISongs() {
        binding.titleTextView.text = currentSong.title
        binding.albumCoverImageView.setImageResource(currentSong.imageRedId)
    }

    private fun playOrPauseMusic() {
        if (isPlaying) {
            mediaPlayer?.pause()
            binding.playPauseButton.setImageResource(R.drawable.baseline_play_circle_outline_24)
        } else {
            mediaPlayer?.start()
            binding.playPauseButton.setImageResource(R.drawable.baseline_pause_circle_outline_24)
        }

        isPlaying = !isPlaying
    }


    private fun playNextSong() {
        currentSongIndex = (currentSongIndex + 1) % AppConstant.songs.size
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        mediaPlayer?.start()
        isPlaying = true
        updateUISongs()
    }

    private fun playPreviousSong() {
        currentSongIndex = (currentSongIndex - 1 + AppConstant.songs.size) % AppConstant.songs.size
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        mediaPlayer?.start()
        isPlaying = true
        updateUISongs()
    }


}