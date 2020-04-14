package cn.minsin.jfx.excel.model;

import cn.minsin.core.override.AutoCloneable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/4/8 22:19
 */
@Getter
@Setter
@Accessors(chain = true)
public class RowParseRule implements AutoCloneable<SheetParseRule> {

    private int startRowIndex;

    private List<Integer> parseCellIndexes;

    public RowParseRule addCell(int cell) {
        if (parseCellIndexes == null) {
            parseCellIndexes = new ArrayList<>(10);
        }
        parseCellIndexes.add(cell);
        return this;
    }
}
