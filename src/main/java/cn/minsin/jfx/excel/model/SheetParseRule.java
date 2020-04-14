package cn.minsin.jfx.excel.model;

import cn.minsin.core.override.AutoCloneable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/4/8 22:15
 */
@Getter
@Setter
@Accessors(chain = true)
public class SheetParseRule implements AutoCloneable<SheetParseRule> {

    public int sheetIndex;

    public RowParseRule parseRowRule;

    public static SheetParseRule create(int sheetIndex, int startRowIndex, List<Integer> cellIndexes) {
        return new SheetParseRule().setParseRowRule(new RowParseRule().setParseCellIndexes(cellIndexes).setStartRowIndex(startRowIndex)).setSheetIndex(sheetIndex);
    }

    /**
     * 复制一份{@linkplain SheetParseRule} 并且重新设置sheetIndex
     * @param sheetIndex
     * @return
     */
    public SheetParseRule clone(int sheetIndex) {
        return this.deepClone().setSheetIndex(sheetIndex);
    }
}
