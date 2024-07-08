package com.jvprogdemo.dao;

import com.jvprogdemo.entity.ToolBrand;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ToolBrandDao extends AbstractDao<ToolBrand> {
	@Override
	public Map<String, ToolBrand> getEntityMap() {
		// normally this would be retrieved from a database
		Collection<ToolBrand> tmp = Set.of(new ToolBrand("Stihl"),
				new ToolBrand("Werner"),
				new ToolBrand("DeWalt"),
				new ToolBrand("Ridgid")
		);
		Map<String, ToolBrand> results = new HashMap<>();
		for (ToolBrand toolBrand : tmp) {
			results.put(toolBrand.getBrandName(), toolBrand);
		}
		return Collections.unmodifiableMap(results);
	}
}
