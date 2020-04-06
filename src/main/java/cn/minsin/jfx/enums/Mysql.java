package cn.minsin.jfx.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: minton.zhang
 * @since: 2020/4/5 18:14
 */
public class Mysql {


    private static Map<String, String> charsets = new HashMap<>(4);

    static {
        charsets.put("UTF-8", "UTF8");
        charsets.put("UTF-16", "UTF16");
        charsets.put("UTF-32", "UTF32");
        charsets.put("UTF-8MB4", "UTF8MB4");
    }


    public static String findByName(String name) {
        return charsets.get(name);
    }

    public Set<String> allCharset() {
        return charsets.keySet();
    }
}
