package cn.minsin.jfx.excel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/4/1 14:13
 */
@Getter
@Setter
public class ExcelSheetModel {

    private String sheetName;

    private List<ExcelRowModel> rows = new ArrayList<>(10);

    /**
     * 赋值
     */
    public void addRows(ExcelRowModel model) {
        rows.add(model);
    }
}
