package com.sayanthrock.rockbrowser.feature.privatemode.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
@Composable fun PrivateModeIntroScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "ROCK PRIVATE", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Private browsing prevents ROCK BROWSER from saving normal local browsing history.\n\nIt does not make you anonymous online.", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}
