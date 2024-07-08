package com.jvprogdemo.dao;

import com.jvprogdemo.entity.ToolType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ToolTypeDao extends AbstractDao<ToolType> {

	@Override
	public Map<String, ToolType> getEntityMap() {
		// normally this would be retrieved from a database
		Collection<ToolType> tmp = Set.of(new ToolType("Ladder", new BigDecimal("1.99"), true, true, false),
				new ToolType("Chainsaw", new BigDecimal("1.49"), true, false, true),
				new ToolType("Jackhammer", new BigDecimal("2.99"), true, false, false)
		);
		Map<String, ToolType> results = new HashMap<>();
		for (ToolType toolType : tmp) {
			results.put(toolType.getType(), toolType);
		}
		return Collections.unmodifiableMap(results);
	}
}
