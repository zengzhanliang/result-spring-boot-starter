package com.zeng.annotation;

import java.lang.annotation.*;

/**
 * 配置是否启用result stater,用于类或者方法
 * @author zengzhanliang
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonResultCovert {
}
