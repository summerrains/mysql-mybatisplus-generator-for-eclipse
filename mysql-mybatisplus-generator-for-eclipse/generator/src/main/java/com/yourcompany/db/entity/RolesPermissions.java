package com.yourcompany.db.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>RolesPermissions for sys_roles_permissions</p><br />
 * 角色权限表
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
 @TableName("sys_roles_permissions")
public class RolesPermissions extends BaseEntity implements Serializable {

	@TableField(exist=false)
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@TableId(value = "rp_id", type = IdType.AUTO)
	private java.lang.Long id;
	/**角色ID*/
	@TableId(value = "r_id", type = IdType.AUTO)
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
