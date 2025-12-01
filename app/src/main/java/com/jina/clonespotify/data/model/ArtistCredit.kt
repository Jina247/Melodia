package com.jina.clonespotify.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistCredit(
    val name: String,
    @SerialName("joinphrase")
    val joinPhrase: String? = null,
    val artist: Artist
)
