package com.libs.golomb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tomer on 09/10/2016.
 */
@Target(ElementType.METHOD) //can use in method only.
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemViewTypeAnnotation {
    int itemViewType();
}
