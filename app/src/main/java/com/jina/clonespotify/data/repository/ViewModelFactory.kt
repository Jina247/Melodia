package com.jina.clonespotify.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.jina.clonespotify.ui.screen.HomeViewModel

class ViewModelFactory(private val repository: TrackRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val savedStateHandle =  extras.createSavedStateHandle()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}