package com.rwz.lib_excel

import com.rwz.lib_excel.core.ExcelManager
import com.rwz.lib_excel.entity.CellEntity
import com.rwz.lib_excel.entity.ExcelConfig
import com.rwz.lib_excel.entity.RowEntity
import com.rwz.lib_excel.entity.Style
import com.rwz.lib_excel.utils.LogUtil
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.IndexedColors

class Launcher {

}

fun main() {
    val config = ExcelConfig("E:\\temp\\excel\\Test.xlsx")
    val data = ExcelManager.read(config)
    LogUtil.write(data.toString())
    config.filePath = "E:\\temp\\excel\\Test2.xlsx"
    val entity = RowEntity(mutableListOf())
    entity.style = Style(color = HSSFColor.HSSFColorPredefined.RED.index)
    val cellEntity = CellEntity("赵丽颖")
//    val cellEntity = CellEntity("赵丽颖", style = Style(HSSFColor.HSSFColorPredefined.BLUE.index))
    entity.list.add(cellEntity)
    entity.list.add(CellEntity("28"))
    entity.list.add(CellEntity("女"))
    data?.list?.add(entity)
    ExcelManager.write(config, data!!)
}