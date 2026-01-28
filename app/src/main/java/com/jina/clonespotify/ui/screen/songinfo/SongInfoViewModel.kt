package com.jina.clonespotify.ui.screen.songinfo

import android.view.View
import androidx.lifecycle.ViewModel
import com.jina.clonespotify.player.MusicPlayer
import kotlinx.coroutines.flow.StateFlow

class SongInfoViewModel(
    private val player: MusicPlayer
): ViewModel() {
    var isPlaying: StateFlow<Boolean> = player.isPlaying
    val duration = player.duration
    val position = player.position

    fun playSong(url: String) {
        player.play(url)
    }

    fun pauseSong() = player.pause()
    fun stopSong() = player.stop()

    fun onPlayPauseClick(url: String) {
        if (isPlaying.value) {
            pauseSong()
        } else {
            playSong(url)
        }
    }

    fun onSeek(newPosition: Long) {
        player.seekTo(newPosition)
    }

    // TODO: remember the position of a playing song, fix time bar, add small bar in homescreen

}