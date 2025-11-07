package com.jina.clonespotify.data.model

data class Playlist(
    val id: String,
    val title: String,
    val description: String? = null,
    val tracks: List<Track> = emptyList(),
    val artworkUrl: String? = null
)

