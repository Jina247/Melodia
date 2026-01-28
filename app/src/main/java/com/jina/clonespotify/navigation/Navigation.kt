package com.jina.clonespotify.navigation

import androidx.navigation.NavController

fun NavController.navigateToTrack(trackId: String) {
    this.navigate("song_info/$trackId")
}