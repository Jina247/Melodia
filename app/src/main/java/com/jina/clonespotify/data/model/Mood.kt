package com.jina.clonespotify.data.model

data class Mood(
    val name: String,  // e.g., "Chill", "Focus", "Happy"
    val queryKeyword: String // used to call API
)
