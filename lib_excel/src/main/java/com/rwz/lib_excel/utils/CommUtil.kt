package com.rwz.lib_excel.utils

/**
 * date： 2020/12/13 15:02
 * author： rwz
 * description：
 **/
object CommUtil {

    fun <D> getEntity(list: MutableList<D>?, index: Int): D?
            = list?.getOrNull(index)

    fun equalsText(one: String?, two: String?): Boolean
            = one != null && two != null && one == two

}