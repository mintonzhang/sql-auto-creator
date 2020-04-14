package cn.minsin.jfx.override;

import cn.minsin.jfx.excel.model.ExcelCellModel;
import cn.minsin.jfx.excel.model.ExcelParseResultModel;
import cn.minsin.jfx.excel.model.ExcelRowModel;
import cn.minsin.jfx.excel.model.ExcelSheetModel;
import cn.minsin.jfx.excel.model.SheetParseRule;
import cn.minsin.jfx.excel.tools.ExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/3/31 23:00
 */
public class ExcelParseFunctions {


    protected Workbook workbook;

    protected SheetParseRule sheetParseRule;


    public ExcelParseFunctions init(InputStream inputStream) throws IOException {
        this.workbook = ExcelUtil.parseInputStreamToWorkBook(inputStream);
        return this;
    }

    public ExcelParseFunctions init(File file) throws IOException {
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
    public static ExcelParseFunctions initByFile(File file) throws IOException {
        return new ExcelParseFunctions().init(file);
    }

    /**
     * 静态初始化
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static ExcelParseFunctions initByInputStream(InputStream inputStream) throws IOException {
        return new ExcelParseFunctions().init(inputStream);
    }

    public ExcelParseFunctions() {
    }


    /**
     * @param parseSheetRule
     * @return
     */
    public ExcelParseFunctions addSheetRules(SheetParseRule parseSheetRule) {
        this.sheetParseRule = parseSheetRule;
        return this;
    }


    /**
     * 调用前必须设置SheetRules  参照以下方法
     * <p>{@link ExcelParseFunctions#addSheetRules(SheetParseRule)}</p>
     * 按照 sheetParseRule进行解析
     */
    public ExcelParseResultModel parse() {
        ExcelParseResultModel excelParseResultModel = new ExcelParseResultModel();

        int sheetIndex = sheetParseRule.getParseRowRule().getStartRowIndex();
        List<Integer> parseCellIndexes = sheetParseRule.getParseRowRule().getParseCellIndexes();

        Iterator<Sheet> sheetIterator = workbook.sheetIterator();

        int index =0;
        while (sheetIterator.hasNext()){
            index++;
            Sheet sheet = sheetIterator.next();
            if(index>3){
                ExcelSheetModel excelSheetModel = new ExcelSheetModel();
                excelSheetModel.setSheetName(sheet.getSheetName());
                int lastRowNum = sheet.getLastRowNum();

                for (int i = sheetIndex; i <= lastRowNum; i++) {
                    Row next = sheet.getRow(i);
                    if (next == null) {
                        continue;
                    }
                    ExcelRowModel excelRowModel = new ExcelRowModel();
                    excelRowModel.setRowIndex(next.getRowNum());
                    for (int needCell : parseCellIndexes) {
                        Cell cell = next.getCell(needCell);
                        if (cell != null) {
                            excelRowModel.addCells(new ExcelCellModel(needCell, cell));
                        }
                    }
                    excelSheetModel.addRows(excelRowModel);
                }
                excelParseResultModel.addSheet(excelSheetModel);
            }
        }
        return excelParseResultModel;
    }
}
