package ${packageName}.mybatis;

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
 * @author ${author}
 * @Date ${createDate}
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisRepository {
	String value() default "";
}
