package abdulmanov.eduard.githubrepositories.domain.common

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    private val basicDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun convertDateToBasicFormat(dateStr: String, originalDateFormat: SimpleDateFormat): String {
        val date = parseDate(dateStr, originalDateFormat)
        return basicDateFormat.format(date)
    }

    fun convertDateFromBasicFormat(dateStr: String, targetDateFormat: SimpleDateFormat): String {
        val date = parseDate(dateStr, basicDateFormat)
        return targetDateFormat.format(date)
    }

    private fun parseDate(dateStr: String, dateFormat: SimpleDateFormat): Date {
        return if (dateStr.isEmpty()) {
            Date()
        } else {
            dateFormat.parse(dateStr) ?: Date()
        }
    }
}