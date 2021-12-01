package com.rwz.lib_excel.utils

import com.rwz.lib_excel.entity.CellEntity
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import java.util.*

/**
 * date： 2020/12/13 15:31
 * author： rwz
 * description：
 **/
object ExcelUtil {
    fun getValue(cell: Cell?): CellEntity {
        val entity = CellEntity()
        if (cell == null) {
            return entity
        }
        val cellType = cell.cellType
        if (cellType == CellType.STRING) {
            entity.value = cell.stringCellValue
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                entity.date = cell.dateCellValue
                entity.type = com.rwz.lib_excel.entity.CellType.Date
            } else {
                entity.value = cell.numericCellValue.toString()
            }
        } else if (cellType == CellType.BOOLEAN) {
            entity.value = cell.booleanCellValue.toString()
            entity.type = com.rwz.lib_excel.entity.CellType.Bool
        } else if (cellType == CellType.ERROR) {
            entity.value = cell.errorCellValue.toString()
            entity.type = com.rwz.lib_excel.entity.CellType.Error
        } else {
            entity.value = cell.stringCellValue
        }
        return entity
    }
}