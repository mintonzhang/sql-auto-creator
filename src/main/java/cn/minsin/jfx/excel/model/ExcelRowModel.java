package cn.minsin.jfx.excel.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    protected Map<Integer, ExcelCellModel> cache;

    /**
     * 赋值
     */
    public void addCells(ExcelCellModel cell) {
        this.cells.add(cell);
    }

    /**
     *
     * @param index Excel中Cell的下标
     * @return 对应Cell的value
     */
    public String getCell(int index) {
        if (this.cache == null) {
            this.cache = new ConcurrentHashMap<>(cells.size());
            for (ExcelCellModel cell : cells) {
                this.cache.put(cell.getCellIndex(), cell);
            }
        }
        boolean b = this.cache.containsKey(index);
        if(!b){
            return "";
        }
        return this.cache.get(index).getCellValue();
    }

}
