package org.openmrs.module.integration.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.integration.DataValueTemplate;
import org.openmrs.module.integration.api.db.DataValueTemplateDAO;

public class HibernateDataValueTemplateDAO implements DataValueTemplateDAO {

	private static Log log = LogFactory.getLog(HibernateCategoryComboDAO.class);

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public DataValueTemplate getDataValueTemplateById(Integer id) {
		return (DataValueTemplate) sessionFactory.getCurrentSession().get(
				DataValueTemplate.class, id);
	}

	@Override
	public DataValueTemplate getDataValueTemplateByUuid(String uuid) {
		return (DataValueTemplate) sessionFactory.getCurrentSession().createCriteria(DataValueTemplate.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public List<DataValueTemplate> getDataValueTemplateByReportTemplate(
			int ReportTemplateId) {
		@SuppressWarnings("unchecked")
		List<DataValueTemplate> list = sessionFactory.getCurrentSession().createCriteria(DataValueTemplate.class)
		        .add(Restrictions.eq("reportTemplateId", ReportTemplateId)).addOrder(Order.asc("dataValueId")).list();
		return list;
	}

	@Override
	public List<DataValueTemplate> getDataValueTemplateByDataElementId(
			int DataElementId) {
		@SuppressWarnings("unchecked")
		List<DataValueTemplate> list = sessionFactory.getCurrentSession().createCriteria(DataValueTemplate.class)
		        .add(Restrictions.eq("dataElementId", DataElementId)).addOrder(Order.asc("dataValueId")).list();
		return list;
	}

	@Override
	public List<DataValueTemplate> getDataValueTemplateByCategoryComboId(
			int CategoryComboId) {
		@SuppressWarnings("unchecked")
		List<DataValueTemplate> list = sessionFactory.getCurrentSession().createCriteria(DataValueTemplate.class)
		        .add(Restrictions.eq("categoryComboId", CategoryComboId)).addOrder(Order.asc("dataValueId")).list();
		return list;
	}

	@Override
	public DataValueTemplate saveDataValueTemplate(
			DataValueTemplate DataValueTemplate) {
		sessionFactory.getCurrentSession().saveOrUpdate(DataValueTemplate);
		return DataValueTemplate;
	}

	@Override
	public void deleteDataValueTemplate(DataValueTemplate DataValueTemplate) {
		sessionFactory.getCurrentSession().delete(DataValueTemplate);
	}

}
