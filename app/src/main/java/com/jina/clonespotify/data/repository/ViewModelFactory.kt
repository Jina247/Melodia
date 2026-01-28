package com.jina.clonespotify.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.jina.clonespotify.player.MusicPlayer
import com.jina.clonespotify.ui.screen.home.HomeViewModel
import com.jina.clonespotify.ui.screen.songinfo.SongInfoViewModel

class ViewModelFactory(
    private val repository: TrackRepository,
    private val musicPlayer: MusicPlayer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        val savedStateHandle = extras.createSavedStateHandle()

        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository, savedStateHandle) as T
            }
            modelClass.isAssignableFrom(SongInfoViewModel::class.java) -> {
                SongInfoViewModel(musicPlayer) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
