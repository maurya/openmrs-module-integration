package org.openmrs.module.integration.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.api.db.DataElementDAO;

public class HibernateDataElementDAO implements DataElementDAO {

	private static Log log = LogFactory.getLog(HibernateCategoryComboDAO.class);

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public DataElement getDataElementById(Integer id) {
		return (DataElement) sessionFactory.getCurrentSession().get(
				DataElement.class, id);
	}

	@Override
	public DataElement getDataElementByUuid(String uuid) {
		return (DataElement) sessionFactory.getCurrentSession().createCriteria(DataElement.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public List<DataElement> getDataElementsByServer(int id) {
		@SuppressWarnings("unchecked")
		List<DataElement> list = sessionFactory.getCurrentSession().createCriteria(DataElement.class)
		        .add(Restrictions.eq("serverId", id)).addOrder(Order.asc("dataElementId")).list();
		return list;
	}

	@Override
	public DataElement saveDataElement(DataElement DataElement) {
		sessionFactory.getCurrentSession().saveOrUpdate(DataElement);
		return DataElement;
	}

	@Override
	public void deleteDataElement(DataElement DataElement) {
		sessionFactory.getCurrentSession().delete(DataElement);
		
	}

}
