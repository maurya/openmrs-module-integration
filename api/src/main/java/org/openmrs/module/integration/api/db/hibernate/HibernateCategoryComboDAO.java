package org.openmrs.module.integration.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.integration.CategoryCombo;
import org.openmrs.module.integration.api.db.CategoryComboDAO;

public class HibernateCategoryComboDAO implements CategoryComboDAO {

	private static Log log = LogFactory.getLog(HibernateCategoryComboDAO.class);

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public CategoryCombo getCategoryComboById(Integer id) {
		return (CategoryCombo) sessionFactory.getCurrentSession().get(
				CategoryCombo.class, id);
	}

	@Override
	public CategoryCombo getCategoryComboByUuid(String uuid) {
		return (CategoryCombo) sessionFactory.getCurrentSession().createCriteria(CategoryCombo.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public List<CategoryCombo> getCategoryComboByServer(int id) {
		@SuppressWarnings("unchecked")
		List<CategoryCombo> list = sessionFactory.getCurrentSession().createCriteria(CategoryCombo.class)
		        .add(Restrictions.eq("serverId", id)).addOrder(Order.asc("categoryComboId")).list();
		return list;
	}

	@Override
	public CategoryCombo saveCategoryCombo(CategoryCombo CategoryCombo) {
		sessionFactory.getCurrentSession().saveOrUpdate(CategoryCombo);
		return CategoryCombo;
	}

	@Override
	public void deleteCategoryCombo(CategoryCombo CategoryCombo) {
		sessionFactory.getCurrentSession().delete(CategoryCombo);
	}

}
