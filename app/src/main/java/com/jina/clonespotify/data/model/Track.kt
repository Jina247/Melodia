package com.jina.clonespotify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val releaseTitle: String? = null,
    val releaseId: String? = null
)


