/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import org.springframework.stereotype.Repository;

import fr.mcc.ginco.beans.ConceptHierarchicalRelationship;
import fr.mcc.ginco.dao.IConceptHierarchicalRelationshipDAO;

/**
 *
 * @author Diego_MF
 */
@Repository
public class ConceptHierarchicalRelationshipDAO extends
		GenericHibernateDAO<ConceptHierarchicalRelationship, ConceptHierarchicalRelationship.Id> implements
		IConceptHierarchicalRelationshipDAO {
	
	public ConceptHierarchicalRelationshipDAO() {
		super(ConceptHierarchicalRelationship.class);
	}
}