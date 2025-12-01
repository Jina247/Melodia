package com.jina.clonespotify.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDTO(
    val id: String,
    val title: String,
    @SerialName("artist-credit") val artistCredit: List<ArtistCredit>,
    val releases: List<Release>? = null
)

fun TrackDTO.toTrack(): Track {
    val artistNames = artistCredit.joinToString(", ") { it.name }
    val firstRelease = releases?.firstOrNull()
    return Track(
        id = id,
        title = title,
        artist = artistNames,
        releaseTitle = firstRelease?.title,
        releaseId = firstRelease?.id
    )
}


