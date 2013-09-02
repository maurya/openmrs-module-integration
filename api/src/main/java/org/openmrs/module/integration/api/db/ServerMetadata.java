package org.openmrs.module.integration.api.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.api.jaxb.ReportTemplates;
import org.openmrs.module.integration.api.jaxb.MetaData;
import org.openmrs.module.integration.api.jaxb.OrgUnitType;
import org.openmrs.module.integration.api.jaxb.ObjectFactory;
import org.openmrs.util.OpenmrsUtil;

/**
 * This object holds a complete set of DHIS metadata. It can be filled from
 * the DHIS API, a set of resources, or files kept in the user application
 * directory. It also performs the connection test.
 */
public class ServerMetadata {

	private static Log log = LogFactory.getLog(ServerMetadata.class);
	private static String MODULE_NAME = "Integration";
	private static String NAMESPACE = "http://www.dhis.org/schemas/DXF/2.0/";

	private static String catsCall = "/api/categoryOptionCombos?viewClass=detailed&paging=false";
	private static String optsCall = "/api/categories?viewClass=export&paging=false";
	private static String orgsCall = "/api/organisationUnits?viewClass=export&paging=false";
	private static String rptsCall = "/api/dataSets";

	public enum ContentType {
		MASTER, CATS, OPTS, ORGS
	}

	private final MessageSourceService mss = Context.getMessageSourceService();
	private final Double hash = Math.random();
	
	private String name;
	private IntegrationServer server;
	private ReportTemplates master;
	private List<MetaData.CategoryOptionCombos.CategoryOptionCombo> cats;
	private List<MetaData.Categories.Category> opts;
	private List<MetaData.OrganisationUnits.OrganisationUnit> orgs;

	private JAXBContext jc;
	private Unmarshaller um;
	
	public ServerMetadata() {
		super();
		try {
			jc = JAXBContext.newInstance(ReportTemplates.class,MetaData.class,OrgUnitType.class);
			um = jc.createUnmarshaller();
		} catch (JAXBException e) {
			jc= null;
			um = null;
		}
	}

	/**
	 * This method initializes the object via the DHIS API. This method stores
	 * the XML received in <application dir>/Integration/<server name>/New. The
	 * files are called master.xml, cats.xml, opts.xml.
	 * 
	 * @param server the server object which is to be accessed.
	 */
	public void getServerMetadata(IntegrationServer server)
			throws IntegrationException {
		this.server = server;
		this.name = server.getServerName();
		final URL url;
		try {
			url = new URL(server.getUrl());
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(), e);
		}
		Credentials creds = new UsernamePasswordCredentials(
				server.getUserName(), server.getPassword());

		this.master = getMasterFromStream(getStreamFromAPI(url, rptsCall, creds,
				"application/dsd+xml", ContentType.MASTER));
		this.cats = getMetaDataFromStream(getStreamFromAPI(url, catsCall, creds,
				"application/xml", ContentType.CATS)).getCategoryOptionCombos().getCategoryOptionCombo();
		this.opts = getMetaDataFromStream(getStreamFromAPI(url, optsCall, creds,
				"application/xml", ContentType.OPTS)).getCategories().getCategory();
	}

	/**
	 * This method initializes the object via resources in the project, it is
	 * intended primarily for testing.
	 * 
	 * @param master the resource to be used as the master xml
	 * @param cats the resource to be used as the cats xml
	 * @param opts the resource to be used as the opts xml
	 */
	public void getServerMetadata(String master, String cats, String opts) throws IntegrationException {
		this.server=new IntegrationServer();
		this.name = master.endsWith(".xml") ? master.substring(0,master.length()-4) : master;
		this.server.setServerName(name);
		this.master = getMasterFromStream(getResourceStream(master));
		this.cats=getMetaDataFromStream(getResourceStream(cats)).getCategoryOptionCombos().getCategoryOptionCombo();
		this.opts=getMetaDataFromStream(getResourceStream(opts)).getCategories().getCategory();

	}

	/**
	 * This method initializes the object via files in the application
	 * directory.
	 * 
	 * @param path the directory in which the files are located
	 * @param master the file to be used as the master xml
	 * @param cats the file to be used as the cats xml
	 * @param opts the file to be used as the opts xml
	 */
	public void getServerMetadata(String path, String master, String cats,
			String opts) throws IntegrationException {
		this.server=new IntegrationServer();
		this.name = name;
		this.server.setServerName(name);
		this.master = getMasterFromStream(getFileStream(path,master));
		this.cats=getMetaDataFromStream(getFileStream(path,cats)).getCategoryOptionCombos().getCategoryOptionCombo();
		this.opts=getMetaDataFromStream(getFileStream(path,opts)).getCategories().getCategory();
	}

	// basic object methods

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return hash.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerMetadata other = (ServerMetadata) obj;
		if (master.equals(other.getMaster()))
			if (cats.equals(other.getCats()))
				if (opts.equals(other.getOpts()))
					return true;
		return false;
	}

	// getters for the underlying components -- no setters
	
	public IntegrationServer getServer() {
		return server;
	}

	public ReportTemplates getMaster() {
		return master;
	}

	public List<ReportTemplates.DataElements.DataElement> getDataElements() {
		return master.getDataElements().getDataElement();
	}

	public List<ReportTemplates.Disaggregations.Disaggregation> getDisaggregations() {
		return master.getDisaggregations().getDisaggregation();
	}

	public List<ReportTemplates.ReportTemplate> getReportTemplates() {
		return master.getReportTemplate();
	}

	public List<MetaData.Categories.Category> getOpts() {
		return opts;
	}

	public List<MetaData.CategoryOptionCombos.CategoryOptionCombo> getCats() {
		return cats;
	}
	
	public List<MetaData.OrganisationUnits.OrganisationUnit> getOrgs() {
		return orgs;
	}
	
	
	/**
	 * This method unmarshals a ReportTemplates object from a stream.
	 * The passage through JAXBElement comes from 
	 * http://stackoverflow.com/questions/819720/no-xmlrootelement-generated-by-jaxb
	 * and is used for consistency even though a root element is generated for ReportTemplates
	 * 
	 * @param stream the InputStream containing the xml
	 * @return the unmarshalled object representing the xml
	 */
	private ReportTemplates getMasterFromStream(InputStream stream) throws IntegrationException {
		ReportTemplates user = null;
		try {
			StreamSource ss = new StreamSource(stream);
			JAXBElement<ReportTemplates> userElement = (JAXBElement<ReportTemplates>) um.unmarshal(ss,ReportTemplates.class);
			user = userElement.getValue();
		} catch (JAXBException e) {
			throw new IntegrationException(e.getLocalizedMessage());
		}
		return user;
	}

	/**
	 * This method unmarshals a MetaData object from a stream.
	 * The passage through JAXBElement comes from 
	 * http://stackoverflow.com/questions/819720/no-xmlrootelement-generated-by-jaxb
	 * and is used because no root element is generated for MetaData
	 * 
	 * @param stream the InputStream containing the xml
	 * @return the unmarshalled object representing the xml
	 */
	private MetaData getMetaDataFromStream(InputStream stream) throws IntegrationException {
		MetaData user = null;
		try {
			StreamSource ss = new StreamSource(stream);
			JAXBElement<MetaData> userElement = (JAXBElement<MetaData>) um.unmarshal(ss,MetaData.class);
			user = userElement.getValue();
		} catch (JAXBException e) {
			throw new IntegrationException(e.getLocalizedMessage());
		}
		return user;
	}

	/**
	 * This method gets XML from the API, stores it in the user application
	 * directory, and provides a stream for unmarshalling.
	 * 
	 * @param url the url of the host to be accessed
	 * @param selector the low-order part of the url for getting a particular set
	 * @param creds a credentials object containing the username and password
	 * @param accept the contents of the accept header
	 * @param saveAs the content type to be saved (MASTER, CATS, OPTS)
	 * @return the unmarshalled object representing the xml
	 */
	private InputStream getStreamFromAPI(URL url, String selector, Credentials creds,
			String accept, ContentType saveAs) throws IntegrationException {
		InputStream result = null;
		
//	Setup the GET
		HttpHost targetHost = new HttpHost(url.getHost(), url.getPort(),
				url.getProtocol());
		DefaultHttpClient httpclient = new DefaultHttpClient();
		BasicHttpContext localcontext = new BasicHttpContext();

		try {
			HttpGet httpGet = new HttpGet(server.getUrl() + selector);
			Header bs = new BasicScheme().authenticate(creds, httpGet,
					localcontext);
			httpGet.setHeader("Authorization", bs.getValue());
			httpGet.setHeader("Content-Type", "application/xml");
			httpGet.setHeader("Accept", accept);

//	GET the response			
			HttpResponse response = httpclient.execute(targetHost, httpGet,
					localcontext);
			HttpEntity entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IntegrationException(response.getStatusLine()
						.getReasonPhrase(), null);
			}

//	Copy the XML from the response to the application directory			
			if (entity != null) {
					final StringBuilder sb = new StringBuilder();
					sb.append(MODULE_NAME);
					sb.append(File.separatorChar);
					sb.append(server.getServerName());
					sb.append(File.separatorChar);
					sb.append("New");
					File folder = OpenmrsUtil
							.getDirectoryInApplicationDataDirectory(sb.toString());
					File of = new File(folder, saveAs.toString().toLowerCase()
							+ ".xml");
					OutputStream os = new FileOutputStream(of);
					IOUtils.copy(entity.getContent(), os);
					
//	Use the stream from the application directory
					result = getFileStream(sb.toString(), saveAs.toString()
							.toLowerCase() + ".xml");
			}
			
//	Catch all the exceptions
		} catch (APIException ex) {
			throw new IntegrationException(ex.getLocalizedMessage());
		} catch (IntegrationException ex) {
			throw ex;		
		} catch (AuthenticationException ex) {
			throw new IntegrationException(ex.getLocalizedMessage());
		} catch (IOException ex) {
			throw new IntegrationException(ex.getLocalizedMessage());

// Close the connection			
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return result;
	}


	/**
	 * This method gets the stream representing a resource
	 * 
	 * @param resource the name of the file
	 * @return input stream containing test
	 */
	private InputStream getResourceStream(String resource) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(resource);
	}

	/**
	 * This method gets the input stream for a file in the user application
	 * directory
	 * 
	 * @param folder the directory within the user application directory
	 * @param file the name of the file
	 * @return input stream for the file
	 */
	private InputStream getFileStream(String folder, String file)
			throws IntegrationException {
		final StringBuilder sb = new StringBuilder();
		sb.append(OpenmrsUtil.getDirectoryInApplicationDataDirectory(folder));
		if (sb.charAt(sb.length() - 1) != File.separatorChar)
			sb.append(File.separatorChar);
		sb.append(file);
		try {
			File f = new File(sb.toString());
			return new FileInputStream(f);
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * This method performs the connection test function
	 * 
	 * @param server the server to be tested
	 * @return null if connection tests ok, else a localized error message
	 */
	public String testConnection(IntegrationServer server) {
		String result = null;
		Credentials creds = new UsernamePasswordCredentials(
				server.getUserName(), server.getPassword());
		DefaultHttpClient httpclient = new DefaultHttpClient();
		BasicHttpContext localcontext = new BasicHttpContext();

		final URL url;
		try {
			url = new URL(server.getUrl());
			HttpHost targetHost = new HttpHost(url.getHost(), url.getPort(),
					url.getProtocol());
			HttpGet httpGet = new HttpGet(server.getUrl() + "/api");

			Header bs = new BasicScheme().authenticate(creds, httpGet,
					localcontext);
			httpGet.setHeader("Authorization", bs.getValue());
			httpGet.setHeader("Content-Type", "text/html");
			httpGet.setHeader("Accept", "text/html");

			HttpResponse response = httpclient.execute(targetHost, httpGet,
					localcontext);
			if (response.getStatusLine().getStatusCode() != 200) {
				result = response.getStatusLine().getReasonPhrase();
			}
		} catch (MalformedURLException e) {
			result = mss.getMessage("Integration.General.Errors.MalformedUrl");
		} catch (AuthenticationException e) {
			result = mss
					.getMessage("Integration.General.Errors.Authentication");
		} catch (ClientProtocolException e) {
			result = mss
					.getMessage("Integration.General.Errors.ClientProtocol");
		} catch (IOException e) {
			result = mss.getMessage("Integration.General.Errors.IOFailure");
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return result;
	}

	/**
	 * This method gets org units from the server.
	 * 
	 */
	public void getOrgUnits()
			throws IntegrationException {
		final URL url;
		try {
			url = new URL(server.getUrl());
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(), e);
		}
		Credentials creds = new UsernamePasswordCredentials(
				server.getUserName(), server.getPassword());

		this.orgs = getMetaDataFromStream(getStreamFromAPI(url, orgsCall, creds,
				"application/xml", ContentType.ORGS)).getOrganisationUnits().getOrganisationUnit();
	}

	/**
	 * This method gets org units via a resource in the project, it is
	 * intended primarily for testing.
	 * 
	 * @param orgs the resource to be used as the orgs xml
	 */
	public void getOrgUnits(String orgs) throws IntegrationException {
		this.orgs=getMetaDataFromStream(getResourceStream(orgs)).getOrganisationUnits().getOrganisationUnit();

	}

	/**
	 * This method gets org units via files in the application
	 * directory.
	 * 
	 * @param path the directory in which the file is located
	 * @param orgs the file to be used as the opts xml
	 */
	public void getOrgUnits(String path, String orgs) throws IntegrationException {
		this.orgs=getMetaDataFromStream(getFileStream(path,orgs)).getOrganisationUnits().getOrganisationUnit();
	}

}
