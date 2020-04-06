package cn.minsin.jfx.excel.tools;

import cn.minsin.core.tools.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * @author: minton.zhang
 * @since: 2020/3/31 22:58
 */
@Slf4j
public class ExcelUtil {


    /**
     * 获取Excel模板文件
     *
     * @param excelPath
     * @param isInDisk 当为false时 则获取resource下的文件 填写时开头不能以/开头
     * @return
     */
    public static InputStream getExcelTemplate(String excelPath, boolean isInDisk) throws FileNotFoundException {
        if (isInDisk) {
            return new FileInputStream(excelPath);
        } else {
            return ExcelUtil.class.getClassLoader().getResourceAsStream(excelPath);
        }
    }

    /**
     * 获取CellValue
     *
     * @param cell
     */
    public static String getCellRealValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String cellValue;
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
            // 数字
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                } else {
                    // 纯数字
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            // 字符串
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            // Boolean
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;
            // 公式
            case FORMULA:
                cellValue = cell.getCellFormula() + "";
                break;
            // 空值
            case BLANK:
                cellValue = "";
                break;
            // 故障
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return StringUtil.filterSpace(cellValue);
    }

    /**
     * 解析文件输入流
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static Workbook parseInputStreamToWorkBook(InputStream inputStream) throws IOException {
        return WorkbookFactory.create(inputStream);
    }

    /**
     * 解析excel文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook parseFileToWorkBook(File file) throws IOException {
        return WorkbookFactory.create(file);
    }
}
