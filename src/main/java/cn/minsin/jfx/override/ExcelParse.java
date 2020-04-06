package cn.minsin.jfx.override;

import cn.minsin.core.exception.MutilsException;
import cn.minsin.jfx.excel.model.ExcelSheetModel;
import cn.minsin.jfx.excel.tools.ExcelUtil;
import cn.minsin.jfx.excel.model.ExcelCellModel;
import cn.minsin.jfx.excel.model.ExcelParseResultModel;
import cn.minsin.jfx.excel.model.ExcelRowModel;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/4/4 23:22
 */
public class ExcelParse {
    @Getter
    protected Workbook workbook;

    protected List<Integer> startParseSheetIndex = new ArrayList<>(5);

    protected boolean isSheetMeasurable = true;


    protected List<Integer> startParseRowIndex = new ArrayList<>(10);

    protected boolean isRowMeasurable = true;


    protected List<Integer> startParseCellIndex = new ArrayList<>(10);


    public ExcelParse init(InputStream inputStream) throws IOException {
        this.workbook = ExcelUtil.parseInputStreamToWorkBook(inputStream);
        return this;
    }

    public ExcelParse init(File file) throws IOException {
        this.workbook = ExcelUtil.parseFileToWorkBook(file);
        return this;
    }

    /**
     * 静态初始化
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static ExcelParse initByFile(File file) throws IOException {
        return new ExcelParse().init(file);
    }

    /**
     * 静态初始化
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static ExcelParse initByInputStream(InputStream inputStream) throws IOException {
        return new ExcelParse().init(inputStream);
    }


    public ExcelParse() {
    }

    public ExcelParse sheetParse(int... sheetIndex) {
        for (int index : sheetIndex) {
            this.startParseSheetIndex.add(index);
        }
        return this;
    }

    public ExcelParse sheetParseBetween(int begin, int end) {
        for (int i = begin; i <= end; i++) {
            this.startParseSheetIndex.add(i);
        }
        return this;
    }

    public ExcelParse sheetParse(int begin) {
        this.startParseSheetIndex.add(begin);
        this.isSheetMeasurable = false;
        return this;
    }

    public ExcelParse rowParse(int... sheetIndex) {
        for (int index : sheetIndex) {
            this.startParseRowIndex.add(index);
        }
        return this;
    }

    public ExcelParse rowParseBetween(int begin, int end) {
        for (int i = begin; i <= end; i++) {
            this.startParseRowIndex.add(i);
        }
        return this;
    }

    public ExcelParse rowParse(int begin) {
        this.startParseRowIndex.add(begin);
        this.isRowMeasurable = false;
        return this;
    }

    public ExcelParse cellParse(int... sheetIndex) {
        for (int index : sheetIndex) {
            this.startParseCellIndex.add(index);
        }
        return this;
    }

    public ExcelParse cellParseBetween(int begin, int end) {
        for (int i = begin; i <= end; i++) {
            this.startParseCellIndex.add(i);
        }
        return this;
    }

    public ExcelParseResultModel parse() throws MutilsException {

        MutilsException.throwException(this.startParseSheetIndex.isEmpty(), "请输入要解析的工作表(Sheet)");
        MutilsException.throwException(this.startParseRowIndex.isEmpty(), "请输入要解析的列");
        MutilsException.throwException(this.startParseCellIndex.isEmpty(), "请输入要解析的行");

        ExcelParseResultModel excelParseResultModel = new ExcelParseResultModel();

        //重新定义解析的sheet
        if (!this.isSheetMeasurable) {
            int numberOfSheets = this.workbook.getNumberOfSheets();
            for (int i = this.startParseSheetIndex.get(0) + 1; i < numberOfSheets; i++) {
                this.startParseSheetIndex.add(i);
            }
        }
        for (Integer parseSheetIndex : startParseSheetIndex) {
            Sheet sheet = this.workbook.getSheetAt(parseSheetIndex);
            ExcelSheetModel excelSheetModel = new ExcelSheetModel();
            excelSheetModel.setSheetName(sheet.getSheetName());
            int lastRowNum = sheet.getLastRowNum();

            List<Integer> oldRowList = new ArrayList<>();
            //复制原row的list
            if (!this.isRowMeasurable) {
                for (int i = this.startParseRowIndex.get(0); i < lastRowNum; i++) {
                    oldRowList.add(i);
                }
            } else {
                oldRowList.addAll(this.startParseRowIndex);
            }
            for (Integer rowIndex : oldRowList) {
                Row next = sheet.getRow(rowIndex);
                if (next == null) {
                    continue;
                }
                ExcelRowModel excelRowModel = new ExcelRowModel();
                excelRowModel.setRowIndex(next.getRowNum());
                for (int needCell : this.startParseCellIndex) {
                    Cell cell = next.getCell(needCell);
                    if (cell == null) {
                        cell = next.createCell(needCell);
                    }
                    excelRowModel.addCells(new ExcelCellModel(needCell, cell));
                }
                excelSheetModel.addRows(excelRowModel);
            }
            excelParseResultModel.addSheet(excelSheetModel);
        }
        return excelParseResultModel;
    }
}
