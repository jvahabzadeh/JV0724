package com.jvprogdemo.dao;

import com.jvprogdemo.entity.AbstractEntity;

import java.util.Map;

public abstract class AbstractDao<T extends AbstractEntity> {
	// I'm assuming that tool, tool type, and tool brand information would be pulled from a database that exists
	// somewhat as follows:
	// (note that the assignment of 50 for the Varchar types is just completely arbitrary)
	// TABLE: Tool
	//      ToolCode	Varchar(50)		not-null	PK
	//      ToolType	Varchar(50)		not-null	FK to ToolType Table
	//      ToolBrand	Varchar(50)		not-null	FK to ToolBrand Table
	//
	// TABLE: ToolType - an enum-type of table
	//      ToolType		Varchar(50)		not-null	PK
	//      DailyCharge		Decimal(10,2)	not-null
	//      WeekdayCharge	Bool			not-null
	//		WeekendCharge	Bool			not-null
	//		HolidayCharge	Bool			not-null
	//
	// TABLE: ToolBrand - an enum-type of table
	//      ToolBrand   Varchar(50)     not-null        PK
	//		(other columns related to the brand)

	public abstract Map<String, T> getEntityMap();
}
