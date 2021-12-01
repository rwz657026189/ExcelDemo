package com.rwz.lib_excel.entity

import java.util.*

/**
 * date： 2020/12/13 14:25
 * author： rwz
 * description：
 **/

data class CellEntity(
    var value: String? = null,
    var date: Date? = null,
    var type: CellType = CellType.Text,
    var style: Style? = null
) {
    override fun toString(): String {
        return value + ""
    }
}

enum class CellType {
    Date, Text, Bool, Error
}