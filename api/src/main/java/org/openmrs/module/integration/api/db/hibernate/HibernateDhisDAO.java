package org.openmrs.module.integration.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.DataValueTemplate;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.Option;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.db.DhisDAO;

public class HibernateDhisDAO implements DhisDAO{

	private static Log log = LogFactory.getLog(HibernateDhisDAO.class);

    private SessionFactory sessionFactory;
    
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
    
    //integration server
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
	public IntegrationServer getIntegrationServerByName(String serverName) {
		return (IntegrationServer) sessionFactory.getCurrentSession()
				.createCriteria(IntegrationServer.class)
				.add(Restrictions.eq("serverName", serverName)).uniqueResult();
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
	
	//option set
	
	@Override
	public Option getOptionById(Integer id) {
		return (Option) sessionFactory.getCurrentSession().get(
				Option.class, id);
	}

	@Override
	public Option getOptionByUuid(String uuid) {
		return (Option) sessionFactory.getCurrentSession().createCriteria(Option.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public List<Option> getOptionsByServer(IntegrationServer integrationServer) {
		@SuppressWarnings("unchecked")
		List<Option> list = sessionFactory.getCurrentSession().createCriteria(Option.class)
		        .add(Restrictions.eq("integrationServer", integrationServer)).addOrder(Order.asc("optionId")).list();
		return list;
	}

	@Override
	public Option saveOption(Option Option) {
			sessionFactory.getCurrentSession().saveOrUpdate(Option);
			return Option;
	}

	@Override
	public void deleteOption(Option Option) {
			sessionFactory.getCurrentSession().delete(Option);
	}
	
	//category combo
	@Override
	public CategoryOption getCategoryOptionById(Integer id) {
		return (CategoryOption) sessionFactory.getCurrentSession().get(
				CategoryOption.class, id);
	}

	@Override
	public CategoryOption getCategoryOptionByUuid(String uuid) {
		return (CategoryOption) sessionFactory.getCurrentSession().createCriteria(CategoryOption.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public List<CategoryOption> getCategoryOptionByServer(IntegrationServer integrationServer) {
		@SuppressWarnings("unchecked")
		List<CategoryOption> list = sessionFactory.getCurrentSession().createCriteria(CategoryOption.class)
		        .add(Restrictions.eq("integrationServer", integrationServer)).addOrder(Order.asc("categoryOptionId")).list();
		return list;
	}

	@Override
	public CategoryOption saveCategoryOption(CategoryOption CategoryOption) {
		sessionFactory.getCurrentSession().saveOrUpdate(CategoryOption);
		return CategoryOption;
	}

	@Override
	public void deleteCategoryOption(CategoryOption CategoryOption) {
		sessionFactory.getCurrentSession().delete(CategoryOption);
	}

	//report template
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
	public List<ReportTemplate> getReportTemplatesByServer(IntegrationServer integrationServer) {
		@SuppressWarnings("unchecked")
		List<ReportTemplate> list = sessionFactory.getCurrentSession()
										.createCriteria(ReportTemplate.class)
										.add(Restrictions.eq("integrationServer", integrationServer))
										.addOrder(Order.asc("reportTemplateId")).list();
		return list;
	}

	@Override
	public ReportTemplate saveReportTemplate(ReportTemplate ReportTemplate) {
		sessionFactory.getCurrentSession().saveOrUpdate(ReportTemplate);
		return ReportTemplate;
	}

	@Override
	public void deleteReportTemplate(
			ReportTemplate ReportTemplate) {
		sessionFactory.getCurrentSession().delete(ReportTemplate);
	}

	//data elements
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
	public List<DataElement> getDataElementsByServer(IntegrationServer integrationServer) {
		@SuppressWarnings("unchecked")
		List<DataElement> list = sessionFactory.getCurrentSession().createCriteria(DataElement.class)
		        .add(Restrictions.eq("integrationServer", integrationServer)).addOrder(Order.asc("dataElementId")).list();
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

	//data value templates
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
			ReportTemplate reportTemplate) {
		@SuppressWarnings("unchecked")
		List<DataValueTemplate> list = sessionFactory.getCurrentSession().createCriteria(DataValueTemplate.class)
		        .add(Restrictions.eq("reportTemplate", reportTemplate)).addOrder(Order.asc("dataValueTemplateId")).list();
		return list;
	}

	@Override
	public List<DataValueTemplate> getDataValueTemplateByDataElement(
			DataElement dataElement) {
		@SuppressWarnings("unchecked")
		List<DataValueTemplate> list = sessionFactory.getCurrentSession().createCriteria(DataValueTemplate.class)
		        .add(Restrictions.eq("dataElement", dataElement)).addOrder(Order.asc("dataValueTemplateId")).list();
		return list;
	}

	@Override
	public List<DataValueTemplate> getDataValueTemplateByCategoryOption(
			CategoryOption categoryOption) {
		@SuppressWarnings("unchecked")
		List<DataValueTemplate> list = sessionFactory.getCurrentSession().createCriteria(DataValueTemplate.class)
		        .add(Restrictions.eq("categoryOption", categoryOption)).addOrder(Order.asc("dataValueTemplateId")).list();
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

	@Override
	public List<CategoryOption> getCategoryComboOptionsByUid(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Option> getOptionSetOptionsByCode(String code) {
		@SuppressWarnings("unchecked")
		List<Option> OptionList= sessionFactory.getCurrentSession().createCriteria(Option.class)
								.add(Restrictions.eq("set_code", code))
								.addOrder(Order.desc("name")).list();
		 
	        return OptionList;
	}

	@Override
	public List<ReportTemplate> getAllReportTemplates() {
		
		@SuppressWarnings("unchecked")
		List<ReportTemplate> ReportTemplateList=sessionFactory.getCurrentSession().createCriteria(ReportTemplate.class).list();

return ReportTemplateList;
	}


}
