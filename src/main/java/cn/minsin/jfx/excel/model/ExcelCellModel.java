package cn.minsin.jfx.excel.model;

import cn.minsin.jfx.excel.tools.ExcelUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.StringJoiner;

/**
 * @author: minton.zhang
 * @since: 2020/4/1 13:56
 */
@Getter
@Setter
public class ExcelCellModel {

    private int cellIndex;

    private Cell cell;

    private CellType cellType;

    public ExcelCellModel(int cellIndex, Cell cell) {
        this.cellIndex = cellIndex;
        this.cell = cell;
        cellType = cell.getCellType();
    }

    public String getCellValue() {
        return ExcelUtil.getCellRealValue(cell);
    }

    /**
     * 获取cell的值
     *
     * @return
     */
    public String getCell() {
        return this.getCellValue();
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]")
                .add("cellIndex=" + cellIndex)
                .add("cell=" + getCellValue())
                .toString();
    }
}
