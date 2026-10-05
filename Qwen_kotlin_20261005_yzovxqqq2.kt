@Composable
fun NetflixStylePlayerControls(
    exoPlayer: ExoPlayer,
    epgData: EpgEntity?, // البرنامج الحالي، التالي، السابق
    onAction: (PlayerAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // إخفاء تلقائي بعد 5 ثوانٍ
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(5000)
            isVisible = false
        }
    }

    // إظهار عند حركة الـ Remote
    LaunchedEffect(Unit) {
        // يمكن ربط هذا بـ onKeyEvent في الشاشة الرئيسية
    }

    if (isVisible) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                // تأثير الزجاج الضبابي (Glassmorphism)
                .background(Color.Black.copy(alpha = 0.4f))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(16.dp)
        ) {
            Column {
                // معلومات البرنامج (السابق، الحالي، التالي)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    EpgInfoBox(title = "السابق", program = epgData?.previous, color = Color.Gray)
                    EpgInfoBox(title = "يُعرض الآن", program = epgData?.current, color = Color.White, isBold = true)
                    EpgInfoBox(title = "التالي", program = epgData?.next, color = Color.LightGray)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // أزرار التحكم
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ControlButton(icon = Icons.Default.SkipPrevious) { onAction(PlayerAction.Prev) }
                    ControlButton(icon = Icons.Default.Replay5) { onAction(PlayerAction.Rewind) }
                    
                    // زر التشغيل/الإيقاف الكبير
                    ControlButton(
                        icon = if (exoPlayer.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        size = 56.dp,
                        bgColor = Color.White.copy(alpha = 0.2f)
                    ) { onAction(PlayerAction.TogglePlay) }
                    
                    ControlButton(icon = Icons.Default.Forward5) { onAction(PlayerAction.Forward) }
                    ControlButton(icon = Icons.Default.SkipNext) { onAction(PlayerAction.Next) }
                    
                    // زر التسجيل على USB
                    ControlButton(icon = Icons.Default.FiberManualRecord, iconTint = Color.Red) { 
                        onAction(PlayerAction.RecordToUsb) 
                    }
                }
                
                // شريط التقدم
                Slider(
                    value = exoPlayer.currentPosition.toFloat(),
                    onValueChange = { exoPlayer.seekTo(it.toLong()) },
                    valueRange = 0f..exoPlayer.duration.toFloat(),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = SliderDefaults.colors(thumbColor = Color.White, activeTrackColor = Color.Red)
                )
            }
        }
    }
}