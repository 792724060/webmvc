package cn.gin.webmvc.anno;

import org.apache.commons.lang.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * <p>Identify the current mapping's name attribute.</p>
     */
    String name() default StringUtils.EMPTY;

    /**
     * <p>This mapping linked to the URL-Mapping.</p>
     */
    String[] value() default {};

    /**
     * <p>The request methods are supported by this mapping.</p>
     */
    RequestMethod[] method() default {};
}