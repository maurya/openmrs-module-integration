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
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.jaxb.*;
import org.openmrs.util.OpenmrsUtil;

public class ServerMetadata {

    private static Log log = LogFactory.getLog( ServerMetadata.class );
    private static String MODULE_NAME = "Integration";
	
	public enum ResourceSource { TEST, MAIN }
	public enum ContentType {MASTER, CATS, OPTS}
	
	private MessageSourceService mss = Context.getMessageSourceService();
	private Double hash = Math.random();
	private String name;
	private IntegrationServer server;
	private ReportTemplates master;
	private List<MetaData.CategoryOptionCombos.CategoryOptionCombo> cats;
	private List<MetaData.Categories.Category> opts;
	private JAXBContext jc;
	private Unmarshaller um;
	
	private final String catsCall = "/api/categoryOptionCombo?viewClass=detailed&paging=false";
	private final String optsCall = "/api/categories?viewClass=export&paging=false";
	private final String rptsCall = "/api/dataSets";
	
	/**
	 * This object holds a complete set of DHIS metadata.  It can be filled
	 * from the DHIS API, a set of resources, or files kept in the user
	 * application directory.  It also performs the connection test.
	 */
	public ServerMetadata() {
		super();
		try {
 			jc = JAXBContext.newInstance(MetaData.class,ReportTemplates.class);
			um = jc.createUnmarshaller();
		} catch (Exception e) {
			jc = null;
		}
	}
		
	/**
	 * This method initializes the object via the DHIS API.  This method stores the
	 * XML received in <application dir>/Integration/<server name>/New.  The files
	 * are called master.xml, cats.xml, opts.xml.
	 * 
	 * @param  server	the server object which is to be accessed.
 */
	public void getServerMetadata(IntegrationServer server) throws IntegrationException {
		this.server=server;
		this.name=server.getServerName();
		final URL url;
		try {
			url=new URL(server.getUrl());
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(),e);
		}
	    Credentials creds = new UsernamePasswordCredentials( server.getUserName(), server.getPassword() );
        
        this.master=(ReportTemplates) getXMLFromAPI(url,rptsCall, creds,"application/dsd+xml",ContentType.MASTER);
        MetaData m=(MetaData) getXMLFromAPI(url,optsCall,creds,"application/xml",ContentType.OPTS);
		this.opts=m.getCategories().getCategory();
        m=(MetaData) getXMLFromAPI(url,catsCall,creds,"application/xml",ContentType.CATS);
		this.cats=m.getCategoryOptionCombos().getCategoryOptionCombo();
	}
	
	/**
	 * This method initializes the object via resources in the project, it is intended primarily
	 * for testing.
	 * 
	 * @param  rs	MAIN or TEST
	 * @param	master	the resource to be used as the master xml
	 * @param	cats	the resource to be used as the cats xml
	 * @param	opts	the resource to be used as the opts xml
 */
	public void getServerMetadata(ResourceSource rs, String master, String cats, String opts) throws IntegrationException {
		this.name=rs.name();
		this.master=(ReportTemplates) getXMLFromResource(rs,master);
		MetaData m = (MetaData) getXMLFromResource(rs,cats);
		this.opts=m.getCategories().getCategory();
		m = (MetaData) getXMLFromResource(rs,opts);
		this.cats=m.getCategoryOptionCombos().getCategoryOptionCombo();
	}
	
	/**
	 * This method initializes the object via files in the application directory.
	 * 
	 * @param  name	the directory in which the files are located
	 * @param	master	the file to be used as the master xml
	 * @param	cats	the file to be used as the cats xml
	 * @param	opts	the file to be used as the opts xml
	 */
	public void getServerMetadata(String name, String master, String cats, String opts) throws IntegrationException {
		this.name=name;
		this.master=(ReportTemplates) getXMLFromServerFile(name,master);
		MetaData m = (MetaData) getXMLFromServerFile(name,cats);
		this.opts=m.getCategories().getCategory();
		m = (MetaData) getXMLFromServerFile(name,opts);
		this.cats=m.getCategoryOptionCombos().getCategoryOptionCombo();
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
	
	/**
	 * This method gets XML from the API, stores it in the user application directory,
	 * and unmarshals it into the DHIS objects. 
	 * 
	 * @param  url	the url of the host to be accessed
	 * @param	selector	the low-order part of the url for getting a particular set
	 * @param	creds	a credentials object containing the username and password
	 * @param	accept	the contents of the accept header
	 * @param	saveAs	the content type to be saved (MASTER, CATS, OPTS)
	 * @return the unmarshalled object representing the xml
 */
	private Object getXMLFromAPI(URL url, String selector, Credentials creds, String accept, ContentType saveAs) throws IntegrationException {
		Object result=new Object();
		HttpEntity entity=getEntityFromAPI(url, selector, creds, accept);
        if (entity  != null ) {
    		try {
    			final StringBuilder sb=new StringBuilder();
    			sb.append(MODULE_NAME);
    			sb.append(File.separatorChar);
    			sb.append(server.getServerName());
    			sb.append(File.separatorChar);
    			sb.append("New");
    			File folder = OpenmrsUtil.getDirectoryInApplicationDataDirectory(sb.toString());
    			File of = new File(folder,saveAs.toString().toLowerCase() + ".xml");
    			OutputStream os = new FileOutputStream(of);
    			IOUtils.copy(entity.getContent(), os);
    			result = getXMLFromServerFile(sb.toString(),saveAs.toString().toLowerCase() + ".xml");
    		} catch (Exception e) {
   				throw new IntegrationException(e.getLocalizedMessage(),e);
    		}
        }
        return result;
	}
	
	/**
	 * This method gets XML from the API as an HttpEntity 
	 * 
	 * @param  url	the url of the host to be accessed
	 * @param	selector	the low-order part of the url for getting a particular set
	 * @param	creds	a credentials object containing the username and password
	 * @param	accept	the contents of the accept header
	 * @return the unmarshalled object representing the xml
	 */
	private HttpEntity getEntityFromAPI(URL url, String selector, Credentials creds, String accept) throws IntegrationException {
	    HttpHost targetHost = new HttpHost( url.getHost(), url.getPort(), url.getProtocol() );
	    DefaultHttpClient httpclient = new DefaultHttpClient();
	    BasicHttpContext localcontext = new BasicHttpContext();
	
	    try
        {
            HttpGet httpGet = new HttpGet( url.getPath() + selector );
            Header bs = new BasicScheme().authenticate( creds, httpGet, localcontext );
            httpGet.addHeader( "Authorization", bs.getValue() );
            httpGet.addHeader( "Content-Type", "application/xml" );
            httpGet.addHeader( "Accept", accept );

            HttpResponse response = httpclient.execute( targetHost, httpGet, localcontext );
            HttpEntity entity = response.getEntity();
            
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IntegrationException(response.getStatusLine().getReasonPhrase(), null);
            }


            // TODO: fix these catches ...
//        } catch ( JAXBException ex )
//        {
//            throw new IntegrationException( "Problem unmarshalling ImportSummary", ex );
        } 
        catch ( AuthenticationException ex )
        {
            throw new IntegrationException( mss.getMessage("Integration.General.Errors.Authentication"), ex );
        }
        catch ( IOException ex )
        {
            throw new IntegrationException( mss.getMessage("Integration.General.Errors.IOFailure"), ex );
        }
        finally
        {
            httpclient.getConnectionManager().shutdown();
        }
	    return null;
    }
	
	/**
	 * This method gets the XML from a resource 
	 * 
	 * @param  rs	the resource source (MAIN, TEST)
	 * @param	resource	the name of the resource
	 * @return the unmarshalled object representing the xml
	 */
	private Object getXMLFromResource(ResourceSource rs, String resource) throws IntegrationException {
		try {
			return um.unmarshal(getResourceStream(rs, resource));
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage());
		}
	}

	/**
	 * This method gets the XML from a file in the user application directory 
	 * 
	 * @param  path	the directory within the user application directory
	 * @param	resource	the name of the file
	 * @return the unmarshalled object representing the xml
	 */
	private Object getXMLFromServerFile(String path, String resource) throws IntegrationException {
		try {
			return um.unmarshal(getFileStream(path,resource));
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(),e);
		}
	}

	/**
	 * This method gets the stream representing a resource 
	 * 
	 * @param  rs	the resource source (MAIN, TEST)
	 * @param	resource	the name of the file
	 * @return input stream containing test
	 */
	private InputStream getResourceStream(ResourceSource rs, String resource) {
		final StringBuilder sb=new StringBuilder();
		switch (rs) {
			case MAIN:
				sb.append("main/resource/");
				break;
			case TEST:
				sb.append("test/resource/");
				break;
		}
		sb.append(resource);
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(sb.toString());
	}
	
	/**
	 * This method gets the input stream for a file in the user application directory 
	 * 
	 * @param  folder	the directory within the user application directory
	 * @param	file	the name of the file
	 * @return input stream for the file
	 */
	private InputStream getFileStream(String folder, String file) throws IntegrationException{
		final StringBuilder sb=new StringBuilder();
		sb.append(OpenmrsUtil.getDirectoryInApplicationDataDirectory(folder));
		if (sb.charAt(sb.length()-1)!=File.separatorChar) 
			sb.append(File.separatorChar);
		sb.append(file);
		try {
			File f=new File(sb.toString());
			return new FileInputStream(f);
		} catch (Exception e) {
			throw new IntegrationException(e.getLocalizedMessage(),e);
		}
	}
	
	/**
	 * This method performs the connection test function 
	 * 
	 * @param  server	the server to be tested
	 * @return null if connection tests ok, else a localized error message
	 */
	public String testConnection(IntegrationServer server) {
		String result=null;
	    Credentials creds = new UsernamePasswordCredentials( server.getUserName(), server.getPassword() );
	    DefaultHttpClient httpclient = new DefaultHttpClient();
	    BasicHttpContext localcontext = new BasicHttpContext();
		
		final URL url;
		try {
			url=new URL(server.getUrl());
		    HttpHost targetHost = new HttpHost( url.getHost(), url.getPort(), url.getProtocol() );
            HttpGet httpGet = new HttpGet( url.getPath() + "/api" );

            Header bs = new BasicScheme().authenticate( creds, httpGet, localcontext );
            httpGet.addHeader( "Authorization", bs.getValue() );
            httpGet.addHeader( "Content-Type", "text/html" );
            httpGet.addHeader( "Accept", "text/html" );

            HttpResponse response = httpclient.execute( targetHost, httpGet, localcontext );
            if (response.getStatusLine().getStatusCode() != 200) {
                result=response.getStatusLine().getReasonPhrase();
            }
		} catch (MalformedURLException e) {
			result = mss.getMessage("Integration.General.Errors.MalformedUrl");
		} catch (AuthenticationException e) {
			result = mss.getMessage("Integration.General.Errors.Authentication");
		} catch (ClientProtocolException e) {
			result = mss.getMessage("Integration.General.Errors.ClientProtocol");
		} catch (IOException e) {
			result = mss.getMessage("Integration.General.Errors.IOFailure");
		} finally {
            httpclient.getConnectionManager().shutdown();
        }
	    
		return result;
	}
}
	
	

