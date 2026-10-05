suspend fun checkForUpdates(context: Context) {
    val url = "https://api.github.com/repos/YOUR_USERNAME/StreamTV/releases/latest"
    val request = Request.Builder().url(url).build()
    
    Net.http.newCall(request).execute().use { response ->
        val json = JsonParser.parseString(response.body?.string()).asJsonObject
        val latestVersion = json.get("tag_name").asString.replace("v", "") // مثال: 2.1.0
        val currentVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        
        if (latestVersion > currentVersion) {
            val apkUrl = json.getAsJsonArray("assets")
                .get(0).asJsonObject.get("browser_download_url").asString
            
            // تحميل الـ APK عبر DownloadManager وتثبيتها
            downloadAndInstallApk(context, apkUrl)
        }
    }
}