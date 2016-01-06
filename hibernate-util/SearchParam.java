package com.joiniot.lock.util;

public class SearchParam {
	private String type;
	private String propertyName;
	private Object propertyValue;
	
	public SearchParam(){
	};
	public SearchParam(String type,String propertyName,Object propertyValue){
		this.type = type;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Object getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
	
}
