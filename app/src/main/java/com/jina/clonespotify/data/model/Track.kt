package com.jina.clonespotify.data.model

data class Track(
    val id: String,
    val title: String,
    val artist: Artist,
    val album: Album,
    val previewUrl: String, // for streaming short clip
    val artworkUrl: String? // album cover
)

