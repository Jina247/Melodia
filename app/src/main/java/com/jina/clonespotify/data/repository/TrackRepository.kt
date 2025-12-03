package com.jina.clonespotify.data.repository

import com.jina.clonespotify.data.model.Track
import com.jina.clonespotify.data.model.toTrack
import com.jina.clonespotify.data.remote.MusicApi

class TrackRepository(
    private val api: MusicApi,
    private val clientId: String
) {

    suspend fun searchByMood(moodTag: String): Result<List<Track>> {
        return try {
            val response = api.searchTracks(
                clientId = clientId,
                tags = moodTag
            )

            val tracks = response.results.map { it.toTrack() }
            Result.success(tracks)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
