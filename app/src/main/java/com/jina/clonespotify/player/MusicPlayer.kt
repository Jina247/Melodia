package com.jina.clonespotify.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MusicPlayer(context: Context) {
    private val player = ExoPlayer.Builder(context).build()

    fun play(url: String) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        player.play()
    }

    fun stop() = player.stop()
    fun pause() = player.pause()
}