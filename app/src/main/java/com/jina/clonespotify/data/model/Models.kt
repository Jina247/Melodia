package com.jina.clonespotify.data.model

import com.jina.clonespotify.utils.coverUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Album Response
@Serializable
data class JamendoAlbumResponse(
    val results: List<AlbumDTO>
)

data class Album(
    val id: String,
    val title: String,
    val artist: Artist?,
    val coverUrl: String,
    val releaseDate: String?
)

@Serializable
data class AlbumDTO(
    val id: String,
    val name: String,
    @SerialName("artist_name") val artistName: String? = null,
    @SerialName("artist_id") val artistId: String? = null,
    val image: String? = null,
    @SerialName("releasedate") val releaseDate: String? = null,
    val tracks: List<TrackDTO>? = null
)

fun AlbumDTO.toDomain() : Album {
    return Album(
        id = id,
        title = name,
        artist = Artist(
            id = artistId ?: "unknown",
            name = artistName ?: "Unknown Artist"
        ),
        coverUrl = image ?: "",
        releaseDate = releaseDate ?: "N/A"
    )
}

@Serializable
data class Artist(
    val id: String,
    val name: String
)

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

@Serializable
data class JamendoTrackResponse(
    val results: List<TrackDTO >
)

data class Mood(
    val name: String,  // e.g., "Chill", "Focus", "Happy"
    val queryKeyword: String // used to call API
)

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

data class Track(
    val id: String,
    val title: String,
    val artist: Artist?,
    val album: String,
    val coverUrl: String,
    val audioUrl: String
)

@Serializable
data class TrackDTO(
    val id: String,
    val name: String,
    @SerialName("artist_name") val artistName: String? = null,
    @SerialName("artist_id") val artistId: String? = null,
    @SerialName("album_name") val albumName: String? = null,
    @SerialName("album_id") val albumId: String? = null,
    @SerialName("album_image") val albumImage: String? = null,
    val image: String? = null,
    val audio: String,
    val audiodownload: String? = null,
    val duration: Int = 0,
    val releasedate: String? = null
)

fun TrackDTO.toTrack(): Track {
    return Track(
        id = id,
        title = name,
        artist = Artist(
            id = artistId ?: "unknown",
            name = artistName ?: "Unknown Artist"
        ),
        album = albumName ?: "Unknown Album",
        coverUrl = albumImage?.takeIf { it.isNotEmpty() } ?: image ?: "",
        audioUrl = audio
    )
}
