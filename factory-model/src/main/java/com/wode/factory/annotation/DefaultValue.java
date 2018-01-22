/**
 * 
 */
package com.wode.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author haisheng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})  
public @interface DefaultValue {
	
	public String StringValue() default "";
	
	public long longValue() default 1L;
	
	public String dateValue() default "";//now
	
}
