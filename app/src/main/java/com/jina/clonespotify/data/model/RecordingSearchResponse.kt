package com.jina.clonespotify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecordingSearchResponse(
    val recordings: List<TrackDTO>
)

