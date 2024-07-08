package com.jvprogdemo.service;

import com.jvprogdemo.dao.ToolDao;
import com.jvprogdemo.entity.Tool;

import java.util.Collection;

public class ToolService extends AbstractEntityService<Tool> {

	@Override
	protected ToolDao getDao() {
		return new ToolDao();
	}

	@Override
	public Collection<Tool> getEntities() {
		return dao.getEntities();
	}
}
