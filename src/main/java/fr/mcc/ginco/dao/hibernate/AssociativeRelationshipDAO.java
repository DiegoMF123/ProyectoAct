/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import fr.mcc.ginco.beans.AssociativeRelationship;
import fr.mcc.ginco.beans.ThesaurusConcept;
import fr.mcc.ginco.dao.IAssociativeRelationshipDAO;
import fr.mcc.ginco.enums.ConceptStatusEnum;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Diego_MF
 */
@Repository
public class AssociativeRelationshipDAO extends
		GenericHibernateDAO<AssociativeRelationship, String> implements
		IAssociativeRelationshipDAO {


	public AssociativeRelationshipDAO() {
		super(AssociativeRelationship.class);
	}

	@Override
	public List<String> getAssociatedConcepts(ThesaurusConcept concept, ConceptStatusEnum status) {
		DetachedCriteria d1 = DetachedCriteria.forClass(AssociativeRelationship.class, "ar1");
		d1.setProjection(Projections.projectionList().add(Projections.property("ar1.identifier.concept2")));
		d1.add(Restrictions.eq("identifier.concept1", concept.getIdentifier()));

		DetachedCriteria d2 = DetachedCriteria.forClass(AssociativeRelationship.class, "ar2");
		d2.setProjection(Projections.projectionList().add(Projections.property("ar2.identifier.concept1")));
		d2.add(Restrictions.eq("identifier.concept2", concept.getIdentifier()));

		Criteria criteria = getCurrentSession().createCriteria(ThesaurusConcept.class, "tc")
				.add(Restrictions.or(
						Subqueries.propertyIn("tc.identifier", d1),
						Subqueries.propertyIn("tc.identifier", d2)))
				.setProjection(Projections.property("tc.identifier"));
		if (status!=null) {
			criteria.add(Restrictions.eq("tc.status",
					status.getStatus()));
		}

		return criteria.list();
	}
	
	@Override
	public List<String> getAssociatedConcepts(ThesaurusConcept concept) {
		return getAssociatedConcepts(concept, null);
	}


	@Override
	public List<AssociativeRelationship> getAssociationsForConcept(ThesaurusConcept concept) {

		Criteria criteria = getCurrentSession().createCriteria(AssociativeRelationship.class)
				.add(Restrictions.or(
						Restrictions.eq("conceptLeft.identifier", concept.getIdentifier()),
						Restrictions.eq("conceptRight.identifier", concept.getIdentifier())));
		return criteria.list();
	}

	@Override
	public AssociativeRelationship getAssociativeRelationship(String id1, String id2) {
		Criteria criteria = getCurrentSession().createCriteria(AssociativeRelationship.class, "ar")
				.add(Restrictions.or(
						Restrictions.and(Restrictions.eq("conceptLeft.identifier", id1), Restrictions.eq("conceptRight.identifier", id2)),
						Restrictions.and(Restrictions.eq("conceptRight.identifier", id1), Restrictions.eq("conceptLeft.identifier", id2))));
		List<AssociativeRelationship> res = criteria.list();
		if (res != null && !res.isEmpty()) {
			return res.get(0);
		}
		return null;
	}
}