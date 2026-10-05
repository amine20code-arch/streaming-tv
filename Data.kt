@Composable
fun CodesDashboardScreen(playlists: List<PlaylistEntity>, dao: AppDao) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp)) {
        items(playlists) { playlist ->
            // جلب الإحصائيات لكل قائمة
            val liveCount = dao.countByKind(playlist.id, "live")
            val vodCount = dao.countByKind(playlist.id, "movie")
            val seriesCount = dao.countByKind(playlist.id, "series")
            val expiryDate = getExpiryDate(playlist) // دالة تجلب تاريخ الانتهاء من Xtream API

            Card(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(playlist.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("ينتهي في: $expiryDate", color = Color.Red.copy(alpha = 0.8f), fontSize = 12.sp)
                    
                    Divider(color = Color.White.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 8.dp))
                    
                    // جدول الإحصائيات
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        StatItem("القنوات", liveCount.toString(), Icons.Default.LiveTv)
                        StatItem("الأفلام", vodCount.toString(), Icons.Default.Movie)
                        StatItem("المسلسلات", seriesCount.toString(), Icons.Default.Tv)
                    }
                }
            }
        }
    }
}