package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.dao.ToolTypeDao;
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
	public Collection<ToolType> getEntities() {
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}

	@Override
	public ToolType getByKey(String toolTypeName) {
		return MAP_CACHE.get(toolTypeName);
	}
}
