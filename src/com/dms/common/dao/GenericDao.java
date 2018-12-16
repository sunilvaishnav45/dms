package com.dms.common.dao;

import java.util.List;
/**
 * 
 * @author sunil
 * E is a class type<br>
 * K - Primary key type
 * @param <E>
 * @param <K>
 */
public interface GenericDao<E, K> {

	// Save the entity
	public K save(E entity);

	// Update entity
	public void update(E entity);

	// Delete entity
	public void delete(E entity);

	// Fetch all entity
	public List<E> getAll();

	// Get Entity by id
	public E getById(K id);

}