package cn.minsin.jfx.excel.model;

import cn.minsin.core.exception.MutilsException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: minton.zhang
 * @since: 2020/4/1 13:50
 */
@ToString
public class ExcelParseResultModel {


    @Getter
    @Setter
    private List<ExcelSheetModel> rows = new ArrayList<>(3);

    private Map<String, ExcelSheetModel> indexMap = new HashMap<>(3);

    /**
     * 赋值
     */
    public void addSheet(ExcelSheetModel model) {
        rows.add(model);
        indexMap.put(model.getSheetName(), model);
    }

    public ExcelSheetModel findSheet(String sheetName) {
        boolean b = indexMap.containsKey(sheetName);
        // 抛出异常,触发回滚
        MutilsException.throwException(!b, "sheetName为'"+sheetName+"'不存在");
        return indexMap.get(sheetName);
    }

}
