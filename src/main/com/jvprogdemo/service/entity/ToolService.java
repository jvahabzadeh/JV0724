package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.ToolDao;
import com.jvprogdemo.models.entity.Tool;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ToolService extends AbstractEntityService<Tool> {
	protected static final Map<String, Tool> MAP_CACHE = new HashMap<>();

	@Override
	protected ToolDao initializeDao() {
		return new ToolDao();
	}

	@Override
	public Collection<Tool> getEntities(boolean forceCacheRefresh) {
		populateMapCacheIfNeeded(false);
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}

	private void populateMapCacheIfNeeded(boolean forceRefresh) {
		if (forceRefresh || MAP_CACHE.isEmpty()) {
			Collection<Tool> tools = dao.getEntities();
			MAP_CACHE.clear();
			for (Tool tool : tools) {
				MAP_CACHE.put(tool.getCode(), tool);
			}
		}
	}

	public Tool getToolByCode(String code) {
		return getToolByCode(code, false);
	}

	public Tool getToolByCode(String code, boolean forceCacheRefresh) {
		populateMapCacheIfNeeded(forceCacheRefresh);
		if (!MAP_CACHE.containsKey(code)) {
			throw new IllegalArgumentException("There is no tool with a Code matching: " + code);
		}
		return MAP_CACHE.get(code);
	}
}
