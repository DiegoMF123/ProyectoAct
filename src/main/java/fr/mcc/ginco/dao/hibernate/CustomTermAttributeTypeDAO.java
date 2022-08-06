/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mcc.ginco.beans.CustomTermAttributeType;
import fr.mcc.ginco.beans.Thesaurus;
import fr.mcc.ginco.dao.ICustomTermAttributeTypeDAO;
import fr.mcc.ginco.exceptions.BusinessException;

/**
 *
 * @author Diego_MF
 */
@Repository
public class CustomTermAttributeTypeDAO extends
		GenericHibernateDAO<CustomTermAttributeType, Integer> implements
		ICustomTermAttributeTypeDAO {

	@Override
	public List<CustomTermAttributeType> getAttributesByThesaurus(
			Thesaurus thesaurus) {
		Criteria criteria = getCurrentSession().createCriteria(
				CustomTermAttributeType.class).add(
				Restrictions.eq("thesaurus.identifier",
						thesaurus.getIdentifier())
		);
		return (List<CustomTermAttributeType>) criteria.list();
	}

	public CustomTermAttributeTypeDAO() {
		super(CustomTermAttributeType.class);
	}

	@Override
	public CustomTermAttributeType getAttributeByValue(Thesaurus thesaurus,
	                                                   String value) {
		Criteria criteria = getCurrentSession().createCriteria(CustomTermAttributeType.class)
				.add(Restrictions.eq("thesaurus.identifier", thesaurus.getIdentifier()))
				.add(Restrictions.eq("value", value));
		List objList = criteria.list();
		if (objList.size() > 0) {
			return (CustomTermAttributeType) objList.get(0);
		}
		return null;
	}

	@Override
	public CustomTermAttributeType getAttributeByCode(Thesaurus thesaurus,
	                                                  String code) {
		Criteria criteria = getCurrentSession()
				.createCriteria(CustomTermAttributeType.class)
				.add(Restrictions.eq("thesaurus.identifier",
						thesaurus.getIdentifier()))
				.add(Restrictions.eq("code", code));
		List objList = criteria.list();
		if (objList.size() > 0) {
			return (CustomTermAttributeType) objList.get(0);
		}
		return null;
	}

	@Override
	public CustomTermAttributeType update(CustomTermAttributeType termAttributeType) {
		getCurrentSession().setFlushMode(FlushMode.MANUAL);
		CustomTermAttributeType existingAttrByCode = this.getAttributeByCode(termAttributeType.getThesaurus(), termAttributeType.getCode());
		CustomTermAttributeType existingAttrByValue = this.getAttributeByValue(termAttributeType.getThesaurus(), termAttributeType.getValue());
		boolean isUniqueCode = true;
		boolean isUniqueValue = true;
		if (existingAttrByCode != null && existingAttrByCode.getIdentifier() != termAttributeType.getIdentifier()) {
			isUniqueCode = false;
		}
		if (existingAttrByValue != null && existingAttrByValue.getIdentifier() != termAttributeType.getIdentifier()) {
			isUniqueValue = false;
		}
		if (isUniqueValue && isUniqueCode) {
			getCurrentSession().saveOrUpdate(termAttributeType);
			getCurrentSession().flush();
		} else {
			if (!isUniqueValue) {
				throw new BusinessException(
						"Already existing custom attribute with value: "
								+ termAttributeType.getValue(),
						"already-existing-custom-attribute-value",
						new Object[]{ termAttributeType.getValue() }
				);
			} else {
				throw new BusinessException(
						"Already existing custom attribute with code: "
								+ termAttributeType.getCode(),
						"already-existing-custom-attribute-code",
						new Object[]{ termAttributeType.getCode() }
				);
			}

		}
		return termAttributeType;
	}
}