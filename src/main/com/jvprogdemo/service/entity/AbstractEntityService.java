package com.jvprogdemo.service.entity;

import com.jvprogdemo.dao.AbstractDao;
import com.jvprogdemo.models.entity.AbstractEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEntityService<T extends AbstractEntity> {
	protected final AbstractDao<T> DAO;
	protected final Map<String, T> MAP_CACHE;

	protected AbstractEntityService() {
		DAO = initializeDao();
		MAP_CACHE = initializeDataMap();
	}
	protected abstract AbstractDao<T> initializeDao();
	public abstract Collection<T> getEntities();
	public abstract T getByKey(String key);

	private Map<String, T> initializeDataMap() {
		Collection<T> entities = DAO.getEntities();
		Map<String, T> map = new HashMap<>();
		for (T entity : entities) {
			map.put(entity.getKey(), entity);
		}
		return map;
	}
}
