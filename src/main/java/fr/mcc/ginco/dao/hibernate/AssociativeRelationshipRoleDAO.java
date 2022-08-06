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

import fr.mcc.ginco.beans.AssociativeRelationshipRole;
import fr.mcc.ginco.dao.IAssociativeRelationshipRoleDAO;
import fr.mcc.ginco.exceptions.BusinessException;

/**
 *
 * @author Diego_MF
 */

@Repository
public class AssociativeRelationshipRoleDAO extends GenericHibernateDAO<AssociativeRelationshipRole, String> implements IAssociativeRelationshipRoleDAO {

	public AssociativeRelationshipRoleDAO() {
		super(AssociativeRelationshipRole.class);
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.IAssociativeRelationshipRoleDAO#getDefaultAssociativeRelationshipRole()
	 */
	@Override
	public AssociativeRelationshipRole getDefaultAssociativeRelationshipRole() {
		List<AssociativeRelationshipRole> defaultRoles = getCurrentSession().createCriteria(AssociativeRelationshipRole.class)
				.setMaxResults(1)
				.add(Restrictions.eq("defaultRole", Boolean.TRUE))
				.list();
		if (defaultRoles != null && defaultRoles.size() == 1) {
			return defaultRoles.get(0);
		} else {
			throw new BusinessException("No defaultRole defined", "no-default-role-defined");
		}
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.IAssociativeRelationshipRoleDAO#getBySkosLabel(java.lang.String)
	 */
	@Override
	public AssociativeRelationshipRole getBySkosLabel(String skosLabel) {
		Criteria criteria = getCurrentSession().createCriteria(
				AssociativeRelationshipRole.class);
		criteria.add(Restrictions.eq("skosLabel", skosLabel));
		List<AssociativeRelationshipRole> foundroles = criteria.list();
		if (foundroles.size() > 0) {
			return foundroles.get(0);
		}
		return null;
	}


}