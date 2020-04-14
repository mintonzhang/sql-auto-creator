package cn.minsin.jfx.excel.model;

import cn.minsin.core.exception.MutilsException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: minton.zhang
 * @since: 2020/4/1 13:50
 */
@ToString
public class ExcelParseResultModel {


    @Getter
    @Setter
    private List<ExcelSheetModel> sheets = new ArrayList<>(3);

    protected Map<String, ExcelSheetModel> cache;

    protected static String INDEX_SUFFIX = "$INDEX_";

    protected static String NAME_SUFFIX = "$NAME_";

    /**
     * 赋值
     */
    public void addSheet(ExcelSheetModel model) {
        this.sheets.add(model);
    }

    protected void initCache(){
        if (this.cache == null) {
            this.cache = new ConcurrentHashMap<>(sheets.size());
            for (ExcelSheetModel cell : sheets) {
                this.cache.put(INDEX_SUFFIX.concat(String.valueOf(cell.getSheetIndex())), cell);
                this.cache.put(NAME_SUFFIX.concat(cell.getSheetName()), cell);
            }
        }
    }

    public ExcelSheetModel findSheet(String sheetName) {
        this.initCache();
        String concat = NAME_SUFFIX.concat(sheetName);
        boolean b = cache.containsKey(concat);
        // 抛出异常,触发回滚
        MutilsException.throwException(!b, "sheetName为'" + sheetName + "'不存在");
        return this.cache.get(concat);
    }

    public ExcelSheetModel findSheet(int sheetIndex) {
        this.initCache();
        String concat = INDEX_SUFFIX.concat(String.valueOf(sheetIndex));
        boolean b = cache.containsKey(concat);
        // 抛出异常,触发回滚
        MutilsException.throwException(!b, "sheetIndex为'" + sheetIndex + "'不存在");
        return this.cache.get(concat);
    }

}
