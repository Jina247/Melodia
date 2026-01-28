package com.jina.clonespotify.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jina.clonespotify.data.model.Track

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTrackClick: (Track) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        if (searchResults.isEmpty()) {
            viewModel.loadPopularTracks()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            item {
                SpotifyHeader()
            }

            // Search Bar
            item {
                SpotifySearchBar(
                    query = searchQuery,
                    onQueryChange = { viewModel.updateQuery(it) },
                    onSearch = { viewModel.searchTracks() }
                )
            }

            // Categories
            item {
                Text(
                    text = "Your top mixes",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }

            // Error Message
            errorMessage?.let { msg ->
                item {
                    Text(
                        text = msg,
                        color = Color(0xFFFF6B6B),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Loading
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF1DB954))
                    }
                }
            }

            // Tracks Grid
            if (searchResults.isNotEmpty()) {
                item {
                    TrackGrid(
                        tracks = searchResults,
                        onTrackClick = onTrackClick
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun SpotifyHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Good evening",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = Color.White
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.History,
                    contentDescription = "History",
                    tint = Color.White
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun SpotifySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("What do you want to listen to?", color = Color.Gray) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onSearch) {
                    Icon(Icons.Default.Send, contentDescription = "Search", tint = Color(0xFF1DB954))
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF282828),
            unfocusedContainerColor = Color(0xFF282828),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}

@Composable
fun TrackGrid(
    tracks: List<Track>,
    onTrackClick: (Track) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        tracks.chunked(2).forEach { rowTracks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowTracks.forEach { track ->
                    SpotifyTrackCard(
                        track = track,
                        onClick = { onTrackClick(track) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowTracks.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SpotifyTrackCard(
    track: Track,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(60.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF282828)
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFF404040))
            ) {
                if (track.coverUrl.isNotEmpty()) {
                    AsyncImage(
                        model = track.coverUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.Album,
                        contentDescription = null,
                        tint = Color(0xFF1DB954),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(30.dp)
                    )
                }
            }

            Text(
                text = track.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
            )
        }
    }
}