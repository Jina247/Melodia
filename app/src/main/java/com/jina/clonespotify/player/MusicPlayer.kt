package com.jina.clonespotify.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MusicPlayer(context: Context) {
    private val player = ExoPlayer.Builder(context).build()
    private val _isPlaying = MutableStateFlow(false)
    var isPlaying: StateFlow<Boolean> = _isPlaying
    private var progressJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _position = MutableStateFlow(0L)
    val position: StateFlow<Long> = _position

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration

    fun play(url: String) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        player.play()
        _duration.value = player.duration.coerceAtLeast(0L)
        _isPlaying.value = true
        startProgressUpdates()
    }

    fun stop() {
        player.stop()
        player.clearMediaItems()
        _isPlaying.value = false
        _position.value = 0L
        stopProgressUpdates()
    }
    fun pause() {
        player.pause()
        _isPlaying.value = false
        stopProgressUpdates()
    }

    fun seekTo(ms: Long) {
        player.seekTo(ms)
        _position.value = ms
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (isActive && player.isPlaying) {
                _position.value = player.currentPosition
                delay(500)
            }
        }
    }

    private fun stopProgressUpdates() {
        progressJob?.cancel()
        progressJob = null
    }

    fun release() {
        scope.cancel()
        player.release()
    }

    private fun startPositionUpdates() {
        // pseudo loop
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                _position.value = player.currentPosition
                delay(500)
            }
        }
    }
}