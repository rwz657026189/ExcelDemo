package com.rwz.lib_excel.entity

/**
 * date： 2020/12/13 14:31
 * author： rwz
 * description：
 **/
data class ExcelEntity(
    var list: MutableList<RowEntity>,
    var style: Style? = null
)