package com.yourcompany.db.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>PermissionsResources for sys_permissions_resources</p><br />
 * 权限资源表
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
 @TableName("sys_permissions_resources")
public class PermissionsResources extends BaseEntity implements Serializable {

	@TableField(exist=false)
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@TableId(value = "pr_id", type = IdType.AUTO)
	private java.lang.Long id;
	/**资源ID*/
	@TableId(value = "res_id", type = IdType.AUTO)
	private java.lang.Long id;
	/**权限ID*/
	@TableId(value = "perm_id", type = IdType.AUTO)
	private java.lang.Long id;

	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	

}
