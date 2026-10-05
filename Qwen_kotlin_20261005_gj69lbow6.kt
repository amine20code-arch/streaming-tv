// في الـ ViewModel
fun getOrganizedContent() = flow {
    val allChannels = dao.getAllChannels() // استعلام يجلب كل القنوات
    val grouped = allChannels.groupBy { it.kind } // تقسيم حسب النوع
    
    val structuredData = grouped.map { (kind, channels) ->
        val subGroups = channels.groupBy { it.groupTitle } // تقسيم حسب المجموعات
        MainCategory(kind, subGroups.map { SubCategory(it.key, it.value) })
    }
    emit(structuredData)
}