package com.jina.clonespotify.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jina.clonespotify.data.model.AlbumItem
import com.jina.clonespotify.data.model.HomeScreenItem
import com.jina.clonespotify.data.model.TrackItem
import com.jina.clonespotify.data.repository.TrackRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.forEach
class HomeViewModel(
    private val repository: TrackRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _searchQuery = savedStateHandle.getStateFlow("search_query", "")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _tracks = MutableStateFlow<List<TrackItem>>(emptyList())
    val tracks: StateFlow<List<TrackItem>> = _tracks.asStateFlow()

    private val _albums = MutableStateFlow<List<AlbumItem>>(emptyList())
    val albums: StateFlow<List<AlbumItem>> = _albums.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun updateQuery(query: String) {
        savedStateHandle["search_query"] = query
    }

    fun searchTracks() {
        if (_searchQuery.value.isBlank()) return
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.searchTracks(_searchQuery.value).fold(
                onSuccess = { tracks ->
                    _tracks.value = tracks.map { track -> TrackItem.fromTrack(track) }
                    if (tracks.isEmpty()) {
                        _errorMessage.value = "No result found"
                    }
                },
                onFailure = { _ ->
                    _errorMessage.value = "Search failed"
                }
            )
            _isLoading.value = false
        }
    }

    fun loadPopularTracks() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            // Search for popular music
            repository.getPopularTracks().fold(
                onSuccess = { tracks ->
                    _tracks.value = tracks.map { track -> TrackItem.fromTrack(track) }
                    if (tracks.isEmpty()) {
                        _errorMessage.value = "No tracks found"
                    }
                },
                onFailure = { error ->
                    _errorMessage.value = "Failed to load tracks: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun loadPopularAlbums() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

        repository.loadPopularAlbums().fold(
            onSuccess = { albums ->
                _albums.value = albums.map {album -> AlbumItem.fromAlbum(album) }
                if (albums.isEmpty()) {
                    _errorMessage.value = "No albums found"
                }
            },
            onFailure = { error ->
                _errorMessage.value = "Failed to load albums: ${error.message}"
            }
        )
        _isLoading.value = false
        }
    }
}