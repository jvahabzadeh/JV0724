package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.dao.ToolBrandDao;
import com.jvprogdemo.models.entity.ToolBrand;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ToolBrandService extends AbstractEntityService<ToolBrand> {
	private static final Map<String, ToolBrand> MAP_CACHE = new HashMap<>();

	@Override
	protected AbstractDao<ToolBrand> initializeDao() {
		return new ToolBrandDao();
	}

	@Override
	public Collection<ToolBrand> getEntities() {
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}

	@Override
	public ToolBrand getByKey(String brandName) {
		return MAP_CACHE.get(brandName);
	}
}
