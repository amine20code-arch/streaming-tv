fun startUsbRecording(context: Context, streamUrl: String, channelName: String) {
    // البحث عن مسارات التخزين الخارجية (USB)
    val externalDirs = ContextCompat.getExternalFilesDirs(context, null)
    val usbDir = externalDirs.firstOrNull { it != null && Environment.getExternalStorageState(it) == Environment.MEDIA_MOUNTED && !Environment.isExternalStorageEmulated(it) }
    
    if (usbDir == null) {
        Toast.makeText(context, "يرجى توصيل ذاكرة USB للتسجيل", Toast.LENGTH_LONG).show()
        return
    }

    val file = File(usbDir, "$channelName_${System.currentTimeMillis()}.ts")
    
    // تشغيل_thread لنسخ البث
    Thread {
        try {
            val request = Request.Builder().url(streamUrl).build()
            Net.http.newCall(request).execute().use { response ->
                response.body?.byteStream()?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output) // نسخ مباشر للبث
                    }
                }
            }
        } catch (e: Exception) { e.printStackTrace() }
    }.start()
}