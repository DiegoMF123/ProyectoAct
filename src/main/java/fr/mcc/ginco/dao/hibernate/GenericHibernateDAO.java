/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import fr.mcc.ginco.beans.ExternalThesaurus;
import fr.mcc.ginco.beans.ThesaurusTerm;
import fr.mcc.ginco.dao.IGenericDAO;

/**
 *
 * @author Diego_MF
 */
public class GenericHibernateDAO<T, ID extends Serializable> implements IGenericDAO<T, ID> {

	private final Class<T> persistentClass;

	@Inject
	@Named("gincoSessionFactory")
	private SessionFactory sessionFactory;

	public GenericHibernateDAO(Class<T> clazz) {
		this.persistentClass = clazz;
	}

	@Override
	public final T loadById(ID id) {
		return (T) getCurrentSession().load(persistentClass, id);
	}

	@Override
	public final T getById(ID id) {
		return (T) getCurrentSession().get(persistentClass, id);
	}
	
	@Override
	public final List<T> getByExternalId(String externalId) {
		Criteria criteria = getCurrentSession().createCriteria(ExternalThesaurus.class, "et").add(Restrictions.eq("et.externalId", externalId));
		return criteria.list();
	}

	@Override
	public final List<T> findAll() {
		return getCurrentSession().createCriteria(persistentClass).list();
	}

	@Override
	public final List<T> findAll(String sortColumn, SortingTypes order) {
		if (order.asc.equals(order)) {
			return getCurrentSession().createCriteria(persistentClass).addOrder(Order.asc(sortColumn)).list();
		} else {
			return getCurrentSession().createCriteria(persistentClass).addOrder(Order.desc(sortColumn)).list();
		}
	}

	@Override
	public final Long count() {
		return (Long) getCurrentSession().createCriteria(persistentClass).setProjection(Projections.rowCount()).list().get(0);
	}

	@Override
	public final T makePersistent(T entity) {
		getCurrentSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		return makePersistent(entity);
	}

	@Override
	public final T delete(T entity) {
		this.getCurrentSession().delete(entity);
		return entity;
	}

	@Override
	public void flush() {
		this.getCurrentSession().flush();
	}

	public final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public final SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}