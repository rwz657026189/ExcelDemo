package com.rwz.lib_excel.entity

/**
 * date： 2020/12/13 14:32
 * author： rwz
 * description：
 **/
data class ExcelConfig(
    // 文件路径
    var filePath: String,
    // sheet下标， 0开始
    var sheetIndex: Int = 0,
    // 开始行 1 => 0
    var startRowIndex: Int = 0,
    // 结束行 -1读取到最后
    var endRowIndex: Int = -1,
    // 开始列 A => 0
    var startColumnIndex: Int = 0,
    // 结束列 -1读取到最后
    var endColumnIndex: Int = -1
)