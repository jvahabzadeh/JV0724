package com.jvprogdemo.service;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.dao.ToolTypeDao;
import com.jvprogdemo.entity.Tool;
import com.jvprogdemo.entity.ToolType;
import exceptions.NoSuchToolTypeException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ToolTypeService extends AbstractEntityService<ToolType> {
	private static Map<String, ToolType> TOOL_TYPES_MAP = null;

	@Override
	protected AbstractDao<ToolType> getDao() {
		return new ToolTypeDao();
	}

	@Override
	public Collection<ToolType> getEntities() {
		return dao.getEntities();
	}

	public ToolType getToolType(Tool tool) throws NoSuchToolTypeException {
		return getToolType(tool, false);
	}

	public ToolType getToolType(Tool tool, boolean forceToolTypeRefresh) throws NoSuchToolTypeException {
		if (forceToolTypeRefresh || TOOL_TYPES_MAP == null) {
			Collection<ToolType> tmp = new ToolTypeDao().getEntities();
			TOOL_TYPES_MAP = new HashMap<>();
			for (ToolType toolType : tmp) {
				TOOL_TYPES_MAP.put(toolType.getType().toLowerCase(), toolType);
			}
		}
		// This error checking won't be necessary if the DB foreign keys are set up correctly.
		String type = tool.getType().toLowerCase();
		if (!TOOL_TYPES_MAP.containsKey(type)) {
			throw new NoSuchToolTypeException("Internal data error: The tool with code " + tool.getCode() +
					" has a type of " + tool.getType() + ", but no such type exists.");
		}
		return TOOL_TYPES_MAP.get(type);
	}
}
