package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.dao.ToolTypeDao;
import com.jvprogdemo.models.entity.Tool;
import com.jvprogdemo.models.entity.ToolType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ToolTypeService extends AbstractEntityService<ToolType> {
	private static final Map<String, ToolType> MAP_CACHE = new HashMap<>();

	@Override
	protected AbstractDao<ToolType> initializeDao() {
		return new ToolTypeDao();
	}

	@Override
	public Collection<ToolType> getEntities(boolean forceCacheRefresh) {
		populateMapCacheIfNeeded(forceCacheRefresh);
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}

	private void populateMapCacheIfNeeded(boolean forceRefresh) {
		if (forceRefresh || MAP_CACHE.isEmpty()) {
			Collection<ToolType> toolTypes = dao.getEntities();
			MAP_CACHE.clear();
			for (ToolType toolType : toolTypes) {
				MAP_CACHE.put(toolType.getTypeName().toLowerCase(), toolType);
			}
		}
	}

	public ToolType getToolTypeForTool(Tool tool) {
		return getToolTypeForTool(tool, false);
	}

	public ToolType getToolTypeForTool(Tool tool, boolean forceCacheRefresh) {
		populateMapCacheIfNeeded(forceCacheRefresh);
		// This error checking won't be necessary if the DB foreign keys are set up correctly.
		String typeName = tool.getTypeName().toLowerCase();
		if (!MAP_CACHE.containsKey(typeName)) {
			throw new IllegalArgumentException("Internal data error: The tool with code " + tool.getCode() +
					" has a Type Name of " + tool.getTypeName() + ", but no such Type exists with that name.");
		}
		return MAP_CACHE.get(typeName);
	}
}
