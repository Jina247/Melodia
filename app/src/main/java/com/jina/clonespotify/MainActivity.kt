package com.jina.clonespotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jina.clonespotify.data.remote.RetrofitInstance
import com.jina.clonespotify.data.repository.TrackRepository
import com.jina.clonespotify.data.repository.ViewModelFactory
import com.jina.clonespotify.ui.screen.HomeScreen
import com.jina.clonespotify.ui.screen.HomeViewModel
import com.jina.clonespotify.ui.theme.CloneSpotifyTheme
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = TrackRepository(
            api = RetrofitInstance.api
        )
        setContent {
            CloneSpotifyTheme {
                Column {
                    MusicApp(repository)
                    BottomNavigationBar()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val colorScheme = MaterialTheme.colorScheme
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = colorScheme.surface,
        contentColor = colorScheme.onSurface
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorScheme.primary,
                selectedTextColor = colorScheme.primary,
                indicatorColor = colorScheme.primary.copy(alpha = 0.2f),
                unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorScheme.primary,
                selectedTextColor = colorScheme.primary,
                indicatorColor = colorScheme.primary.copy(alpha = 0.2f),
                unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.LibraryMusic, contentDescription = "Library") },
            label = { Text("Library") },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorScheme.primary,
                selectedTextColor = colorScheme.primary,
                indicatorColor = colorScheme.primary.copy(alpha = 0.2f),
                unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
    }
}

@Composable
fun MusicApp(repository: TrackRepository) {
    val viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(repository))
    HomeScreen(viewModel)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}