package com.yourcompany.db.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>Manager for sys_manager</p><br />
 * 管理员表
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
 @TableName("sys_manager")
public class Manager extends BaseEntity implements Serializable {

	@TableField(exist=false)
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@TableId(value = "m_id", type = IdType.AUTO)
	private java.lang.Long id;
	/**真实姓名*/
	@TableField(value = "m_name")
	private java.lang.String name;
	/**昵称*/
	@TableField(value = "m_nickname")
	private java.lang.String nickname;
	/**登录名*/
	@TableField(value = "m_loginname")
	private java.lang.String loginname;
	/**密码*/
	@TableField(value = "m_password")
	private java.lang.String password;
	/**加密盐*/
	@TableField(value = "m_salt")
	private java.lang.String salt;
	/**添加时间*/
	@TableField(value = "m_create_date")
	private java.util.Date createDate;
	/**修改时间*/
	@TableField(value = "m_update_date")
	private java.util.Date updateDate;
	/**是否删除，0：未删除，1：已删除*/
	@TableField(value = "m_is_delete")
	private java.lang.Integer isDelete;
	/**账户状态，0：激活，1：禁用*/
	@TableField(value = "m_status")
	private java.lang.Integer status;
	/***/
	@TableField(value = "m_su_admin")
	private java.lang.Integer suAdmin;

	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}
	
	public java.lang.String getNickname() {
		return this.nickname;
	}
	public void setLoginname(java.lang.String loginname) {
		this.loginname = loginname;
	}
	
	public java.lang.String getLoginname() {
		return this.loginname;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setSalt(java.lang.String salt) {
		this.salt = salt;
	}
	
	public java.lang.String getSalt() {
		return this.salt;
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
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setSuAdmin(java.lang.Integer suAdmin) {
		this.suAdmin = suAdmin;
	}
	
	public java.lang.Integer getSuAdmin() {
		return this.suAdmin;
	}
	

}
