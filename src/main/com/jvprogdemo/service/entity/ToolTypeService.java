package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.dao.ToolTypeDao;
import com.jvprogdemo.models.entity.ToolType;

import java.util.Collection;
import java.util.Collections;

public class ToolTypeService extends AbstractEntityService<ToolType> {
	@Override
	protected AbstractDao<ToolType> initializeDao() {
		return new ToolTypeDao();
	}

	@Override
	public Collection<ToolType> getEntities() {
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}
}
