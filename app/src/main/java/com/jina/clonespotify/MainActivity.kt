package com.jina.clonespotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jina.clonespotify.data.remote.JamendoRetrofitInstance
import com.jina.clonespotify.data.repository.TrackRepository
import com.jina.clonespotify.data.repository.ViewModelFactory
import com.jina.clonespotify.player.MusicPlayer
import com.jina.clonespotify.ui.screen.home.HomeScreen
import com.jina.clonespotify.ui.screen.home.HomeViewModel
import com.jina.clonespotify.ui.screen.songinfo.SongInfoScreen
import com.jina.clonespotify.ui.screen.songinfo.SongInfoViewModel
import com.jina.clonespotify.ui.theme.CloneSpotifyTheme

class MainActivity : ComponentActivity() {
    private lateinit var musicPlayer: MusicPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val clientId = "260d205c"

        musicPlayer = MusicPlayer(this)
        val repository = TrackRepository(
            api = JamendoRetrofitInstance.api,
            clientId = clientId
        )

        setContent {
            CloneSpotifyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF121212)
                ) {
                    Column {
                        MusicApp(repository, musicPlayer)
                        SpotifyBottomNavigationBar()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
    }
}

@Composable
fun SpotifyBottomNavigationBar() {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = Color(0xFF121212),
        contentColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFF535353),
                unselectedTextColor = Color(0xFF535353)
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFF535353),
                unselectedTextColor = Color(0xFF535353)
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.LibraryMusic, contentDescription = "Library") },
            label = { Text("Your Library") },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFF535353),
                unselectedTextColor = Color(0xFF535353)
            )
        )
    }
}

@Composable
fun MusicApp(repository: TrackRepository, musicPlayer: MusicPlayer) {
    val navController = rememberNavController()
    val factory = remember { ViewModelFactory(repository, musicPlayer) }


    val homeViewModel: HomeViewModel = viewModel(factory = factory)
    val songInfoViewModel: SongInfoViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("home") {
            Scaffold(
                containerColor = Color(0xFF121212),
                bottomBar = { SpotifyBottomNavigationBar() }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    HomeScreen(
                        viewModel = homeViewModel,
                        onTrackClick = { track ->
                            navController.navigate("song_info/${track.id}")
                        }
                    )
                }
            }
        }

        composable(
            route = "song_info/{trackId}",
            arguments = listOf(navArgument("trackId") { type = NavType.StringType })
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getString("trackId") ?: ""
            val track = repository.getTrackById(trackId)

            Scaffold(
                containerColor = Color(0xFF121212),
                bottomBar = { SpotifyBottomNavigationBar() }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    SongInfoScreen(
                        songInfoViewModel = songInfoViewModel,
                        track = track
                    )
                }
            }
        }
    }
}