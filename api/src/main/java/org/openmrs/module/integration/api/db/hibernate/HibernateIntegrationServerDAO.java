package org.openmrs.module.integration.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.api.db.IntegrationServerDAO;

public class HibernateIntegrationServerDAO implements IntegrationServerDAO {

	private static Log log = LogFactory.getLog(HibernateIntegrationServerDAO.class);

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public IntegrationServer getIntegrationServerById(Integer id) {
		return (IntegrationServer) sessionFactory.getCurrentSession().get(
				IntegrationServer.class, id);
	}

	@Override
	public IntegrationServer getIntegrationServerByUuid(String uuid) {
		return (IntegrationServer) sessionFactory.getCurrentSession().createCriteria(IntegrationServer.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public IntegrationServer getIntegrationServerByName(String name) {
		return (IntegrationServer) sessionFactory.getCurrentSession()
				.createCriteria(IntegrationServer.class)
				.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public IntegrationServer getIntegrationServerByUrl(String url) {
		return (IntegrationServer) sessionFactory.getCurrentSession()
				.createCriteria(IntegrationServer.class)
				.add(Restrictions.eq("url", url)).uniqueResult();
	}

	@Override
	public IntegrationServer saveIntegrationServer(
			IntegrationServer IntegrationServer) {
		sessionFactory.getCurrentSession().saveOrUpdate(IntegrationServer);
		return IntegrationServer;
	}

	@Override
	public void deleteIntegrationServer(
			IntegrationServer IntegrationServer) {
		sessionFactory.getCurrentSession().delete(IntegrationServer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntegrationServer> getAllIntegrationServers() {
		List<IntegrationServer> list = sessionFactory.getCurrentSession().createCriteria(IntegrationServer.class).list();
		return list;
	}
    
}
