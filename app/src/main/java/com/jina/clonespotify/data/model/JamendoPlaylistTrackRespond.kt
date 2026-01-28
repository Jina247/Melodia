package com.jina.clonespotify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JamendoPlaylistTracksResponse(
    val results: List<PlaylistWithTracks>
)

@Serializable
data class PlaylistWithTracks(
    val id: String,
    val name: String,
    val tracks: List<TrackDTO>
)