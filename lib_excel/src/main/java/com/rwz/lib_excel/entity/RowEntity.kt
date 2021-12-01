package com.rwz.lib_excel.entity

/**
 * date： 2020/12/13 14:30
 * author： rwz
 * description：
 **/
data class RowEntity(
    var list: MutableList<CellEntity>,
    var style: Style? = null
)