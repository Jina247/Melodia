package com.jina.clonespotify.ui.screen.songinfo

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
    homeViewMode: HomeViewModel,
    songInfoViewModel: SongInfoViewModel,
    track: Track,
//    onBackClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Column() {
        TopBar(homeViewMode)

        Spacer(modifier = Modifier.height(40.dp))

        AlbumCover(track)

        Spacer(modifier = Modifier.height(48.dp))

        ActionBar(
            songInfoViewModel,
            onPlayPauseClick = { songInfoViewModel.onPlayPauseClick(track.coverUrl)}
        )

    }

}

// Helper function to format time
@SuppressLint("DefaultLocale")
private fun formatTime(seconds: Long): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%d:%02d", mins, secs)
}

@Composable
fun TopBar(viewModel: HomeViewModel) {
    val colorScheme = colorScheme
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
            Icons.Default.KeyboardArrowDown,
            contentDescription = "Collapse",
            tint = colorScheme.onBackground.copy(0.7f)
            )
        }

        Text(
            text = viewModel.searchQuery.toString()
        )
    }
}

@Composable
fun AlbumCover(track: Track) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val size = screenWidth * 0.65f
     Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(20.dp),
         verticalArrangement = Arrangement.SpaceEvenly,
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
         if (track.coverUrl.isNotEmpty()) {
             AsyncImage(
                 model = track.coverUrl,
                 contentDescription = null,
                 modifier = Modifier
                     .fillMaxSize()
                     .size(size)
                     .aspectRatio(1f)
                     .clip(RoundedCornerShape(8.dp)),
                 contentScale = ContentScale.Crop
             )
         } else {
             Icon(
                 Icons.Default.Album,
                 contentDescription = null,
                 modifier = Modifier
                     .fillMaxSize()
                     .size(size)
                     .aspectRatio(1f)
                     .clip(RoundedCornerShape(8.dp)),
                 tint = colorScheme.primary
             )
         }

         Spacer(modifier = Modifier.height(8.dp))

         Text(
             text = track.title,
             style = MaterialTheme.typography.headlineSmall.copy(
                 fontWeight = FontWeight.Bold,
                 lineHeight = 32.sp
             ),
             maxLines = 2,
             overflow = TextOverflow.Ellipsis,
             textAlign = TextAlign.Center
         )

         Spacer(modifier = Modifier.height(6.dp))

         Text(
             text = track.artist.name,
             style = MaterialTheme.typography.bodyMedium.copy(
                 color = Color.Gray,
                 fontWeight = FontWeight.Medium
             ),
             maxLines = 1,
             overflow = TextOverflow.Ellipsis,
             textAlign = TextAlign.Center
         )
     }
}

@Composable
fun ActionBar(
    viewModel: SongInfoViewModel,
    onPlayPauseClick: () -> Unit,
    isPlaying: Boolean = false
) {
    val position by viewModel.position.collectAsState()
    val duration by viewModel.duration.collectAsState()

    val progress =
        if (duration > 0) position.toFloat() / duration.toFloat()
        else 0f

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {

        Slider(
            value = progress,
            onValueChange = { newProgress ->
                val newPosition = (newProgress * duration).toLong()
                viewModel.onSeek(newPosition)
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(formatTime(position))
            Text(formatTime(duration))
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Player Controls
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                Icons.Default.Shuffle,
                contentDescription = "Shuffle",
                tint = colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                Icons.Default.SkipPrevious,
                contentDescription = "Previous",
                tint = colorScheme.onBackground,
                modifier = Modifier.size(40.dp)
            )
        }

        // Play/Pause Button
        FloatingActionButton(
            onClick = onPlayPauseClick,
            modifier = Modifier.size(72.dp),
            containerColor = colorScheme.primary,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(
                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = colorScheme.onPrimary,
                modifier = Modifier.size(36.dp)
            )
        }

        IconButton(
            onClick = { },
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                Icons.Default.SkipNext,
                contentDescription = "Next",
                tint = colorScheme.onBackground,
                modifier = Modifier.size(40.dp)
            )
        }

        IconButton(
            onClick = { },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                Icons.Default.Repeat,
                contentDescription = "Repeat",
                tint = colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

