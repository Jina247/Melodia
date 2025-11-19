package com.jina.clonespotify.data.repository

import com.jina.clonespotify.data.model.Track
import com.jina.clonespotify.data.remote.MusicApi

class TrackRepository(
    private val api: MusicApi
){
    suspend fun searchTracks(query: String): Result<List<Track>> {
        return try {
            val respond = api.searchTracks(query)
            val track = respond.docs.mapNotNull { dto ->
                if (dto.id != null) {
                    Track(
                        id = dto.id,
                        title = dto.title.toString(),
                        artist = dto.artist,
                        album = dto.album,
                        previewUrl = dto.preview.toString(),
                        md5Image = dto.md5Image.toString()
                    )
                } else null
            }
            Result.success(track)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}