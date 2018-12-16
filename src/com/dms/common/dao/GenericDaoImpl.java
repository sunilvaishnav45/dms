package com.dms.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dms.resources.HibernateSessionFactory;


@SuppressWarnings("unchecked")
@Repository
@Transactional
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {

	@Autowired
	private HibernateSessionFactory hibernateSessionFactory;

	protected Class<? extends E> daoType;

	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		daoType = (Class<? extends E>) pt.getActualTypeArguments()[0];
	}

	public Session getSession() {
		return hibernateSessionFactory.getSession();
	}

	@Override
	public K save(E entity) {
		return (K) this.getSession().save(entity);
	}

	@Override
	public void update(E entity) {
		this.getSession().update(entity);
	}

	@Override
	public void delete(E entity) {
		this.getSession().delete(entity);
	}

	@Override
	public List<E> getAll() {
		return this.getSession().createCriteria(daoType).list();
	}

	@Override
	public E getById(K id) {
		return (E) this.getSession().get(daoType, id);
	}

}
