package com.rwz.lib_excel.core

import com.rwz.lib_excel.entity.CellType
import com.rwz.lib_excel.entity.ExcelConfig
import com.rwz.lib_excel.entity.ExcelEntity
import com.rwz.lib_excel.entity.RowEntity
import com.rwz.lib_excel.utils.LogUtil
import org.apache.poi.hssf.record.cf.PatternFormatting.SOLID_FOREGROUND
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Workbook
import org.openxmlformats.schemas.drawingml.x2006.main.STPresetColorVal.LIME
import java.io.FileOutputStream

/**
 * date： 2020/12/13 15:11
 * author： rwz
 * description：
 **/
object ExcelWriter {

    fun write(
        workbook: Workbook,
        config: ExcelConfig,
        entity: ExcelEntity
    ): Boolean {
        val rowList: List<RowEntity> = entity.list
        val sheet = workbook.createSheet()
        val lastRowNum = config.startRowIndex + rowList.size
        val endRowIndex =
            if (config.endRowIndex == -1) (lastRowNum - 1) else config.endRowIndex
        for (i in config.startRowIndex .. endRowIndex) {
            val rowEntity = rowList.getOrNull(i - config.startRowIndex)
            val columnList = rowEntity?.list
            if (rowEntity == null || columnList == null) {
                LogUtil.writeError("该行为空：i = $i, endRowIndex = $endRowIndex")
                continue
            }
            // 创建一行
            val row = sheet.createRow(i)
            // 设置行样式
            rowEntity.style?.let {
                val cellStyle = workbook.createCellStyle()
                cellStyle.fillBackgroundColor = HSSFColor.HSSFColorPredefined.RED.index
                cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
                row.rowStyle = cellStyle
            }
            val lastCellNum: Int = config.startColumnIndex + columnList.size
            val endColumnIndex =
                if (config.endColumnIndex == -1) (lastCellNum - 1) else config.endColumnIndex
            for (j in config.startColumnIndex .. endColumnIndex) {
                columnList.getOrNull(j - config.startColumnIndex)?.let {
                    LogUtil.write("ExcelWriter write：i = $i, j = $j , value = ${it.value}")
                    // 创建单元格
                    val cell = row.createCell(j)
                    it.style?.let {style ->
//                        val cellStyle = workbook.createCellStyle()
//                        cellStyle.fillBackgroundColor = style.color
//                        cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
//                        cell.cellStyle = cellStyle
                    }
                    // 如果有日期，优先设置日期
                    when (it.type) {
                        CellType.Text -> cell.setCellValue(it.value)
                        CellType.Bool -> cell.setCellValue(it.value)
                        CellType.Error -> cell.setCellValue(it.value)
                        CellType.Date -> cell.setCellValue(it.date)
                    }
                } ?: LogUtil.writeError("单元格为空：i = $i, j = $j")
            }
        }
        workbook.write(FileOutputStream(config.filePath))
        return true
    }

}