package com.yourcompany.db.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>Permissions for sys_permissions</p><br />
 * 权限表
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
 @TableName("sys_permissions")
public class Permissions extends BaseEntity implements Serializable {

	@TableField(exist=false)
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@TableId(value = "perm_id", type = IdType.AUTO)
	private java.lang.Long id;
	/**增加时间*/
	@TableField(value = "perm_create_date")
	private java.util.Date createDate;
	/**修改时间*/
	@TableField(value = "perm_update_date")
	private java.util.Date updateDate;
	/**是否删除*/
	@TableField(value = "perm_is_delete")
	private java.lang.Integer isDelete;
	/**权限名称*/
	@TableField(value = "perm_name")
	private java.lang.String name;
	/**权限值*/
	@TableField(value = "perm_value")
	private java.lang.String value;
	/***/
	@TableField(value = "perm_remark")
	private java.lang.String remark;

	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public java.lang.String getValue() {
		return this.value;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	

}
