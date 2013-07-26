package org.openmrs.module.integration.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.db.ReportTemplateDAO;

public class HibernateReportTemplateDAO implements ReportTemplateDAO {

	private static Log log = LogFactory.getLog(HibernateCategoryComboDAO.class);

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public ReportTemplate getReportTemplateById(Integer id) {
		return (ReportTemplate) sessionFactory.getCurrentSession().get(
				ReportTemplate.class, id);
	}

	@Override
	public ReportTemplate getReportTemplateByUuid(String uuid) {
		return (ReportTemplate) sessionFactory.getCurrentSession().createCriteria(ReportTemplate.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();	}

	@Override
	public List<ReportTemplate> getChildReportTemplatesByMasterTemplate(
			String masterTemplate) {
		@SuppressWarnings("unchecked")
		List<ReportTemplate> list = sessionFactory.getCurrentSession().createCriteria(ReportTemplate.class)
		        .add(Restrictions.eq("reportTemplateMaster", masterTemplate)).addOrder(Order.asc("reportTemplateId")).list();
		return list;
	}

	@Override
	public List<ReportTemplate> getChildReportTemplatesByServer(int id) {
		@SuppressWarnings("unchecked")
		List<ReportTemplate> list = sessionFactory.getCurrentSession().createCriteria(ReportTemplate.class)
		        .add(Restrictions.eq("serverId", id)).addOrder(Order.asc("reportTemplateId")).list();
		return list;
	}

	@Override
	public ReportTemplate saveChildReportTemplate(ReportTemplate ReportTemplate) {
		sessionFactory.getCurrentSession().saveOrUpdate(ReportTemplate);
		return ReportTemplate;
	}

	@Override
	public void deleteChildReportTemplate(
			ReportTemplate ReportTemplate) {
		sessionFactory.getCurrentSession().delete(ReportTemplate);
	}

}
