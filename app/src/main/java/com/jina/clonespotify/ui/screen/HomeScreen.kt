package com.jina.clonespotify.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jina.clonespotify.data.model.Track
import com.jina.clonespotify.utils.coverUrl


@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val colorScheme = MaterialTheme.colorScheme
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        containerColor = colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                // Header with greeting
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    colorScheme.primary.copy(alpha = 0.3f),
                                    colorScheme.background
                                )
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Good evening",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onBackground
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            IconButton(onClick = { }) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = colorScheme.onBackground
                                )
                            }
                            IconButton(onClick = { }) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "Settings",
                                    tint = colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }

            item {
                // Quick Access Grid
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(2) { col ->
                                QuickAccessCard(
                                    title = listOf(
                                        "Liked Songs", "Daily Mix 1",
                                        "Chill Vibes", "Your Top Songs 2024",
                                        "Discover Weekly", "Release Radar"
                                    )[row * 2 + col],
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader("Recently Played")
                HorizontalMusicList()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader("Made For You")
                HorizontalMusicList(isCircular = true)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader("Popular Albums")
                HorizontalMusicList()
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    isLoading: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search books...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true
        )

        Button(
            onClick = onSearch,
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Search")
            }
        }
    }
}

@Composable
fun TrackCard(track: Track) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 70.dp, height = 100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center

            ) {
                if (track.md5Image != null) {
                    AsyncImage(
                        model = coverUrl(track.md5Image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = track.artist?.name ?: "N/A",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                track.album?.title?.let { title ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Album,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = title.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//fun HorizontalMusicList(isCircular: Boolean = false) {
//    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
//    ) {
//        items(6) { index ->
//            MusicCard(
//                title = "Playlist ${index + 1}",
//                subtitle = "Artist Name • 2024",
//                isCircular = isCircular
//            )
//        }
//    }
//}
//
//@Composable
//fun MusicCard(title: String, subtitle: String, isCircular: Boolean = false) {
//    val colorScheme = MaterialTheme.colorScheme
//
//    Column(
//        modifier = Modifier.width(140.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .size(140.dp)
//                .clip(if (isCircular) CircleShape else RoundedCornerShape(8.dp))
//                .background(colorScheme.primary.copy(alpha = 0.2f)),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                Icons.Default.Album,
//                contentDescription = null,
//                tint = colorScheme.primary,
//                modifier = Modifier.size(60.dp)
//            )
//        }
//
//        Text(
//            text = title,
//            style = MaterialTheme.typography.bodyMedium,
//            fontWeight = FontWeight.SemiBold,
//            color = colorScheme.onBackground,
//            maxLines = 1
//        )
//
//        Text(
//            text = subtitle,
//            style = MaterialTheme.typography.bodySmall,
//            color = colorScheme.onBackground.copy(alpha = 0.7f),
//            maxLines = 1,
//            fontSize = 12.sp
//        )
//    }
}