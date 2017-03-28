package com.yourcompany.db.mybatis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 
 * 注解，用于标识MyBatis的Dao,方便
 * {@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 * 
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisRepository {
	String value() default "";
}
