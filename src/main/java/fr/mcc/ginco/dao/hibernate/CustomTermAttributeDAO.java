/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mcc.ginco.beans.CustomTermAttribute;
import fr.mcc.ginco.beans.ThesaurusTerm;
import fr.mcc.ginco.beans.generic.GenericCustomAttributeType;
import fr.mcc.ginco.dao.ICustomTermAttributeDAO;

/**
 *
 * @author Diego_MF
 */
@Repository
public class CustomTermAttributeDAO extends
		GenericHibernateDAO<CustomTermAttribute, String> implements
		ICustomTermAttributeDAO {
	public CustomTermAttributeDAO() {
		super(CustomTermAttribute.class);
	}

	@Override
	public List<CustomTermAttribute> getAttributesByEntity(ThesaurusTerm entity) {
		Criteria criteria = getCurrentSession().createCriteria(
				CustomTermAttribute.class).add(
				Restrictions.eq("entity.identifier", entity.getIdentifier()));
		return criteria.list();
	}

	@Override
	public CustomTermAttribute getAttributeByType(ThesaurusTerm entity,
			GenericCustomAttributeType type) {
		Criteria criteria = getCurrentSession()
				.createCriteria(CustomTermAttribute.class)
				.add(Restrictions.eq("entity.identifier",
						entity.getIdentifier()))
				.add(Restrictions.eq("type.identifier", type.getIdentifier()));
		List<CustomTermAttribute> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
