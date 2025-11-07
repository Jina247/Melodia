package com.jina.clonespotify.data.model

data class Album(
    val id: String,
    val title: String,
    val artist: Artist,
    val artworkUrl: String? = null,
    val tracks: List<Track> = emptyList()
)

