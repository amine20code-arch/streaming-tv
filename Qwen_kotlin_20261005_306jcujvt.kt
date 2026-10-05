fun importAndFindBestCode(context: Context, uri: Uri): String {
    val content = context.contentResolver.openInputStream(uri)?.bufferedReader().use { it?.readText() } ?: return ""
    
    // استخراج الروابط وأكواد Xtream باستخدام Regex
    val regex = Regex("""(http[s]?://[^\s]+|user=[^\s&]+&pass=[^\s&]+)""")
    val foundCodes = regex.findAll(content).map { it.value }.distinct().toList()
    
    var bestCode = ""
    var maxChannels = 0
    
    for (code in foundCodes) {
        try {
            // إذا كان كود Xtream، نتحقق من عدد القنوات
            if (code.contains("user=") || code.contains("username=")) {
                val count = XtreamClient(code).countChannels() // دالة افتراضية تجلب العدد
                if (count > maxChannels) {
                    maxChannels = count
                    bestCode = code
                }
            }
        } catch (e: Exception) { /* تجاهل الأكواد الخاطئة */ }
    }
    return bestCode
}