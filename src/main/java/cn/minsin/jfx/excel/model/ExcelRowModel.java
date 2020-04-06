package cn.minsin.jfx.excel.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ExcelRowModel{

    /**
     *
     */
    private static final long serialVersionUID = 7096662531755822709L;

    /**
     * 行下标
     */
    private int rowIndex;

    /**
     * key为列下标 value为改该列的值
     */
    private List<ExcelCellModel> cells = new ArrayList<>(10);

    /**
     * 赋值
     */
    public void addCells(ExcelCellModel cell) {
        cells.add(cell);
    }

}
