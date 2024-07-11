package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.ToolDao;
import com.jvprogdemo.models.entity.Tool;

import java.util.Collection;
import java.util.Collections;

public class ToolService extends AbstractEntityService<Tool> {
	@Override
	protected ToolDao initializeDao() {
		return new ToolDao();
	}

	@Override
	public Collection<Tool> getEntities() {
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}
}
