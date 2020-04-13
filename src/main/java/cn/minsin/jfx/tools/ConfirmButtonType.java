package cn.minsin.jfx.tools;

/**
 * @author: minton.zhang
 * @since: 2020/4/6 18:34
 */
public enum ConfirmButtonType {
    YES("YES", "是"),
    NO("NO", "否"),
    OK("OK", "确认"),
    CANCEL("OK", "取消"),
    ;

    private String key;

    private String name;

    ConfirmButtonType(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static ConfirmButtonType findConfirmTypeName(String name) {
        for (ConfirmButtonType value : ConfirmButtonType.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
