package org.openmrs.module.integration.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.DataValueTemplate;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.Option;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.DhisService;
import org.springframework.transaction.annotation.Transactional;
import org.openmrs.module.integration.api.db.DhisDAO;

public class DhisServiceImpl extends BaseOpenmrsService implements DhisService {
	
		// Logger
		private transient Log log = LogFactory.getLog(DhisServiceImpl.class);

		// Private variables
		private DhisDAO dao;
		
		/**
		* @param dao the dao to set
		*/
		    public void setDao(DhisDAO dao) {
		this.dao = dao;
		    }
		    
		    /**
		* @return the dao
		*/
		    public DhisDAO getDao() {
		return dao;
		    }
	@Override
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerById(Integer id) {
		return dao.getIntegrationServerById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByUuid(String uuid) {
		return dao.getIntegrationServerByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByName(String serverName) {
		return dao.getIntegrationServerByName(serverName);
	}

	@Override
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByUrl(String url) {
		return dao.getIntegrationServerByUrl(url);
	}

	@Override
	@Transactional(readOnly = true)
	public List<IntegrationServer> getAllIntegrationServers() {
		return dao.getAllIntegrationServers();
	}

	@Override
	@Transactional
	public IntegrationServer saveIntegrationServer(
			IntegrationServer IntegrationServer) {
		return dao.saveIntegrationServer(IntegrationServer);
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteIntegrationServer(IntegrationServer IntegrationServer) {
		 dao.deleteIntegrationServer(IntegrationServer);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Option getOptionById(Integer id) {
		return dao.getOptionById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Option getOptionByUuid(String uuid) {
		return dao.getOptionByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Option> getOptionsByServer(IntegrationServer IntegrationServer) {
		return dao.getOptionsByServer(IntegrationServer);
	}

	@Override
	@Transactional
	public Option saveOption(Option Option) {
		return dao.saveOption(Option);
	}

	@Override
	@Transactional
	public void deleteOption(Option Option) {
		dao.deleteOption(Option);
		
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryOption getCategoryOptionById(Integer id) {
		return dao.getCategoryOptionById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryOption getCategoryOptionByUuid(String uuid) {
		return dao.getCategoryOptionByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryOption> getCategoryOptionByServer(IntegrationServer IntegrationServer) {
		return dao.getCategoryOptionByServer(IntegrationServer);
	}

	@Override
	@Transactional
	public CategoryOption saveCategoryOption(CategoryOption CategoryOption) {
		return dao.saveCategoryOption(CategoryOption);
	}

	@Override
	@Transactional
	public void deleteCategoryOption(CategoryOption CategoryOption) {
		dao.deleteCategoryOption(CategoryOption);
		
	}

	@Override
	@Transactional(readOnly = true)
	public ReportTemplate getReportTemplateById(Integer id) {
		return dao.getReportTemplateById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public ReportTemplate getReportTemplateByUuid(String uuid) {
		return dao.getReportTemplateByUuid(uuid);
	}


	@Override
	@Transactional(readOnly = true)
	public List<ReportTemplate> getReportTemplatesByServer(IntegrationServer integrationServer) {
		return dao.getReportTemplatesByServer(integrationServer);
	}

	@Override
	@Transactional
	public ReportTemplate saveReportTemplate(ReportTemplate ReportTemplate) {
		return dao.saveReportTemplate(ReportTemplate);
	}

	@Override
	@Transactional
	public void deleteReportTemplate(ReportTemplate ReportTemplate) {
		dao.deleteReportTemplate(ReportTemplate);
		
	}

	@Override
	@Transactional(readOnly = true)
	public DataElement getDataElementById(Integer id) {
		return dao.getDataElementById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public DataElement getDataElementByUuid(String uuid) {
		return dao.getDataElementByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataElement> getDataElementsByServer(IntegrationServer IntegrationServer) {
		return dao.getDataElementsByServer(IntegrationServer);
	}

	@Override
	@Transactional
	public DataElement saveDataElement(DataElement DataElement) {
		return dao.saveDataElement(DataElement);
	}

	@Override
	@Transactional
	public void deleteDataElement(DataElement DataElement) {
		dao.deleteDataElement(DataElement);
		
	}

	@Override
	@Transactional(readOnly = true)
	public DataValueTemplate getDataValueTemplateById(Integer id) {
		return dao.getDataValueTemplateById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public DataValueTemplate getDataValueTemplateByUuid(String uuid) {
		return dao.getDataValueTemplateByUuid(uuid);
				
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataValueTemplate> getDataValueTemplateByReportTemplate(
			ReportTemplate ReportTemplate) {
		return dao.getDataValueTemplateByReportTemplate(ReportTemplate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataValueTemplate> getDataValueTemplateByDataElement(
			DataElement DataElement) {
		return dao.getDataValueTemplateByDataElement(DataElement);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataValueTemplate> getDataValueTemplateByCategoryOption(
			CategoryOption CategoryOption) {
		return dao.getDataValueTemplateByCategoryOption(CategoryOption);
	}

	@Override
	@Transactional
	public DataValueTemplate saveDataValueTemplate(
			DataValueTemplate DataValueTemplate) {
		return dao.saveDataValueTemplate(DataValueTemplate);
	}

	@Override
	@Transactional
	public void deleteDataValueTemplate(DataValueTemplate DataValueTemplate) {
		dao.deleteDataValueTemplate(DataValueTemplate);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Map<DataElement, List<CategoryOption>> getDataElementToCategoryOptionDictionaryByReportTemplate(
			ReportTemplate ReportTemplate) {
		
		DataElement de=new DataElement();
		CategoryOption co=new CategoryOption();
		List<CategoryOption> temporaryCategoryOptionList=new ArrayList<CategoryOption>();
		Map<DataElement,List<CategoryOption>> DataElementToCategoryOptionDictionary = new HashMap<DataElement, List<CategoryOption>>();
		List<DataValueTemplate> DataValueTemplateList=getDataValueTemplateByReportTemplate(ReportTemplate);
		for(DataValueTemplate d:DataValueTemplateList){
			de=d.getDataElement();
			co=d.getCategoryOption();			
			temporaryCategoryOptionList=DataElementToCategoryOptionDictionary.get(de);
			if(temporaryCategoryOptionList== null){	
				temporaryCategoryOptionList=new ArrayList<CategoryOption>();
				DataElementToCategoryOptionDictionary.put(de, temporaryCategoryOptionList);
			}
			temporaryCategoryOptionList.add(co);
		}
		return DataElementToCategoryOptionDictionary;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Option> getOptionToCategoryOptionDictionaryByReportTemplate(
			ReportTemplate ReportTemplate) {
		Set<Option> OptionsList=new HashSet<Option>();
		List<DataValueTemplate> DataValueTemplateList=getDataValueTemplateByReportTemplate(ReportTemplate);
		Set<CategoryOption> categoryOptionList = new HashSet<CategoryOption>();
		for(DataValueTemplate d:DataValueTemplateList){
			categoryOptionList.add(d.getCategoryOption());
			}
		for(CategoryOption co: categoryOptionList){
			OptionsList.addAll(co.getOptions());
			}
		return OptionsList;
	}

}
