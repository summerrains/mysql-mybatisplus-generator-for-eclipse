package net.summerrains.generator;

import java.io.Serializable;

public class EntityField implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columnName;
	private String lowerPropertyName;
	private String upperPropertyName;
	private String type;
	private String propertyType;
	private String comment;
	private boolean fieldPrefix;
	
	public EntityField(boolean fieldPrefix) {
		this.fieldPrefix = fieldPrefix;
	}
	public boolean isFieldPrefix() {
		return fieldPrefix;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		this.propertyType = mysqlProcessType(type);
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
		this.lowerPropertyName = getLowerPropertyName(columnName,isFieldPrefix());
		this.upperPropertyName = getUpperPropertyName(columnName,isFieldPrefix());
	}
	public String getUpperPropertyName() {
		return upperPropertyName;
	}
	
	public String getLowerPropertyName() {
		return lowerPropertyName;
	}
	public void setLowerPropertyName(String lowerPropertyName) {
		this.lowerPropertyName = lowerPropertyName;
	}
	public void setUpperPropertyName(String upperPropertyName) {
		this.upperPropertyName = upperPropertyName;
	}
	private static String getUpperPropertyName(String columnName,boolean isFieldPrefix) {
		StringBuilder sb = new StringBuilder();
		if (columnName.contains("_")) {
			String[] tables = columnName.split("_");
			int l = tables.length;
			int s = 0;
			if(isFieldPrefix) {
				s = 1;
			}
			for (int i = s; i < l; i++) {
				String temp = tables[i].trim();
				if(temp.length() > 1) {
					sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1).toLowerCase());
				} else {
					sb.append(temp.toUpperCase());
				}
			}
		} else {
			if(columnName.length() > 1) {
				sb.append(columnName.substring(0, 1).toUpperCase()).append(columnName.substring(1).toLowerCase());
			} else {
				sb.append(columnName.toUpperCase());
			}
		}
		return sb.toString();
	}
	protected static String getLowerPropertyName(String columnName,boolean isFieldPrefix) {
		String upperPropertyName = getUpperPropertyName(columnName,isFieldPrefix);
		StringBuilder sb = new StringBuilder();
		if(upperPropertyName.length() > 1) {
			sb.append(upperPropertyName.substring(0, 1).toLowerCase()).append(upperPropertyName.substring(1));
		} else {
			sb.append(upperPropertyName.toLowerCase());
		}
		return sb.toString();
	}
	
	/**
	 * MYSQL字段类型转换
	 *
	 * @param type
	 *            字段类型
	 * @return
	 */
	protected static String mysqlProcessType(String type) {
		String t = type.toLowerCase();
		if (t.contains("char")) {
			return "java.lang.String";
		} else if (t.contains("bigint")) {
			return "java.lang.Long";
		} else if (t.contains("int")) {
			return "java.lang.Integer";
		} else if (t.contains("date") || t.contains("timestamp")) {
			return "java.util.Date";
		} else if (t.contains("text")) {
			return "java.lang.String";
		} else if (t.contains("bit")) {
			return "java.lang.Boolean";
		} else if (t.contains("decimal")) {
			return "java.math.BigDecimal";
		} else if (t.contains("blob")) {
			return "byte[]";
		} else if (t.contains("float")) {
			return "java.lang.Float";
		} else if (t.contains("double")) {
			return "java.lang.Double";
		} else if (t.contains("json") || t.contains("enum")) {
			return "java.lang.String";
		}
		return null;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
}
