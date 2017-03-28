package ${packageName}.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>${entityName} for ${tableName}</p><br />
 * ${tableComment}
 * @author ${author}
 * @Date ${createDate}
 */
 @TableName("${tableName}")
public class ${entityName} extends BaseEntity implements Serializable {

	@TableField(exist=false)
	private static final long serialVersionUID = 1L;
	
	<#list entityFieldList as column>
	/**${column.comment}*/
	<#if primaryKey??>
		<#if column.lowerPropertyName == primaryKey.lowerPropertyName>
	@TableId(value = "${column.columnName}", type = IdType.AUTO)
		<#else>
	@TableField(value = "${column.columnName}")
		</#if>
	<#else>
	@TableField(value = "${column.columnName}")
	</#if>
	private ${column.propertyType} ${column.lowerPropertyName};
	</#list>

	
	<#list entityFieldList as method>
	public void set${method.upperPropertyName}(${method.propertyType} ${method.lowerPropertyName}) {
		this.${method.lowerPropertyName} = ${method.lowerPropertyName};
	}
	
	public ${method.propertyType} get${method.upperPropertyName}() {
		return this.${method.lowerPropertyName};
	}
	</#list>
	

}
