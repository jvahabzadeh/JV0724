package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.dao.ToolBrandDao;
import com.jvprogdemo.models.entity.ToolBrand;

import java.util.Collection;
import java.util.Collections;

public class ToolBrandService extends AbstractEntityService<ToolBrand> {
	@Override
	protected AbstractDao<ToolBrand> initializeDao() {
		return new ToolBrandDao();
	}

	@Override
	public Collection<ToolBrand> getEntities() {
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}
}
