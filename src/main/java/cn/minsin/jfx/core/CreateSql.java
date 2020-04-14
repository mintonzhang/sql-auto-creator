package cn.minsin.jfx.core;

import cn.minsin.core.tools.StringUtil;
import cn.minsin.jfx.constant.DefaultConstant;
import cn.minsin.jfx.excel.model.ExcelCellModel;
import cn.minsin.jfx.excel.model.ExcelParseResultModel;
import cn.minsin.jfx.excel.model.ExcelRowModel;
import cn.minsin.jfx.excel.model.ExcelSheetModel;
import cn.minsin.jfx.model.TableModel;
import cn.minsin.jfx.override.ExcelParse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/4/4 23:10
 */
public class CreateSql {


    private static String CREATE_DATA_BASE = "DROP DATABASE IF EXISTS `{DATABASE_NAME}`;\r\n"
            + " CREATE DATABASE `{DATABASE_NAME}` DEFAULT CHARACTER SET `{CHARSET}` COLLATE `{CHARSET}_general_ci`;\r\n"
            + "USE `{DATABASE_NAME}`;\r\n";


    public static StringBuffer createSql(String databaseName, File file, String charset) throws IOException {
        StringBuffer stringBuffer = new StringBuffer(CREATE_DATA_BASE.replace(DefaultConstant.DATABASE_NAME, databaseName).replace(DefaultConstant.CHARSET, charset));
        FileInputStream fileInputStream = new FileInputStream(file);
        List<TableModel> parse = parse(fileInputStream);
        for (TableModel tableModel : parse) {
            String dropSql = tableModel.getDropSql();
            String createSql = tableModel.getCreateSql(charset);
            stringBuffer.append("\r\n")
                    .append(dropSql).append("\r\n")
                    .append(createSql);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(stringBuffer.toString().getBytes());
        return stringBuffer;
    }




    public static List<TableModel> parse(InputStream in) throws IOException {

        int[] cellList = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        ExcelParseResultModel parse = ExcelParse.initByInputStream(in).sheetParse(3).rowParse(0).cellParse(cellList).parse();

        List<TableModel> list = new ArrayList<>(10);

        for (ExcelSheetModel sheetModel : parse.getRows()) {
            List<ExcelRowModel> rows = sheetModel.getRows();
            TableModel model = null;
            List<TableModel.Column> cs = null;
            boolean flag = false;
            for (ExcelRowModel row : rows) {
                List<ExcelCellModel> cells = row.getCells();
                String cellRealValue0 = cells.get(0).getCellValue();
                String cellRealValue1 = cells.get(1).getCellValue();
                String cellRealValue2 = cells.get(2).getCellValue();
                String cellRealValue3 = cells.get(3).getCellValue();
                String cellRealValue4 = cells.get(4).getCellValue();
                String cellRealValue5 = cells.get(5).getCellValue();
                String cellRealValue6 = cells.get(6).getCellValue();
                String cellRealValue7 = cells.get(7).getCellValue();
                String cellRealValue8 = cells.get(8).getCellValue();
                String cellRealValue9 = cells.get(9).getCellValue();
                if (StringUtil.isAllBlank(cellRealValue0, cellRealValue1, cellRealValue2, cellRealValue3,
                        cellRealValue4, cellRealValue5, cellRealValue6, cellRealValue7, cellRealValue8,
                        cellRealValue9)) {
                    continue;
                }
                if ("start".equalsIgnoreCase(cellRealValue0)) {
                    model = new TableModel();
                    cs = new ArrayList<>();
                    model.setRemark(cellRealValue2);
                    model.setTableName(cellRealValue1);
                    model.setColumn(cs);
                    flag = true;
                    continue;
                }

                if (flag) {
                    flag = false;
                    continue;
                }
                if (cs != null) {
                    TableModel.Column c = new TableModel().new Column();
                    c.setColumn(cellRealValue1);
                    c.setType(cellRealValue2);
                    c.setLength(cellRealValue3);
                    c.setNull("y".equalsIgnoreCase(cellRealValue4));
                    c.setPrimaryKey(!StringUtil.isBlank(cellRealValue5));
                    c.setIncrease("y".equalsIgnoreCase(cellRealValue6));
                    c.setDescription(cellRealValue7);
                    c.setDefaultValue(cellRealValue8);
                    c.setRemark(cellRealValue9);
                    cs.add(c);
                }
                if ("end".equalsIgnoreCase(cellRealValue0)) {
                    if (model != null) {
                        list.add(model);
                        cs = null;
                        model = null;
                    }
                }
            }
        }
        return list;
    }
}
