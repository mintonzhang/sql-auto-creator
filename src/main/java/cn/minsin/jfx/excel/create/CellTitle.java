package cn.minsin.jfx.excel.create;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: minton.zhang
 * @since: 2020/4/8 22:12
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellTitle {

    /**
     * cell列名
     *
     * @return
     */
    String value() default "无";

    /**
     * cell所在列
     * 暂未使用
     */
    @Deprecated
    int sort() default 0;

}
