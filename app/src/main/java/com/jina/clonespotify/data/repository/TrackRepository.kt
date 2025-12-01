package com.jina.clonespotify.data.repository

import com.jina.clonespotify.data.model.Track
import com.jina.clonespotify.data.model.toTrack
import com.jina.clonespotify.data.remote.MusicApi

class TrackRepository(
    private val api: MusicApi
) {
    suspend fun searchTracks(query: String): Result<List<Track>> {
        return try {
            val respond = api.searchTracks(query)
            val mappedTrack = respond.recordings.map { it.toTrack() }
            Result.success(mappedTrack)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}