package com.jvprogdemo.service;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.entity.AbstractEntity;

import java.util.Collection;

public abstract class AbstractEntityService<T extends AbstractEntity> {
	protected final AbstractDao<T> dao;

	protected AbstractEntityService() {
		dao = getDao();
	}
	protected abstract AbstractDao<T> getDao();
	public abstract Collection<T> getEntities();
}
