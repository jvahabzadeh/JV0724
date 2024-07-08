package com.jvprogdemo.dao;

import com.jvprogdemo.entity.Tool;
import com.jvprogdemo.entity.ToolBrand;
import com.jvprogdemo.entity.ToolType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ToolDao extends AbstractDao<Tool> {
	private static Map<String, ToolBrand> TOOL_BRANDS = null;
	private static Map<String, ToolType> TOOL_TYPES = null;

	@Override
	public Map<String, Tool> getEntityMap() {
		// In a real-world scenario, this would be pulled from the DB directly.  The cross-check routines would not
		// be needed if the DB has the foreign keys set up correctly.
		Collection<Tool> tmp = Set.of(createToolWithCrossCheck("CHNS", "Chainsaw", "Stihl"),
				createToolWithCrossCheck("LADW", "Ladder", "Werner"),
				createToolWithCrossCheck("JAKD", "Jackhammer", "DeWalt"));
		Map<String, Tool> results = new HashMap<>();
		for (Tool tool : tmp) {
			results.put(tool.getToolCode(), tool);
		}
		return Collections.unmodifiableMap(results);
	}

	private Tool createToolWithCrossCheck(String toolCode, String toolType, String toolBrand) {
		if (TOOL_BRANDS == null || TOOL_TYPES == null) {
			refreshToolAndBrandData();
		}
		return new Tool(toolCode, TOOL_TYPES.get(toolType), TOOL_BRANDS.get(toolBrand));
	}

	private void refreshToolAndBrandData() {
		ToolBrandDao toolBrandDao = new ToolBrandDao(); // with apologies to Marlon BrandDao
		ToolTypeDao toolTypeDao = new ToolTypeDao();
		TOOL_BRANDS = toolBrandDao.getEntityMap();
		TOOL_TYPES = toolTypeDao.getEntityMap();
	}
}
