package com.jina.clonespotify.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jina.clonespotify.data.model.Track
import com.jina.clonespotify.data.repository.TrackRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TrackRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _searchQuery = savedStateHandle.getStateFlow("search_query", "")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<Track>>(emptyList())
    val searchResults: StateFlow<List<Track>> = _searchResults.asStateFlow()

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
                    _searchResults.value = tracks
                    if (tracks.isEmpty()) {
                        _errorMessage.value = "No result found"
                    }
                },
                onFailure = { error ->
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
                    _searchResults.value = tracks
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
}