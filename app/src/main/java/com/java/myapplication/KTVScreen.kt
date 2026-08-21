package com.java.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KTVScreen(initSuccess: Boolean, onBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    
    val songs = remember {
        listOf(
            SongItem("1", "月亮代表我的心", "邓丽君"),
            SongItem("2", "海阔天空", "Beyond"),
            SongItem("3", "十年", "陈奕迅"),
            SongItem("4", "突然好想你", "五月天"),
            SongItem("5", "平凡之路", "朴树"),
            SongItem("6", "后来", "刘若英"),
            SongItem("7", "红豆", "王菲"),
            SongItem("8", "富士山下", "陈奕迅"),
            SongItem("9", "富士山下", "陈奕迅"),
            SongItem("10", "富士山下", "陈奕迅")
        )
    }
    
    val filteredSongs = songs.filter { song ->
        song.name.contains(searchQuery, ignoreCase = true) ||
        song.singer.contains(searchQuery, ignoreCase = true)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KTV 点歌", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A2E)
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F0F1A))
                .padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("搜索歌曲或歌手...", color = Color.Gray) },
                shape = RoundedCornerShape(25.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE94560),
                    unfocusedBorderColor = Color(0xFF333344)
                )
            )
            
            if (!initSuccess) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Error, contentDescription = null, 
                            tint = Color(0xFFE94560), modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("KTV 服务初始化失败", color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("请检查 native 库是否加载成功", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(filteredSongs) { song ->
                        SongCard(
                            song = song,
                            onClick = { /* TODO: 调用 KTV 播放歌曲 */ }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

data class SongItem(val id: String, val name: String, val singer: String)

@Composable
fun SongCard(song: SongItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.MusicNote, contentDescription = null, tint = Color(0xFFE94560))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(song.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(song.singer, color = Color.Gray, fontSize = 14.sp)
            }
            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Gray)
        }
    }
}
