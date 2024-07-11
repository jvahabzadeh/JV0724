package com.jvprogdemo.dao;

import com.jvprogdemo.models.entity.ToolType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

public class ToolTypeDao extends AbstractDao<ToolType> {
	// Data that would normally be retrieved from a database
	private static final Collection<ToolType> DATA_TABLE_OBJECTS = Set.of(
			new ToolType("Ladder", new BigDecimal("1.99"), true, true, false),
			new ToolType("Chainsaw", new BigDecimal("1.49"), true, false, true),
			new ToolType("Jackhammer", new BigDecimal("2.99"), true, false, false)
	);
	@Override
	public Collection<ToolType> getEntities() {
		return DATA_TABLE_OBJECTS;
	}
}
