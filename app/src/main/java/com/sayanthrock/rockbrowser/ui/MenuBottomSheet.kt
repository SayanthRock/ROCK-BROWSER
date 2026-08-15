package com.sayanthrock.rockbrowser.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun MenuBottomSheet(onDismiss: () -> Unit, onNavigateTo: (String) -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f)
    ) {
        LazyColumn(modifier = Modifier.padding(bottom = 32.dp).padding(horizontal = 8.dp)) {
            item {
                Text(
                    "ROCK BROWSER",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item { MenuItem("New tab") { onDismiss(); onNavigateTo("new_tab") } }
            item { MenuItem("New private tab") { onDismiss(); onNavigateTo("new_private_tab") } }
            item { MenuItem("Bookmarks") { onDismiss(); onNavigateTo("bookmarks") } }
            item { MenuItem("History") { onDismiss(); onNavigateTo("history") } }
            item { MenuItem("Downloads") { onDismiss(); onNavigateTo("downloads") } }
            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }
            item { MenuItem("Share") { onDismiss(); onNavigateTo("share") } }
            item { MenuItem("Find in page") { onDismiss(); onNavigateTo("find_in_page") } }
            item { MenuItem("Desktop site") { onDismiss(); onNavigateTo("desktop_site") } }
            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }
            item { MenuItem("GitHub") { onDismiss(); onNavigateTo("github") } }
            item { MenuItem("Settings") { onDismiss(); onNavigateTo("settings") } }
            item { MenuItem("About") { onDismiss(); onNavigateTo("about") } }
        }
    }
}

@Composable fun MenuItem(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
