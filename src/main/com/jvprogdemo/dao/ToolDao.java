package com.jvprogdemo.dao;

import com.jvprogdemo.models.entity.Tool;

import java.util.Collection;
import java.util.Set;

public class ToolDao extends AbstractDao<Tool> {
	// Data that would normally be retrieved from a database
	private static final Collection<Tool> DATA_TABLE_OBJECTS = Set.of(
			new Tool("CHNS", "Chainsaw", "Stihl"),
			new Tool("LADW", "Ladder", "Werner"),
			new Tool("JAKD", "Jackhammer", "DeWalt")
	);

	@Override
	public Collection<Tool> getEntities() {
		return DATA_TABLE_OBJECTS;
	}
}
