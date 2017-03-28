package net.summerrains.generator;

import java.io.Serializable;

/**
 * 主键
 * @author summerrains
 *
 */
public class PrimaryKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columnName;
	private String lowerPropertyName;
	private String type;
	private String propertyType;
	private boolean fieldPrefix;
	
	public PrimaryKey(boolean fieldPrefix) {
		this.fieldPrefix = fieldPrefix;
	}
	public boolean isFieldPrefix() {
		return fieldPrefix;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
		this.lowerPropertyName = EntityField.getLowerPropertyName(columnName,isFieldPrefix());
	}
	public String getLowerPropertyName() {
		return lowerPropertyName;
	}
	public void setLowerPropertyName(String lowerPropertyName) {
		this.lowerPropertyName = lowerPropertyName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		this.propertyType = EntityField.mysqlProcessType(type);
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	
}
