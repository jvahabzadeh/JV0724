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
	public Collection<Tool> getEntities() {
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}

	@Override
	public Tool getByKey(String toolCode) {
		return MAP_CACHE.get(toolCode);
	}
}
