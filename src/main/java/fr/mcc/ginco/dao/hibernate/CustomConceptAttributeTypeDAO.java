/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import fr.mcc.ginco.beans.CustomConceptAttributeType;
import fr.mcc.ginco.beans.Thesaurus;
import fr.mcc.ginco.dao.ICustomConceptAttributeTypeDAO;
import fr.mcc.ginco.exceptions.BusinessException;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Diego_MF
 */
@Repository
public class CustomConceptAttributeTypeDAO extends
		GenericHibernateDAO<CustomConceptAttributeType, Integer> implements ICustomConceptAttributeTypeDAO {

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.generic.IGenericCustomAttributeType#getAttributesByThesaurus(fr.mcc.ginco.beans.Thesaurus)
	 */
	@Override
	public List<CustomConceptAttributeType> getAttributesByThesaurus(Thesaurus thesaurus) {
		Criteria criteria = getCurrentSession().createCriteria(CustomConceptAttributeType.class)
				.add(Restrictions.eq("thesaurus.identifier", thesaurus.getIdentifier()));
		return (List<CustomConceptAttributeType>) criteria.list();
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.generic.IGenericCustomAttributeType#getAttributeByCode(fr.mcc.ginco.beans.Thesaurus, java.lang.String)
	 */
	@Override
	public CustomConceptAttributeType getAttributeByCode(Thesaurus thesaurus, String code) {
		Criteria criteria = getCurrentSession().createCriteria(CustomConceptAttributeType.class)
				.add(Restrictions.eq("thesaurus.identifier", thesaurus.getIdentifier()))
				.add(Restrictions.eq("code", code));
		List objList = criteria.list();
		if (objList.size() > 0) {
			return (CustomConceptAttributeType) objList.get(0);
		}
		return null;

	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.generic.IGenericCustomAttributeType#getAttributeByValue(fr.mcc.ginco.beans.Thesaurus, java.lang.String)
	 */
	@Override
	public CustomConceptAttributeType getAttributeByValue(Thesaurus thesaurus, String value) {
		Criteria criteria = getCurrentSession().createCriteria(CustomConceptAttributeType.class)
				.add(Restrictions.eq("thesaurus.identifier", thesaurus.getIdentifier()))
				.add(Restrictions.eq("value", value));
		List objList = criteria.list();
		if (objList.size() > 0) {
			return (CustomConceptAttributeType) objList.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.hibernate.GenericHibernateDAO#update(java.lang.Object)
	 */
	@Override
	public CustomConceptAttributeType update(CustomConceptAttributeType conceptAttributeType) {
		getCurrentSession().setFlushMode(FlushMode.MANUAL);
		CustomConceptAttributeType existingAttrByCode = this.getAttributeByCode(conceptAttributeType.getThesaurus(), conceptAttributeType.getCode());
		CustomConceptAttributeType existingAttrByValue = this.getAttributeByValue(conceptAttributeType.getThesaurus(), conceptAttributeType.getValue());
		boolean isUniqueCode = true;
		boolean isUniqueValue = true;
		if (existingAttrByCode != null && existingAttrByCode.getIdentifier() != conceptAttributeType.getIdentifier()) {
			isUniqueCode = false;
		}
		if (existingAttrByValue != null && existingAttrByValue.getIdentifier() != conceptAttributeType.getIdentifier()) {
			isUniqueValue = false;
		}
		if (isUniqueValue && isUniqueCode) {
			getCurrentSession().saveOrUpdate(conceptAttributeType);
			getCurrentSession().flush();
		} else {
			if (!isUniqueValue) {
				throw new BusinessException(
						"Already existing custom attribute with value : "
								+ conceptAttributeType.getValue(),
						"already-existing-custom-attribute-value",
						new Object[]{conceptAttributeType.getValue()}
				);
			} else {
				throw new BusinessException(
						"Already existing custom attribute with code : "
								+ conceptAttributeType.getCode(),
						"already-existing-custom-attribute-code",
						new Object[]{conceptAttributeType.getCode()}
				);
			}
		}
		return conceptAttributeType;
	}

	public CustomConceptAttributeTypeDAO() {
		super(CustomConceptAttributeType.class);
	}
}