package com.yourcompany.db.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>ManagerRoles for sys_manager_roles</p><br />
 * 用户角色表
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
 @TableName("sys_manager_roles")
public class ManagerRoles extends BaseEntity implements Serializable {

	@TableField(exist=false)
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@TableId(value = "mr_id", type = IdType.AUTO)
	private java.lang.Long id;
	/**用户ID*/
	@TableId(value = "m_id", type = IdType.AUTO)
	private java.lang.Long id;
	/**角色ID*/
	@TableId(value = "r_id", type = IdType.AUTO)
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
