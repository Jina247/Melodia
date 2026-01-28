package com.jina.clonespotify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JamendoTrackResponse(
    val results: List<TrackDTO >
)

