package xyz.eddief.funwithflows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EntryScreen(onSectionClick: (Section) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(Section.values()) {
            EntrySection(
                section = it,
                onSectionClick = { onSectionClick(it) }
            )
        }

    }
}

@Composable
fun EntrySection(
    section: Section,
    onSectionClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSectionClick)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(section.route)
    }
}