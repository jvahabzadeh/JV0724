package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.models.entity.AbstractEntity;

import java.util.Collection;

public abstract class AbstractEntityService<T extends AbstractEntity> {
	protected final AbstractDao<T> dao;

	protected AbstractEntityService() {
		dao = initializeDao();
	}
	protected abstract AbstractDao<T> initializeDao();
	public abstract Collection<T> getEntities(boolean forceCacheRefresh);
	public final Collection<T> getEntities() {
		return getEntities(false);
	}
}
