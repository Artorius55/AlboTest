package utils

import java.util.*

class DateUtils {
    companion object{
        fun getMonthName(month: Int) : String{
            val mCalendar = Calendar.getInstance()
            mCalendar.set(Calendar.MONTH,month)
            return mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        }
    }
}