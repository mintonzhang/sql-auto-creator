package cn.minsin.jfx.excel.enums;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author: minton.zhang
 * @since: 2020/3/31 22:57
 */
public enum ExcelVersion {
    /**
     * Excel版本
     */
    VERSION_2003(".xls", 2003), VERSION_2007(".xlsx", 2007);
    private String suffix;

    private int year;

    ExcelVersion(String suffix, int year) {
        this.suffix = suffix;
        this.year = year;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getYear() {
        return year;
    }

    /**
     * 判断当前work版本
     * @param workbook
     * @return
     */
    public static ExcelVersion checkVersion(Workbook workbook) {
        return workbook instanceof HSSFWorkbook ? ExcelVersion.VERSION_2003 : ExcelVersion.VERSION_2007;
    }
}
