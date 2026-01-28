package com.jina.clonespotify.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JamendoPlaylistResponse(
    val results: List<PlaylistDTO>
)

@Serializable
data class PlaylistDTO(
    val id: String,
    val name: String,
    @SerialName("user_name") val userName: String,
    @SerialName("creationdate") val creationDate: String,
    val tracks: List<TrackDTO>? = null
)