package com.jina.clonespotify.ui.screen.songinfo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jina.clonespotify.data.model.Track
import com.jina.clonespotify.ui.screen.home.HomeViewModel

@Composable
fun SongInfoScreen(
    songInfoViewModel: SongInfoViewModel,
    track: Track?
) {
    val isPlaying by songInfoViewModel.isPlaying.collectAsState()
    val position by songInfoViewModel.position.collectAsState()
    val duration by songInfoViewModel.duration.collectAsState()

    if (track == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center
        ) {
            Text("Track not found", color = Color.White)
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF404040),
                        Color(0xFF121212),
                        Color(0xFF121212)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top Bar
            SpotifyTopBar()

            Spacer(modifier = Modifier.height(20.dp))

            // Album Art
            SpotifyAlbumArt(track)

            Spacer(modifier = Modifier.weight(1f))

            // Track Info
            SpotifyTrackInfo(track)

            Spacer(modifier = Modifier.height(24.dp))

            // Progress Bar
            SpotifyProgressBar(
                position = position,
                duration = duration,
                onSeek = { songInfoViewModel.onSeek(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Player Controls
            SpotifyPlayerControls(
                isPlaying = isPlaying,
                onPlayPause = {
                    if (track.audioUrl.isNotEmpty()) {
                        songInfoViewModel.onPlayPauseClick(track.audioUrl)
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Bottom Actions
            SpotifyBottomActions()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SpotifyTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Navigate back */ }) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "More",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SpotifyAlbumArt(track: Track) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        if (track.coverUrl.isNotEmpty()) {
            AsyncImage(
                model = track.coverUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF282828)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Album,
                    contentDescription = null,
                    tint = Color(0xFF1DB954),
                    modifier = Modifier.size(120.dp)
                )
            }
        }
    }
}

@Composable
fun SpotifyTrackInfo(track: Track) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = track.artist?.name ?: "Unknown",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFB3B3B3),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Like",
                    tint = Color(0xFFB3B3B3),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun SpotifyProgressBar(
    position: Long,
    duration: Long,
    onSeek: (Long) -> Unit
) {
    val progress = if (duration > 0) position.toFloat() / duration.toFloat() else 0f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Slider(
            value = progress,
            onValueChange = { newProgress ->
                val newPosition = (newProgress * duration).toLong()
                onSeek(newPosition)
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color(0xFF404040)
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(position / 1000),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB3B3B3),
                fontSize = 12.sp
            )
            Text(
                text = formatTime(duration / 1000),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB3B3B3),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun SpotifyPlayerControls(
    isPlaying: Boolean,
    onPlayPause: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                Icons.Default.Shuffle,
                contentDescription = "Shuffle",
                tint = Color(0xFF1DB954),
                modifier = Modifier.size(24.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                Icons.Default.SkipPrevious,
                contentDescription = "Previous",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        FloatingActionButton(
            onClick = onPlayPause,
            modifier = Modifier.size(64.dp),
            containerColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                Icons.Default.SkipNext,
                contentDescription = "Next",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                Icons.Default.Repeat,
                contentDescription = "Repeat",
                tint = Color(0xFFB3B3B3),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SpotifyBottomActions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                Icons.Default.Devices,
                contentDescription = "Devices",
                tint = Color(0xFFB3B3B3),
                modifier = Modifier.size(24.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                Icons.Default.Share,
                contentDescription = "Share",
                tint = Color(0xFFB3B3B3),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@SuppressLint("DefaultLocale")
private fun formatTime(seconds: Long): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%d:%02d", mins, secs)
}