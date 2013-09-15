package org.openmrs.module.integration.api.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.openmrs.module.integration.OrgUnitDisplay;
import org.openmrs.module.integration.api.JaxbObjects;
import org.openmrs.module.integration.api.jaxb.MetaData;
import org.openmrs.module.integration.api.jaxb.OrgUnitType;
import org.openmrs.module.integration.api.jaxb.ReportTemplates;
import org.openmrs.util.OpenmrsUtil;

public class DhisMetadataUtils {

	private static Log log = LogFactory.getLog(DhisMetadataUtils.class);
	private static String MODULE_NAME = "Integration";
	private static String NAMESPACE = "http://www.dhis.org/schema/dxf/2.0";

	private static String catsCall = "/api/categoryOptionCombos?viewClass=detailed&paging=false";
	private static String optsCall = "/api/categories?viewClass=export&paging=false";
	private static String orgsCall = "/api/organisationUnits?viewClass=export&paging=false";
	private static String rptsCall = "/api/dataSets";

	public static enum ContentType {
		MASTER, CATS, OPTS, ORGS
	}

	/**
	 * This method gets the 3 metadata files via the DHIS API into
	 * <application dir>/Integration/<server name>/New. The
	 * files are called master.xml, cats.xml, opts.xml.
	 * 
	 * @param server the server object which is to be accessed.
	 */
	public static void getServerMetadata(IntegrationServer server)
			throws IntegrationException {
		
		getDhisMetadataFromAPI(ContentType.MASTER, server);
		getDhisMetadataFromAPI(ContentType.CATS, server);
		getDhisMetadataFromAPI(ContentType.OPTS, server);
	}

	/**
	 * This method gets the 3 metadata files via resources in the project, it is
	 * intended primarily for testing.
	 * 
	 * @param master the resource to be used as the master xml
	 * @param cats the resource to be used as the cats xml
	 * @param opts the resource to be used as the opts xml
	 */
	public static void getServerMetadata(String master, String cats, String opts) throws IntegrationException {
		String name = master.endsWith(".xml") ? master.substring(0,master.length()-4) : master;
		
		getDhisMetadataFromResource(ContentType.MASTER, master, name);
		getDhisMetadataFromResource(ContentType.CATS, cats, name);
		getDhisMetadataFromResource(ContentType.OPTS, opts, name);
	}
	
	/**
	 * This method downloads a metadata xml file into the server's New subdirectory in application space
	 * 
	 * @param meta	content type to be downloaded
	 * @param is	server to be downloaded from
	 */
	public static void getDhisMetadataFromAPI(ContentType meta, IntegrationServer is) throws IntegrationException {
		URL url;
		String selector="";
		String accept="";
		
		switch (meta) {
		case MASTER:
			selector = rptsCall;
			accept = "application/dsd+xml";
			break;
		case CATS:
			selector = catsCall;
			accept = "application/xml";
			break;
		case OPTS:
			selector = optsCall;
			accept = "application/xml";
			break;
		case ORGS:
			selector = orgsCall;
			accept = "application/xml";
			break;
		}
		
		Credentials creds = new UsernamePasswordCredentials(
				is.getUserName(), is.getPassword());
		DefaultHttpClient httpclient = new DefaultHttpClient();
		BasicHttpContext localcontext = new BasicHttpContext();

//		Setup the GET
		try {
			url = new URL(is.getUrl());
			HttpHost targetHost = new HttpHost(url.getHost(), url.getPort(),
					url.getProtocol());
			
			HttpGet httpGet = new HttpGet(is.getUrl() + selector);
			Header bs = new BasicScheme().authenticate(creds, httpGet,
					localcontext);
			httpGet.setHeader("Authorization", bs.getValue());
			httpGet.setHeader("Content-Type", "application/xml");
			httpGet.setHeader("Accept", accept);

//		GET the response			
			HttpResponse response = httpclient.execute(targetHost, httpGet,
					localcontext);
			HttpEntity entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IntegrationException(response.getStatusLine()
						.getReasonPhrase(), null);
			} 
			
//		Copy the XML from the response to the application directory			
			if (entity != null) {
				File of = getServerFile(meta, "New", is.getServerName());
				OutputStream os = new FileOutputStream(of);
				IOUtils.copy(entity.getContent(), os);
			}

// 		Handle exceptions and close the connection			

		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(),null);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * This method downloads a resource xml file into the server's New subdirectory in application space
	 * 
	 * @param meta	content type to be downloaded
	 * @param resource	name of resource to be downloaded
	 * @param server	name of server to be downloaded from
	 */
	public static void getDhisMetadataFromResource(ContentType meta, String resource, String server) 
			throws IntegrationException {
		File of = getServerFile(meta, "New", server);
		try {
			OutputStream os = new FileOutputStream(of);
			InputStream in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(resource);
			IOUtils.copy(in, os);
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(),null);
		}
	}
	
	/**
	 * This method downloads an xml file into the server's New subdirectory in application space
	 * 
	 * @param meta	content type to be downloaded
	 * @param inFile	file object to be downloaded
	 * @param server	name of server to be downloaded from
	 */
	public static void getDhisMetadataFromFile(ContentType meta, File inFile, String server) 
			throws IntegrationException {
		File of = getServerFile(meta, "New", server);
		try {
			OutputStream os = new FileOutputStream(of);
			InputStream in = new FileInputStream(inFile);
			IOUtils.copy(in, os);
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(),null);
		}
	}
	
	/**
	 * This method builds a file object corresponding to an xml file 
	 * 
	 * @param meta	content type to be downloaded
	 * @param subdir	name of subdir to be downloaded to (New, Current)
	 * @param server	name of server to be downloaded to
	 */
	public static File getServerFile(ContentType meta, String subdir, String server) {
		final StringBuilder sb = new StringBuilder();
		sb.append(MODULE_NAME);
		sb.append(File.separatorChar);
		sb.append(server);
		sb.append(File.separatorChar);
		sb.append(subdir);
		File folder = OpenmrsUtil.getDirectoryInApplicationDataDirectory(sb.toString());
		return new File(folder, meta.toString().toLowerCase() + ".xml");
	}
	
	/**
	 * This method unmarshals a master.xml file in the server's subdir
	 * The passage through JAXBElement comes from 
	 * http://stackoverflow.com/questions/819720/no-xmlrootelement-generated-by-jaxb
	 * and is used because no root element is generated for MetaData (TODO: Still true?)
	 * 
	 * @param subdir	subdir of file to be unmarshaled from
	 * @param server	name of server to be unmarshaled form
	 */
	public static ReportTemplates UnmarshalMaster(String subdir, String server) throws IntegrationException {
		ReportTemplates user=null;
		try {
			FileInputStream in = new FileInputStream(getServerFile(ContentType.MASTER,subdir,server));
			StreamSource ss = new StreamSource(in);
			JAXBElement<ReportTemplates> userElement = (JAXBElement<ReportTemplates>) JaxbObjects.getUM().unmarshal(ss,ReportTemplates.class);
			user = userElement.getValue();
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(),null);
		}
		return user;
	}
	
	/**
	 * This method unmarshals a metadata xml file in the server's subdir
	 * The passage through JAXBElement comes from 
	 * http://stackoverflow.com/questions/819720/no-xmlrootelement-generated-by-jaxb
	 * and is used because no root element is generated for MetaData TODO: Still true?
	 * 
	 * @param meta	content type to be downloaded
	 * @param subdir	subdir of file to be unmarshaled from
	 * @param server	name of server to be unmarshaled form
	 */
	public static MetaData UnmarshalMetaData(ContentType meta, String subdir, String server) throws IntegrationException {
		MetaData user=null;
		try {
			FileInputStream in = new FileInputStream(getServerFile(meta,subdir,server));
			StreamSource ss = new StreamSource(in);
			JAXBElement<MetaData> userElement = (JAXBElement<MetaData>) JaxbObjects.getUM().unmarshal(ss,MetaData.class);
			user = userElement.getValue();
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(),null);
		}
		return user;
	}

	/**
	 * This method performs the connection test function
	 * 
	 * @param server the server to be tested
	 * @return null if connection tests ok, else a localized error message
	 */
	public static String testConnection(IntegrationServer server) {
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
			result = MODULE_NAME + ".General.Errors.MalformedUrl";
		} catch (AuthenticationException e) {
			result = MODULE_NAME + ".General.Errors.Authentication";
		} catch (ClientProtocolException e) {
			result = MODULE_NAME + ".General.Errors.ClientProtocol";
		} catch (IOException e) {
			result = MODULE_NAME + ".General.Errors.IOFailure";
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return result;
	}
	
	/**
	 * This method builds the org unit display list from orgs.xml in the server New subdir
	 * It is then available via OrgUnitDisplay.getAllHierarchical()
	 * 
	 * @param rebuild	forces rebuilding of the tree even if it is for same server
	 * @param server the server to be tested
	 */
	public static void getOrgUnitDisplay(Boolean rebuild, String server) throws IntegrationException {
		MetaData orgs = UnmarshalMetaData(ContentType.ORGS, "New", server);
		if (orgs==null) return;
		if (orgs.getOrganisationUnits()==null) return;
		if (server.equals(OrgUnitDisplay.getServerName()) && !rebuild) return;
		OrgUnitDisplay.Reset();
		for (OrgUnitType.OrganisationUnit org : orgs.getOrganisationUnits().getOrganisationUnit()) {
			OrgUnitDisplay d = new OrgUnitDisplay(org.getName(),org.getCode(),org.getId());
			d.setLevel(org.getLevel().intValue());
			d.setServer(server);
			if (org.getParent()!=null) {
				d.setParent(org.getParent().getName(), org.getParent().getCode(), org.getParent().getId(), d.getUid());
			} 
		}
		return;
	}

	public static void copyNewToCurrent(String server) throws IntegrationException {
		for (ContentType ct : ContentType.values()) {
			File of = getServerFile(ct,"Current",server);
			File inf = getServerFile(ct,"New",server);
			if (inf.canRead()) {
				try {
					OutputStream os = new FileOutputStream(of);
					InputStream ins = new FileInputStream(inf);
					IOUtils.copy(ins, os);
				} catch (Exception e) {
					throw new IntegrationException( e.getLocalizedMessage(),null);
				}
			}
		}
	}
}