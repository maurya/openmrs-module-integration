package org.openmrs.module.integration.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.integration.CategoryComboToOptionSet;
import org.openmrs.module.integration.api.db.CategoryComboToOptionSetDAO;

public class HibernateCategoryComboToOptionSetDAO implements CategoryComboToOptionSetDAO {

	private static Log log = LogFactory.getLog(HibernateCategoryComboDAO.class);

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public List<CategoryComboToOptionSet> getCategoryComboToOptionSetByCategoryComboId(
			int id) {
		@SuppressWarnings("unchecked")
		List<CategoryComboToOptionSet> list = sessionFactory.getCurrentSession().createCriteria(CategoryComboToOptionSet.class)
		        .add(Restrictions.eq("categoryComboId", id)).addOrder(Order.asc("categoryComboToOptionSetId")).list();
		return list;
	}

	@Override
	public List<CategoryComboToOptionSet> getCategoryComboToOptionSetByOptionSetId(
			int id) {
		@SuppressWarnings("unchecked")
		List<CategoryComboToOptionSet> list = sessionFactory.getCurrentSession().createCriteria(CategoryComboToOptionSet.class)
		        .add(Restrictions.eq("optionSetId", id)).addOrder(Order.asc("categoryComboToOptionSetId")).list();
		return list;
	}


}
