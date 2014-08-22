package com.xiaoluo.entities;

import java.io.Serializable;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月19日 - 下午1:26:49
 */
public class ModuleEntity implements Serializable {
	private String groupId;
	private String moduleName;
	private String iconUrl;
	private String point;
	private String description;
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public void setModulename(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getIconUrl() {
		return iconUrl;
	}
	
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public String getPoint() {
		return point;
	}
	
	public void setPoint(String point) {
		this.point = point;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
