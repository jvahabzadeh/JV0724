package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.dao.ToolBrandDao;
import com.jvprogdemo.models.entity.Tool;
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
	public Collection<ToolBrand> getEntities(boolean forceCacheRefresh) {
		populateMapCacheIfNeeded(forceCacheRefresh);
		return Collections.unmodifiableCollection(MAP_CACHE.values());
	}

	private void populateMapCacheIfNeeded(boolean forceRefresh) {
		if (forceRefresh || MAP_CACHE.isEmpty()) {
			Collection<ToolBrand> toolBrands = dao.getEntities();
			MAP_CACHE.clear();
			for (ToolBrand toolBrand : toolBrands) {
				MAP_CACHE.put(toolBrand.getBrandName().toLowerCase(), toolBrand);
			}
		}
	}

	public ToolBrand getToolBrandForTool(Tool tool) {
		return getToolBrandForTool(tool, false);
	}

	public ToolBrand getToolBrandForTool(Tool tool, boolean forceCacheRefresh) {
		populateMapCacheIfNeeded(forceCacheRefresh);
		// This error checking won't be necessary if the DB foreign keys are set up correctly.
		String brandName = tool.getTypeName().toLowerCase();
		if (!MAP_CACHE.containsKey(brandName)) {
			throw new IllegalArgumentException("Internal data error: The tool with code " + tool.getCode() +
					" has a brand name of " + tool.getBrandName() + ", but no such Brand exists with that name.");
		}
		return MAP_CACHE.get(brandName);
	}
}
