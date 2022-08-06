/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import fr.mcc.ginco.beans.CustomConceptAttribute;
import fr.mcc.ginco.beans.ThesaurusConcept;
import fr.mcc.ginco.beans.generic.GenericCustomAttributeType;
import fr.mcc.ginco.dao.ICustomConceptAttributeDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Diego_MF
 */
@Repository
public class CustomConceptAttributeDAO extends GenericHibernateDAO<CustomConceptAttribute, String>
		implements ICustomConceptAttributeDAO {

	public CustomConceptAttributeDAO() {
		super(CustomConceptAttribute.class);
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.generic.IGenericCustomAttribute#getAttributesByEntity(java.lang.Object)
	 */
	@Override
	public List<CustomConceptAttribute> getAttributesByEntity(ThesaurusConcept entity) {
		Criteria criteria = getCurrentSession().createCriteria(CustomConceptAttribute.class)
				.add(Restrictions.eq("entity.identifier", entity.getIdentifier()));
		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.generic.IGenericCustomAttribute#getAttributeByType(java.lang.Object,
	 * fr.mcc.ginco.beans.generic.GenericCustomAttributeType)
	 */
	@Override
	public CustomConceptAttribute getAttributeByType(ThesaurusConcept entity,
	                                                 GenericCustomAttributeType type) {
		Criteria criteria = getCurrentSession().createCriteria(CustomConceptAttribute.class)
				.add(Restrictions.eq("entity.identifier", entity.getIdentifier()))
				.add(Restrictions.eq("type.identifier", type.getIdentifier()));
		List<CustomConceptAttribute> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
