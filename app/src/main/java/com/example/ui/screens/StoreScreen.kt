package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppCategory
import com.example.model.AppItem
import com.example.model.AppPlatform
import com.example.ui.components.PillButton
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

enum class SortOption(val displayName: String) {
    MOST_DOWNLOADED("Most Downloaded"),
    TOP_RATED("Top Rated"),
    NEWEST("Newest"),
    SMALLEST_SIZE("Smallest Size")
}

@Composable
fun StoreScreen(
    apps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    onQuickDownload: (AppItem) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedPlatform by remember { mutableStateOf<AppPlatform?>(null) }
    var selectedCategory by remember { mutableStateOf(AppCategory.ALL) }
    var selectedSort by remember { mutableStateOf(SortOption.MOST_DOWNLOADED) }
    var isGridView by remember { mutableStateOf(false) }

    val filteredApps = remember(apps, searchQuery, selectedPlatform, selectedCategory, selectedSort) {
        apps.filter { app ->
            val matchesSearch = searchQuery.isBlank() ||
                    app.name.contains(searchQuery, ignoreCase = true) ||
                    app.tagline.contains(searchQuery, ignoreCase = true) ||
                    app.developerName.contains(searchQuery, ignoreCase = true)
            val matchesPlatform = selectedPlatform == null || app.platform == selectedPlatform
            val matchesCategory = selectedCategory == AppCategory.ALL || app.category == selectedCategory
            matchesSearch && matchesPlatform && matchesCategory
        }.sortedWith { a, b ->
            when (selectedSort) {
                SortOption.MOST_DOWNLOADED -> b.downloadCount.compareTo(a.downloadCount)
                SortOption.TOP_RATED -> b.rating.compareTo(a.rating)
                SortOption.NEWEST -> b.releaseDate.compareTo(a.releaseDate)
                SortOption.SMALLEST_SIZE -> a.fileSizeMb.compareTo(b.fileSizeMb)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // SEARCH BAR
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search apps, utilities, tools, developers...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = NeonCyan)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.White)
                    }
                }
            },
            singleLine = true,
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = Color(0xFF263366),
                focusedContainerColor = Color(0xFF0F152E),
                unfocusedContainerColor = Color(0xFF0F152E)
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // PLATFORM FILTER ROW
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 2.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedPlatform == null,
                    onClick = { selectedPlatform = null },
                    label = { Text("All Platforms") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonCyan,
                        selectedLabelColor = Color.Black
                    )
                )
            }
            items(AppPlatform.entries) { platform ->
                FilterChip(
                    selected = selectedPlatform == platform,
                    onClick = {
                        selectedPlatform = if (selectedPlatform == platform) null else platform
                    },
                    label = { Text(platform.displayName) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(platform.badgeColorHex),
                        selectedLabelColor = Color.Black
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // CATEGORY FILTER ROW
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 2.dp)
        ) {
            items(AppCategory.entries) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    label = { Text(cat.displayName) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonPurple,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // RESULTS HEADER WITH SORT & VIEW TOGGLE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${filteredApps.size} apps available",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF94A3B8)
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // View toggle icon
                IconButton(
                    onClick = { isGridView = !isGridView },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isGridView) Icons.Default.ViewList else Icons.Default.GridView,
                        contentDescription = "Toggle View",
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // APP LIST OR GRID
        if (filteredApps.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No apps found matching your filters",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Text(
                        text = "Try clearing search keywords or selecting all platforms.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        } else if (isGridView) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredApps) { app ->
                    StoreAppGridItem(
                        app = app,
                        onClick = { onAppClick(app) },
                        onQuickDownload = { onQuickDownload(app) }
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredApps) { app ->
                    StoreAppListItem(
                        app = app,
                        onClick = { onAppClick(app) },
                        onQuickDownload = { onQuickDownload(app) }
                    )
                }
            }
        }
    }
}

@Composable
fun StoreAppListItem(
    app: AppItem,
    onClick: () -> Unit,
    onQuickDownload: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0F152E))
            .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(app.iconColor)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.iconSymbol,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = app.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    if (app.isVerified) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = NeonEmerald,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Text(
                    text = app.tagline,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(app.platform.badgeColorHex).copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = app.platform.extension.uppercase().removePrefix("."),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(app.platform.badgeColorHex),
                            fontSize = 10.sp
                        )
                    }

                    Text(
                        text = "★ ${app.rating}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFFFFB020)
                    )

                    Text(
                        text = "${app.fileSizeMb} MB",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF64748B)
                    )
                }
            }

            // Action
            IconButton(
                onClick = onQuickDownload,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(NeonCyan)
                    .size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Download",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun StoreAppGridItem(
    app: AppItem,
    onClick: () -> Unit,
    onQuickDownload: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0F152E))
            .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(app.iconColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = app.iconSymbol,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(app.platform.badgeColorHex).copy(alpha = 0.2f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = app.platform.extension.uppercase().removePrefix("."),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(app.platform.badgeColorHex),
                        fontSize = 9.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = app.name,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                maxLines = 1
            )

            Text(
                text = app.developerName,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF94A3B8),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "★ ${app.rating}",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFFFFB020)
                )

                IconButton(
                    onClick = onQuickDownload,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(NeonCyan)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
