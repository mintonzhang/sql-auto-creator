package cn.minsin.jfx.tools;

import javafx.stage.FileChooser;

/**
 * @author: minton.zhang
 * @since: 2020/4/4 22:01
 */
public class ExtensionUtils {

    public static FileChooser.ExtensionFilter[] excel() {
        return new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("All Excel", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")};
    }

    public static FileChooser.ExtensionFilter[] sql() {
        return new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("数据库脚本", "*.sql")};
    }

}
