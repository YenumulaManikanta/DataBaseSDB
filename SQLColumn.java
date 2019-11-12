package com.miscot.springmvc.dto;

public class SQLColumn {
	private String  Name;
	private boolean  AutoIncrement;
	private String  Type;
	private int  TypeCode;
	private String  TableName;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public boolean isAutoIncrement() {
		return AutoIncrement;
	}
	public void setAutoIncrement(boolean autoIncrement) {
		AutoIncrement = autoIncrement;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public int getTypeCode() {
		return TypeCode;
	}
	public void setTypeCode(int typeCode) {
		TypeCode = typeCode;
	}
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	
}
