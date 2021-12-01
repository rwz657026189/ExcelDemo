package com.rwz.lib_excel.core

import com.rwz.lib_excel.entity.CellEntity
import com.rwz.lib_excel.entity.ExcelConfig
import com.rwz.lib_excel.entity.ExcelEntity
import com.rwz.lib_excel.entity.RowEntity
import com.rwz.lib_excel.utils.ExcelUtil
import com.rwz.lib_excel.utils.LogUtil
import org.apache.poi.ss.usermodel.Workbook
import java.util.*

/**
 * date： 2020/12/13 15:11
 * author： rwz
 * description：
 **/
object ExcelReader {

    fun read(
        workbook: Workbook,
        entity: ExcelConfig,
        filter: ((Int, Int, CellEntity?) -> Boolean)?
    ): ExcelEntity {
        val sheet = workbook.getSheetAt(entity.sheetIndex)
        val endRowIndex =
            if (entity.endRowIndex == -1) sheet.lastRowNum else entity.endRowIndex
        LogUtil.write("ExcelReader lastRowNum = ${sheet.lastRowNum}")
        val data: MutableList<RowEntity> = ArrayList<RowEntity>()
        for (i in entity.startRowIndex .. endRowIndex) {
            val rowList: MutableList<CellEntity> = ArrayList()
            // 读取行
            val sheetRow = sheet.getRow(i)
            val lastCellNum = sheetRow.lastCellNum.toInt()
            val endColumnIndex =
                if (entity.endColumnIndex == -1) lastCellNum else entity.endColumnIndex
            for (j in entity.startColumnIndex .. endColumnIndex) {
                // 读取每行的单元格
                val cell = sheetRow.getCell(j)
                val value: CellEntity = ExcelUtil.getValue(cell)
                // 如果有过滤方法，需要过滤之后再添加进去
                rowList.takeIf { filter?.invoke(i, j, value) ?: true }?.add(value)
            }
            // 添加行
            data.add(RowEntity(rowList))
        }
        return ExcelEntity(data)
    }

}