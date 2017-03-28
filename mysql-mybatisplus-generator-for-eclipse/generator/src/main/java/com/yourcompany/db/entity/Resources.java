package com.yourcompany.db.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>Resources for sys_resources</p><br />
 * 资源表
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
 @TableName("sys_resources")
public class Resources extends BaseEntity implements Serializable {

	@TableField(exist=false)
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@TableId(value = "res_id", type = IdType.AUTO)
	private java.lang.Long id;
	/**增加时间*/
	@TableField(value = "res_create_date")
	private java.util.Date createDate;
	/**修改时间*/
	@TableField(value = "res_update_date")
	private java.util.Date updateDate;
	/**是否删除*/
	@TableField(value = "res_is_delete")
	private java.lang.Integer isDelete;
	/**资源名称*/
	@TableField(value = "res_name")
	private java.lang.String name;
	/**父ID*/
	@TableField(value = "res_parent")
	private java.lang.Long parent;
	/**资源地址*/
	@TableField(value = "res_url")
	private java.lang.String url;
	/***/
	@TableField(value = "res_sort")
	private java.lang.Long sort;

	
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
	public void setParent(java.lang.Long parent) {
		this.parent = parent;
	}
	
	public java.lang.Long getParent() {
		return this.parent;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setSort(java.lang.Long sort) {
		this.sort = sort;
	}
	
	public java.lang.Long getSort() {
		return this.sort;
	}
	

}
