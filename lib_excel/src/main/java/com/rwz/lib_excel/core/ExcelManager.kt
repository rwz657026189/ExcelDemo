package com.rwz.lib_excel.core

import com.rwz.lib_excel.entity.CellEntity
import com.rwz.lib_excel.entity.ExcelConfig
import com.rwz.lib_excel.entity.ExcelEntity
import com.rwz.lib_excel.utils.FileUtil
import com.rwz.lib_excel.utils.LogUtil
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

/**
 * date： 2020/12/13 15:01
 * author： rwz
 * description：
 **/
object ExcelManager {

    /**
     * 读取excel表格
     */
    fun read(config: ExcelConfig,
             filter: ((Int, Int, CellEntity?) -> Boolean)? = null) : ExcelEntity? {
        val workbook = geneWorkbook(config.filePath, true)
        var result : ExcelEntity? = null
        try {
            result = workbook?.let { ExcelReader.read(it, config, filter) }
        } catch (e: Exception) {
        } finally {
            workbook?.close()
        }
        return result
    }

    fun write(config: ExcelConfig,
              entity: ExcelEntity) : Boolean{
        val workbook = geneWorkbook(config.filePath, false)
        var result = false
        try {
            result = workbook?.let { ExcelWriter.write(it, config, entity) } ?: false
        } catch (e: Exception) {
        } finally {
            workbook?.close()
        }
        return result
    }

    private fun geneWorkbook(filePath: String, isRead: Boolean): Workbook? {
        var excel = File(filePath)
        if (!excel.exists()) {
            if (isRead) {
                return null
            } else {
                excel = FileUtil.createFile(filePath);
            }
        }
        val extensionName = FileUtil.getFileExtensionName(filePath);
        try {
            return when(extensionName) {
                FileType.XLS.extraName -> if (isRead) {
                    HSSFWorkbook(FileInputStream(excel))
                } else {
                    HSSFWorkbook()
                }
                FileType.XLSX.extraName -> if (isRead) {
                    XSSFWorkbook(excel)
                } else {
                    XSSFWorkbook()
                }
                else -> null
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }


}