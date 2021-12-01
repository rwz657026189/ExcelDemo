package com.rwz.lib_excel.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * date： 2020/12/13 14:37
 * author： rwz
 * description：
 **/
object LogUtil {
    val LINE = System.lineSeparator()
    val DELIMITER = "====================================================="
    private val TEMP = StringBuilder()
    private val sdf = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
    private val startTime = System.currentTimeMillis()

    fun getTime(): String? = sdf.format(Date(System.currentTimeMillis()))

    fun write(text: String?) {
        TEMP.append(getTime()).append(" ").append(text).append(LINE)
        println(text)
    }
    fun writeError(text: String?) {
        TEMP.append(getTime()).append("【Error】").append(text).append(LINE)
        println("【Error】$text")
    }

    fun writeToFile(dstFile: String?) {
        val currTime = System.currentTimeMillis()
        val time = String.format("%.3fs", (currTime - startTime) / 1000)
        val headers: String = (LINE + DELIMITER  + DELIMITER + LINE +
                "DELIMITER   【" + getTime() + "】 耗时：" + time + LINE +
                DELIMITER + DELIMITER + LINE)
        FileUtil.writeText(dstFile, headers + TEMP.toString(), false)
    }
}