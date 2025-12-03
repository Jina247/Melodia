package com.jina.clonespotify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackDTO(
    val id: String,
    val name: String,
    val artist_name: String,
    val album_name: String,
    val album_image: String,
    val audio: String
)

fun TrackDTO.toTrack(): Track {
    return Track(
        id = id,
        title = name,
        artist = artist_name,
        album = album_name,
        coverUrl = album_image,
        audioUrl = audio
    )
}






