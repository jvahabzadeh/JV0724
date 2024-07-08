package com.jvprogdemo.dao;

import com.jvprogdemo.entity.ToolBrand;

import java.util.Collection;
import java.util.Set;

public class ToolBrandDao extends AbstractDao<ToolBrand> {
	// Data that would normally be retrieved from a database
	private static final Collection<ToolBrand> DATA_TABLE_OBJECTS = Set.of(
			new ToolBrand("Stihl"),
			new ToolBrand("Werner"),
			new ToolBrand("DeWalt"),
			new ToolBrand("Ridgid")
	);

	@Override
	public Collection<ToolBrand> getEntities() {
		return DATA_TABLE_OBJECTS;
	}
}
