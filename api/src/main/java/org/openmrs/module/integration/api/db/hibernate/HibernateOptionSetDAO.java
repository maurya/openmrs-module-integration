package org.openmrs.module.integration.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.integration.OptionSet;
import org.openmrs.module.integration.api.db.OptionSetDAO;

public class HibernateOptionSetDAO implements OptionSetDAO {

	private static Log log = LogFactory.getLog(HibernateOptionSetDAO.class);

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public OptionSet getOptionSetById(Integer id) {
		return (OptionSet) sessionFactory.getCurrentSession().get(
				OptionSet.class, id);
	}

	@Override
	public OptionSet getOptionSetByUuid(String uuid) {
		return (OptionSet) sessionFactory.getCurrentSession().createCriteria(OptionSet.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public List<OptionSet> getOptionSetsByServer(int id) {
		@SuppressWarnings("unchecked")
		List<OptionSet> list = sessionFactory.getCurrentSession().createCriteria(OptionSet.class)
		        .add(Restrictions.eq("serverId", id)).addOrder(Order.asc("optionSetId")).list();
		return list;
	}

	@Override
	public OptionSet saveOptionSet(OptionSet OptionSet) {
			sessionFactory.getCurrentSession().saveOrUpdate(OptionSet);
			return OptionSet;
	}

	@Override
	public void deleteOptionSet(OptionSet OptionSet) {
			sessionFactory.getCurrentSession().delete(OptionSet);
	}

}
