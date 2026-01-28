package com.jina.clonespotify.data.repository

import com.jina.clonespotify.data.model.PlaylistDTO
import com.jina.clonespotify.data.model.Track
import com.jina.clonespotify.data.model.toTrack
import com.jina.clonespotify.data.remote.MusicApi

class TrackRepository(
    private val api: MusicApi,
    private val clientId: String
) {
    private var cachedTracks = mutableMapOf<String, Track>()

    /**
     * Search tracks by query (searches in track names, artist names, tags)
     */
    suspend fun searchTracks(query: String): Result<List<Track>> {
        return try {
            val response = api.searchTracks(
                clientId = clientId,
                search = query
            )
            val tracks = response.results.map { it.toTrack() }

            // Cache tracks
            tracks.forEach { cachedTracks[it.id] = it }

            Result.success(tracks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Search tracks by mood/tags (e.g., "happy", "chill", "rock")
     */
    suspend fun searchByMood(moodTag: String): Result<List<Track>> {
        return try {
            val response = api.searchTracks(
                clientId = clientId,
                tags = moodTag
            )
            val tracks = response.results.map { it.toTrack() }

            // Cache tracks
            tracks.forEach { cachedTracks[it.id] = it }

            Result.success(tracks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get popular/trending tracks
     */
    suspend fun getPopularTracks(): Result<List<Track>> {
        return try {
            val response = api.getPopularTracks(clientId = clientId)
            val tracks = response.results.map { it.toTrack() }

            // Cache tracks
            tracks.forEach { cachedTracks[it.id] = it }

            Result.success(tracks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get a specific track by ID
     */
    suspend fun fetchTrackById(trackId: String): Result<Track> {
        return try {
            val response = api.getTrackById(
                clientId = clientId,
                trackId = trackId
            )

            if (response.results.isNotEmpty()) {
                val track = response.results.first().toTrack()
                cachedTracks[track.id] = track
                Result.success(track)
            } else {
                Result.failure(Exception("Track not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get track from cache or fetch if not cached
     */
    fun getTrackById(trackId: String): Track? {
        return cachedTracks[trackId]
    }

    /**
     * Get playlists by name
     */
    suspend fun getPlaylists(name: String? = null): Result<List<PlaylistDTO>> {
        return try {
            val response = api.getPlaylists(
                clientId = clientId,
                name = name
            )
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get tracks from a specific playlist
     */
    suspend fun getPlaylistTracks(playlistId: String): Result<List<Track>> {
        return try {
            val response = api.getPlaylistTracks(
                clientId = clientId,
                playlistId = playlistId
            )

            if (response.results.isNotEmpty()) {
                val tracks = response.results.first().tracks.map { it.toTrack() }

                // Cache tracks
                tracks.forEach { cachedTracks[it.id] = it }

                Result.success(tracks)
            } else {
                Result.failure(Exception("Playlist not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}