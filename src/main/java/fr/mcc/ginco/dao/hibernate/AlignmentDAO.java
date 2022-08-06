/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import fr.mcc.ginco.beans.Alignment;
import fr.mcc.ginco.dao.IAlignmentDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the data access object to the alignment database table
 */
@Repository
public class AlignmentDAO extends
		GenericHibernateDAO<Alignment, String> implements IAlignmentDAO {

	public AlignmentDAO() {
		super(Alignment.class);
	}


	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.IAlignmentDAO#findBySourceConceptId(java.lang.String)
	 */
	@Override
	public List<Alignment> findBySourceConceptId(String sourceConceptId) {
		Criteria criteria = getCurrentSession().createCriteria(Alignment.class);
		criteria.add(Restrictions.eq("sourceConcept.identifier", sourceConceptId));
		return criteria.list();
	}

	@Override
	public List<Alignment> findByTargetConceptId(String targetConceptId) {
		Criteria criteria = getCurrentSession().createCriteria(Alignment.class);
		criteria.createAlias("targetConcepts", "tc");
		criteria.add(Restrictions.eq("tc.internalTargetConcept.identifier", targetConceptId));
		return criteria.list();
	}

	@Override
	public List<Alignment> findByExternalThesaurus(Integer externalThesaurusId) {
		Criteria criteria = getCurrentSession().createCriteria(Alignment.class);
		criteria.add(Restrictions.eq("externalTargetThesaurus.identifier", externalThesaurusId));
		return criteria.list();
	}

}