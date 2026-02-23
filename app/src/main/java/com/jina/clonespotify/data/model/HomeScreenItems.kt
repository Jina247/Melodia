package com.jina.clonespotify.data.model

sealed interface HomeScreenItem {
    val id: String
}

// Section Header
data class SectionHeader(
    override val id: String,
    val title: String // e.g., "Popular This Week", "Top Albums"
) : HomeScreenItem

// Single Track Card
data class TrackItem(
    override val id: String,
    val title: String,
    val artistName: String,
    val artistId: String,
    val album: String,
    val coverUrl: String,
    val audioUrl: String
) : HomeScreenItem {
    companion object {
        fun fromTrack(track: Track) = TrackItem(
            id = track.id,
            title = track.title,
            artistName = track.artist?.name ?: "Unknown artist",
            artistId = track.artist?.id ?: "unknown",
            album = track.album,
            coverUrl = track.coverUrl,
            audioUrl = track.audioUrl
        )
    }
}

// Album Card
data class AlbumItem(
    override val id: String,
    val albumName: String,
    val artistName: String,
    val artistId: String,
    val coverUrl: String,
    val releaseDate: String?
) : HomeScreenItem {
    companion object {
        fun fromAlbum(album: Album) = AlbumItem(
            id = album.id,
            albumName = album.title,
            artistName = album.artist?.name ?: "Unknown Artist",
            artistId = album.artist?.id ?: "unknown",
            coverUrl = album.coverUrl,
            releaseDate = album.releaseDate
        )
    }
}

// Playlist Card
data class PlaylistItem(
    override val id: String,
    val playlistName: String,
    val userName: String,
    val creationDate: String,
    val trackCount: Int
) : HomeScreenItem {
    companion object {
        fun fromPlaylistDTO(playlist: PlaylistDTO) = PlaylistItem(
            id = playlist.id,
            playlistName = playlist.name,
            userName = playlist.userName,
            creationDate = playlist.creationDate,
            trackCount = playlist.tracks?.size ?: 0
        )
    }
}

// Horizontal Scrolling Carousel (for "Popular Albums", "New Releases", etc.)
data class AlbumCarousel(
    override val id: String,
    val sectionTitle: String,
    val albums: List<Album>
) : HomeScreenItem

data class PlaylistCarousel(
    override val id: String,
    val sectionTitle: String,
    val playlists: List<PlaylistDTO>
) : HomeScreenItem

data class TrackCarousel(
    override val id: String,
    val sectionTitle: String,
    val tracks: List<Track>
) : HomeScreenItem