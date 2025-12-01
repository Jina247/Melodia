package com.jina.clonespotify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Release(
    val id: String,
    val title: String
)


